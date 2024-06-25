package com.example.demo.v1.services;

import com.example.demo.v1.dtos.structured.SavingDTO;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.models.Saving;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISavingService {
    Saving save(SavingDTO savingDTO);
    Saving update(UUID id, SavingDTO savingDTO);
    Optional<Saving> getById(UUID id);
    List<Saving> getAll();
    boolean delete(UUID id);
    void registerTransaction(Customer Customer, String message);
}
