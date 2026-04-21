package io.github.martinezdom.repairshop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.CustomerCreateDTO;
import io.github.martinezdom.repairshop.dtos.CustomerResponseDTO;
import io.github.martinezdom.repairshop.dtos.CustomerUpdateDTO;
import io.github.martinezdom.repairshop.services.CustomerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(dto);
        return ResponseEntity.status(201).body(customerResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CustomerResponseDTO> customerResponseDTO = customerService.getAllCustomers(page, size);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO dto) {
        CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(customerResponseDTO);
    }
}
