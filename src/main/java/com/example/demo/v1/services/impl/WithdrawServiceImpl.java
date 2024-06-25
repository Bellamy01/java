package com.example.demo.v1.services.impl;
import com.example.demo.v1.configs.EmailService;
import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.dtos.structured.WithdrawDTO;
import com.example.demo.v1.enumerations.ETransactionType;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.models.Withdraw;
import com.example.demo.v1.repositories.ICustomerRepository;
import com.example.demo.v1.repositories.IWithdrawRepository;
import com.example.demo.v1.services.IMessageService;
import com.example.demo.v1.services.IWithdrawService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WithdrawServiceImpl implements IWithdrawService {
    @Autowired
    private IWithdrawRepository withdrawRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public Withdraw save(WithdrawDTO withdrawDTO) {
        if (withdrawDTO.getType() != null && !withdrawDTO.getType().equals(ETransactionType.WITHDRAW)) {
            throw new IllegalArgumentException("Type must be set to Withdraw");
        }
        Withdraw withdraw = modelMapper.map(withdrawDTO, Withdraw.class);
        Optional<Customer> customerOpt = customerRepository.findById(withdrawDTO.getCustomer());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getAccount().equals(withdraw.getAccount())) {
                customer.setBalance(customer.getBalance() - withdraw.getAmount());
                customerRepository.save(customer);
                withdraw.setCustomer(customer);
                Withdraw saved = withdrawRepository.save(withdraw);
                String emailContent = "Dear " + customer.getFirstName() + " " + customer.getLastName() +  " ,\n\nYour withdrawal of " + withdraw.getAmount() + " on your account " + withdraw.getAccount() + " has been completed successfully";
                emailService.sendSimpleEmail(customer.getEmail(), "Withdrawal Successful", emailContent);
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
    public Withdraw update(UUID id, WithdrawDTO withdrawDTO) {
        if (withdrawDTO.getType() != null && !withdrawDTO.getType().equals(ETransactionType.WITHDRAW)) {
            throw new IllegalArgumentException("Type must be set to Withdraw");
        }
        Optional<Withdraw> withdrawOPt = withdrawRepository.findById(id);
        if (withdrawOPt.isPresent()) {
            Withdraw existingWithdraw = withdrawOPt.get();

            // Fetch the customer
            Optional<Customer> customerOpt = customerRepository.findById(withdrawDTO.getCustomer());
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                double originalAmount = withdrawDTO.getAmount();
                double newAmount = withdrawDTO.getAmount();

                customer.setBalance(customer.getBalance() - originalAmount - newAmount);
                customerRepository.save(customer);

                existingWithdraw.setAccount(withdrawDTO.getAccount());
                existingWithdraw.setAmount(newAmount);
                existingWithdraw.setType(withdrawDTO.getType());

                return withdrawRepository.save(existingWithdraw);
            } else {
                throw new IllegalArgumentException("Customer not found");
            }
        }
        return null;
    }

    @Override
    public Optional<Withdraw> getById(UUID id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public boolean delete(UUID id) {
        try {
            withdrawRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Withdraw> getAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public void registerTransaction(Customer customer, String message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCustomer(customer.getId());
        messageDTO.setMessage(message);
        messageService.save(messageDTO);
    }
}
