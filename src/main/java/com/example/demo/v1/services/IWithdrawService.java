package com.example.demo.v1.services;


import com.example.demo.v1.dtos.structured.WithdrawDTO;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.models.Withdraw;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWithdrawService {
    Withdraw save(WithdrawDTO withdrawDTO);
    Withdraw update(UUID id, WithdrawDTO withdrawDTO);
    Optional<Withdraw> getById(UUID id);
    List<Withdraw> getAll();
    boolean delete(UUID id);
    void registerTransaction(Customer Customer, String message);
}
