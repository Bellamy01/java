package com.example.demo.v1.controllers;

import com.example.demo.v1.dtos.structured.SavingDTO;
import com.example.demo.v1.dtos.structured.WithdrawDTO;
import com.example.demo.v1.models.Saving;
import com.example.demo.v1.models.Withdraw;
import com.example.demo.v1.services.IWithdrawService;
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
@RequestMapping("/api/v1/withdraw")
public class WithdrawController {
    private final IWithdrawService withdrawService;

    @PostMapping
    public ApiResponse<Withdraw> save(
            @Valid @RequestBody WithdrawDTO withdrawDTO
    ) {
        Withdraw savedWithdraw = withdrawService.save(withdrawDTO);
        if (savedWithdraw != null){
            return new ApiResponse<>(savedWithdraw,"Withraw Registered successfully", HttpStatus.CREATED,true);
        }
        return new ApiResponse<>("Failure to record saving",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ApiResponse<List<Withdraw>> getAll() {
        List<Withdraw> withdraws = withdrawService.getAll();
        if (withdraws != null){
            return new ApiResponse<>(withdraws,"Withdraws retrieval successful",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure saving retrieval",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Optional<Withdraw>> getById(@PathVariable(value = "id") UUID id){
        Optional<Withdraw> withdraw = withdrawService.getById(id);
        if (withdraw.isPresent()){
            return new ApiResponse<>(withdraw,"Withdraw retrieval Successful!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to retrieve saving",null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "id") UUID id){
        boolean deletionSuccessful = withdrawService.delete(id);
        if(deletionSuccessful){
            return new ApiResponse<>(true,"Withdraw successfully delete!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to delete saving",null,HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<Withdraw> update(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody WithdrawDTO withdrawDTO
    ) {
        Withdraw updatedWithdraw = withdrawService.update(id,withdrawDTO);
        return new ApiResponse<>(updatedWithdraw,"Withdraw successfully updated !",HttpStatus.OK,true);
    }
}
