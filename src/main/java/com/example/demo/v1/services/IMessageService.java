package com.example.demo.v1.services;

import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.models.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMessageService {
    Message save(MessageDTO messageDTO);
    Message update(UUID id, MessageDTO messageDTO);
    Optional<Message> getById(UUID id);
    List<Message> getAll();
    boolean delete(UUID id);
}
