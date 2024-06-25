package com.example.demo.v1.controllers;

import com.example.demo.v1.dtos.structured.BankingDTO;
import com.example.demo.v1.dtos.structured.WithdrawDTO;
import com.example.demo.v1.models.Banking;
import com.example.demo.v1.models.Withdraw;
import com.example.demo.v1.services.IBankingService;
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
@RequestMapping("/api/v1/banking")
public class BankingController {
    private final IBankingService bankingService;

    @PostMapping
    public ApiResponse<Banking> save(
            @Valid @RequestBody BankingDTO bankingDTO
    ) {
        Banking savedBanking = bankingService.save(bankingDTO);
        if (savedBanking != null){
            return new ApiResponse<>(savedBanking,"Banking Registered successfully", HttpStatus.CREATED,true);
        }
        return new ApiResponse<>("Failure to record",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ApiResponse<List<Banking>> getAll() {
        List<Banking> bankings = bankingService.getAll();
        if (bankings != null){
            return new ApiResponse<>(bankings,"Bankings retrieval successful",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure banking retrieval",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Optional<Banking>> getById(@PathVariable(value = "id") UUID id){
        Optional<Banking> banking = bankingService.getById(id);
        if (banking.isPresent()){
            return new ApiResponse<>(banking,"Banking retrieval Successful!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to retrieve banking",null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "id") UUID id){
        boolean deletionSuccessful = bankingService.delete(id);
        if(deletionSuccessful){
            return new ApiResponse<>(true,"Banking successfully delete!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to delete banking",null,HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<Banking> update(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody BankingDTO bankingDTO
    ) {
        Banking updatedBanking = bankingService.update(id,bankingDTO);
        return new ApiResponse<>(updatedBanking,"Banking successfully updated !",HttpStatus.OK,true);
    }
}
