package com.example.demo.v1.configs;

import com.example.demo.v1.dtos.structured.CustomerDTO;
import com.example.demo.v1.models.Customer;
import com.example.demo.v1.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServiceRunner implements CommandLineRunner {
    @Autowired
    private ICustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ServiceRunner.run");
        createCustomerIfNotExist();
        System.out.println("ServiceRunner.run.done");
    }

    public void createCustomerIfNotExist() {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setBalance(1000.0);
        customer.setDob("1990-01-01");
        customer.setEmail("cedrickmanzii0@gmail.com");
        customer.setMobile("123456789");
        customer.setAccount("RCA0236");
        customerService.save(customer);
        System.out.println("ServiceRunner.createCustomerIfNotExist");
    }
}
