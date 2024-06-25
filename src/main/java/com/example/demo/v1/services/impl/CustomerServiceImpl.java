package com.example.demo.v1.services.impl;

import com.example.demo.v1.dtos.structured.CustomerDTO;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.repositories.ICustomerRepository;
import com.example.demo.v1.services.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Customer save(CustomerDTO customerDTO) {
        // Check the account param to be at max 10 and least 2 characters with only letters and numbers allowed
        if (!customerDTO.getAccount().matches("^[a-zA-Z0-9]{2,10}$")) {
            throw new IllegalArgumentException("Account number must be at least 2 and at most 10 characters long and contain only letters and numbers");
        }
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(UUID id, CustomerDTO customerDTO) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setFirstName(customerDTO.getFirstName());
            existingCustomer.setLastName(customerDTO.getLastName());
            existingCustomer.setAccount(customerDTO.getAccount());
            existingCustomer.setDob(customerDTO.getDob());
            existingCustomer.setMobile(customerDTO.getMobile());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setBalance(customerDTO.getBalance());

            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    public Optional<Customer> getById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public boolean delete(UUID id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
