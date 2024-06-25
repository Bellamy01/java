package com.example.demo.v1.services.impl;

import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.models.Message;
import com.example.demo.v1.repositories.IMessageRepository;
import com.example.demo.v1.services.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessagingServiceImpl implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Message save(MessageDTO messageDTO) {
        Message message = modelMapper.map(messageDTO, Message.class);
        return messageRepository.save(message);
    }

    @Override
    public Message update(UUID id, MessageDTO messageDTO) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            Message existingMessage = message.get();
            existingMessage.setMessage(messageDTO.getMessage());

            return messageRepository.save(existingMessage);
        }
        return null;
    }

    @Override
    public Optional<Message> getById(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public boolean delete(UUID id) {
        try {
            messageRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }


}
