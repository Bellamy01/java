package com.example.demo.v1.controllers;

import com.example.demo.v1.dtos.structured.SavingDTO;
import com.example.demo.v1.models.Saving;
import com.example.demo.v1.services.ISavingService;
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
@RequestMapping("/api/v1/savings")
public class SavingController {
    private final ISavingService savingService;

    @PostMapping
    public ApiResponse<Saving> save(
            @Valid @RequestBody SavingDTO savingDTO
    ) {
        Saving savedSaving = savingService.save(savingDTO);
        if (savedSaving != null){
            return new ApiResponse<>(savedSaving,"Saving Registered successfully",HttpStatus.CREATED,true);
        }
        return new ApiResponse<>("Failure to record saving",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ApiResponse<List<Saving>> getAll() {
        List<Saving> savings = savingService.getAll();
        if (savings != null){
            return new ApiResponse<>(savings,"Savings retrieval successful",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure saving retrieval",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Optional<Saving>> getById(@PathVariable(value = "id") UUID id){
        Optional<Saving> saving = savingService.getById(id);
        if (saving.isPresent()){
            return new ApiResponse<>(saving,"Saving retrieval Successful!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to retrieve saving",null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "id") UUID id){
        boolean deletionSuccessful = savingService.delete(id);
        if(deletionSuccessful){
            return new ApiResponse<>(true,"Saving successfully delete!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to delete saving",null,HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<Saving> update(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody SavingDTO savingDTO
    ) {
        Saving updatedSaving = savingService.update(id,savingDTO);
        return new ApiResponse<>(updatedSaving,"Saving successfully updated !", HttpStatus.OK,true);
    }
}
