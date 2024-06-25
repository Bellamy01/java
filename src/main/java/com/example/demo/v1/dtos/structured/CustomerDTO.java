package com.example.demo.v1.dtos.structured;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String dob;
    private String account;
    private Double balance;
}
