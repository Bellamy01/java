package com.example.demo.v1.services.impl;

import com.example.demo.v1.configs.EmailService;
import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.dtos.structured.SavingDTO;
import com.example.demo.v1.enumerations.ETransactionType;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.models.Saving;
import com.example.demo.v1.repositories.ICustomerRepository;
import com.example.demo.v1.repositories.ISavingRepository;
import com.example.demo.v1.services.IMessageService;
import com.example.demo.v1.services.ISavingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SavingServiceImpl implements ISavingService {
    @Autowired
    private ISavingRepository savingRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private EmailService emailService;

    @Override
    public Saving save(SavingDTO savingDTO) {
        if (savingDTO.getType() != null && !savingDTO.getType().equals(ETransactionType.SAVING)) {
            throw new IllegalArgumentException("Type must be set to Saving");
        }
        Saving saving = modelMapper.map(savingDTO, Saving.class);
        Optional<Customer> customerOpt = customerRepository.findById(savingDTO.getCustomer());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getAccount().equals(savingDTO.getAccount())) {
                customer.setBalance(customer.getBalance() + savingDTO.getAmount());
                customerRepository.save(customer);
                Saving saved = savingRepository.save(saving);
                String emailContent = "Dear " + customer.getFirstName() + " " + customer.getLastName() +  " ,\n\nYour savings of " + saved.getAmount() + " on your account " + saved.getAccount() + " has been completed successfully";
                emailService.sendSimpleEmail(customer.getEmail(), "Saving Successful", emailContent);
                registerTransaction(customer, emailContent);
                return saved;
            } else {
                throw new IllegalArgumentException("Account number does not match customer account");
            }
        } else {
            throw new IllegalArgumentException("Customer not found");
        }
    }

    @Override
    public Saving update(UUID id, SavingDTO savingDTO) {
        if (savingDTO.getType() != null && !savingDTO.getType().equals(ETransactionType.SAVING)) {
            throw new IllegalArgumentException("Type must be set to Saving");
        }
        Optional<Saving> savingOpt = savingRepository.findById(id);
        if (savingOpt.isPresent()) {
            Saving existingSaving = savingOpt.get();

            // Fetch the customer
            Optional<Customer> customerOpt = customerRepository.findById(savingDTO.getCustomer());
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                double originalAmount = existingSaving.getAmount();
                double newAmount = savingDTO.getAmount();

                // Update customer balance
                customer.setBalance(customer.getBalance() - originalAmount + newAmount);
                customerRepository.save(customer);

                // Update saving details
                existingSaving.setAccount(savingDTO.getAccount());
                existingSaving.setAmount(newAmount);
                existingSaving.setType(savingDTO.getType());

                return savingRepository.save(existingSaving);
            } else {
                throw new IllegalArgumentException("Customer not found");
            }
        }
        return null;
    }

    @Override
    public Optional<Saving> getById(UUID id) {
        return savingRepository.findById(id);
    }

    @Override
    public boolean delete(UUID id) {
        Optional<Saving> savingOpt = savingRepository.findById(id);
        if (savingOpt.isPresent()) {
            Saving saving = savingOpt.get();

            // Fetch the customer
            Optional<Customer> customerOpt = customerRepository.findById(saving.getCustomer().getId());
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                customer.setBalance(customer.getBalance() - saving.getAmount());
                customerRepository.save(customer);
            }

            savingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Saving> getAll() {
        return savingRepository.findAll();
    }

    @Override
    public void registerTransaction(Customer customer, String message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCustomer(customer.getId());
        messageDTO.setMessage(message);
        messageService.save(messageDTO);
    }
}
