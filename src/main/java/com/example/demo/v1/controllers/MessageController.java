package com.example.demo.v1.controllers;

import com.example.demo.v1.dtos.structured.BankingDTO;
import com.example.demo.v1.dtos.structured.MessageDTO;
import com.example.demo.v1.models.Banking;
import com.example.demo.v1.models.Message;
import com.example.demo.v1.services.IMessageService;
import com.example.demo.v1.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final IMessageService messageService;

    @PostMapping
    public ApiResponse<Message> save(
            @Valid @RequestBody MessageDTO messageDTO
            ) {
        Message savedMessage = messageService.save(messageDTO);
        if (savedMessage != null){
            return new ApiResponse<>(savedMessage,"Message Registered successfully", HttpStatus.CREATED,true);
        }
        return new ApiResponse<>("Failure to record",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ApiResponse<List<Message>> getAll() {
        List<Message> messages = messageService.getAll();
        if (messages != null){
            return new ApiResponse<>(messages,"Messages retrieval successful",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure message retrieval",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Optional<Message>> getById(@PathVariable(value = "id") UUID id){
        Optional<Message> message = messageService.getById(id);
        if (message.isPresent()){
            return new ApiResponse<>(message,"Message retrieval Successful!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to retrieve message",null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "id") UUID id){
        boolean deletionSuccessful = messageService.delete(id);
        if(deletionSuccessful){
            return new ApiResponse<>(true,"Message successfully delete!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to delete message",null,HttpStatus.BAD_REQUEST);
    }
}
