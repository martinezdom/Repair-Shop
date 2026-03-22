package io.github.martinezdom.repairshop.services;

import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.CustomerCreateDTO;
import io.github.martinezdom.repairshop.entities.Customer;
import io.github.martinezdom.repairshop.exceptions.CustomerAlreadyExistsException;
import io.github.martinezdom.repairshop.repositories.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerCreateDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }

        if (customerRepository.existsByPhone(dto.getPhone())) {
            throw new CustomerAlreadyExistsException("Customer with this phone number already exists");
        }

        Customer customer = new Customer();
        customer.setEmail(dto.getEmail());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhone(dto.getPhone());
        return customerRepository.save(customer);
    }
}
