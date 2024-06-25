package com.example.demo.v1.services;

import com.example.demo.v1.dtos.structured.CustomerDTO;
import com.example.demo.v1.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICustomerService {
    Customer save(CustomerDTO customersDTO);
    Customer update(UUID id, CustomerDTO customersDTO);
    Optional<Customer> getById(UUID id);
    List<Customer> getAll();
    boolean delete(UUID id);
}