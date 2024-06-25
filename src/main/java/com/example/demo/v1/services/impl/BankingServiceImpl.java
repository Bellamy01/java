package com.example.demo.v1.services.impl;

import com.example.demo.v1.configs.EmailService;
import com.example.demo.v1.dtos.structured.BankingDTO;
import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.enumerations.ETransactionType;
import com.example.demo.v1.models.Banking;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.repositories.IBankingRepository;
import com.example.demo.v1.repositories.ICustomerRepository;
import com.example.demo.v1.services.IBankingService;
import com.example.demo.v1.services.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankingServiceImpl implements IBankingService {
    @Autowired
    private IBankingRepository bankingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;

    @Autowired
    private IMessageService messageService;

    @Override
    public Banking save(BankingDTO bankingDTO) {
        if (bankingDTO.getType() != null && !bankingDTO.getType().equals(ETransactionType.TRANSFER)) {
            throw new IllegalArgumentException("Type must be set to Transfer");
        }
        Banking banking = modelMapper.map(bankingDTO, Banking.class);
        Optional<Customer> customerOpt = customerRepository.findById(bankingDTO.getCustomer());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (!customer.getAccount().equals(banking.getAccount())) {
                if (customer.getBalance() >= banking.getAmount()) {
                    customer.setBalance(customer.getBalance() - banking.getAmount());
                    customerRepository.save(customer);
                    banking.setCustomer(customer);
                    Banking banking1 = bankingRepository.save(banking);
                    String emailContent = "Dear " + customer.getFirstName() + " " + customer.getLastName() +  " ,\n\nYour transfer of " + banking.getAmount() + " to account " + banking.getAccount() + " has been completed successfully";
                    emailService.sendSimpleEmail(customer.getEmail(), "Transfer Successful", emailContent);
                    registerTransaction(customer, emailContent);
                    return banking1;
                } else {
                    throw new IllegalArgumentException("Insufficient balance");
                }
            } else {
                throw new IllegalArgumentException("Account number does not match customer account");
            }
        } else {
            throw new IllegalArgumentException("Customer not found");
        }
    }

    @Override
    public Banking update(UUID id, BankingDTO bankingDTO) {
        if (bankingDTO.getType() != null && !bankingDTO.getType().equals(ETransactionType.TRANSFER)) {
            throw new IllegalArgumentException("Type must be set to Transfer");
        }
        Optional<Banking> bankingOpt = bankingRepository.findById(id);
        if (bankingOpt.isPresent()) {
            Banking existingBanking = bankingOpt.get();

            // Fetch the customer
            Optional<Customer> customerOpt = customerRepository.findById(bankingDTO.getCustomer());
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                if (!customer.getAccount().equals(bankingDTO.getAccount())) {
                    if (customer.getBalance() >= bankingDTO.getAmount()) {
                        customer.setBalance(customer.getBalance() - bankingDTO.getAmount());
                        customerRepository.save(customer);
                        existingBanking.setCustomer(customer);
                        existingBanking.setAmount(bankingDTO.getAmount());
                        existingBanking.setAccount(bankingDTO.getAccount());
                        // Do the email sending here
                        Banking banking = bankingRepository.save(existingBanking);
                        String emailContent = "Dear " + customer.getFirstName() + " " + customer.getLastName() +  " ,\n\nYour transfer of " + banking.getAmount() + " to account " + banking.getAccount() + " has been completed successfully";
                        emailService.sendSimpleEmail(customer.getEmail(), "Transfer Successful", emailContent);
                        registerTransaction(customer, emailContent);
                    } else {
                        throw new IllegalArgumentException("Insufficient balance");
                    }
                } else {
                    throw new IllegalArgumentException("Account number does not match customer account");
                }
            } else {
                throw new IllegalArgumentException("Customer not found");
            }
        }
        return null;
    }

    @Override
    public Optional<Banking> getById(UUID id) {
        return bankingRepository.findById(id);
    }

    @Override
    public boolean delete(UUID id) {
        try {
            bankingRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Banking> getAll() {
        return bankingRepository.findAll();
    }

    public void registerTransaction(Customer customer, String message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCustomer(customer.getId());
        messageDTO.setMessage(message);
        messageService.save(messageDTO);
    }
}
