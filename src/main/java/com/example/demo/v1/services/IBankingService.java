package com.example.demo.v1.services;

import com.example.demo.v1.dtos.structured.BankingDTO;
import com.example.demo.v1.models.Banking;
import com.example.demo.v1.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IBankingService {
    Banking save(BankingDTO bankingDTO);
    Banking update(UUID id, BankingDTO bankingDTO);
    Optional<Banking> getById(UUID id);
    List<Banking> getAll();
    boolean delete(UUID id);
    void registerTransaction(Customer Customer, String message);
}
