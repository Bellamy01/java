package com.example.demo.v1.controllers;

import com.example.demo.v1.dtos.structured.CustomerDTO;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.services.ICustomerService;
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
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping
    public ApiResponse<Customer> save(
            @Valid @RequestBody CustomerDTO customerDTO
    ) {
        Customer savedCustomer = customerService.save(customerDTO);
        if (savedCustomer != null){
            return new ApiResponse<>(savedCustomer,"Customer Registered successfully", HttpStatus.CREATED,true);
        }
        return new ApiResponse<>("Failure to add Customer",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ApiResponse<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAll();
        if (customers != null){
            return new ApiResponse<>(customers,"Customers retrieval successful",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure Customer retrieval",null,HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ApiResponse<Optional<Customer>> getById(@PathVariable(value = "id") UUID id){
        Optional<Customer> customer = customerService.getById(id);
        if (customer != null){
            return new ApiResponse<>(customer,"Customer retrieval Successful!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to retrieve customer",null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<Boolean> remove(@PathVariable(value = "id") UUID id){
        boolean deletionSuccessful = customerService.delete(id);
        if(deletionSuccessful){
            return new ApiResponse<>(true,"Customer Successfully delete!",HttpStatus.OK,true);
        }
        return new ApiResponse<>("Failure to delete customer",null,HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<Customer> update(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody CustomerDTO customerDTO
    ) {
        Customer updatedCustomer = customerService.update(id,customerDTO);
        return new ApiResponse<>(updatedCustomer,"Customer successfully updated !",HttpStatus.OK,true);
    }
}
