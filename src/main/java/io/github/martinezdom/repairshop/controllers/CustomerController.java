package io.github.martinezdom.repairshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.CustomerCreateDTO;
import io.github.martinezdom.repairshop.entities.Customer;
import io.github.martinezdom.repairshop.services.CustomerService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerCreateDTO dto) {
        Customer newCustomer = customerService.createCustomer(dto);
        return ResponseEntity.status(201).body(newCustomer);
    }
    
}
