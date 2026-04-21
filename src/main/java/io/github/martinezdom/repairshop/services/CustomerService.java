package io.github.martinezdom.repairshop.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.CustomerCreateDTO;
import io.github.martinezdom.repairshop.dtos.CustomerResponseDTO;
import io.github.martinezdom.repairshop.dtos.CustomerUpdateDTO;
import io.github.martinezdom.repairshop.entities.Customer;
import io.github.martinezdom.repairshop.exceptions.CustomerAlreadyExistsException;
import io.github.martinezdom.repairshop.exceptions.CustomerNotFoundException;
import io.github.martinezdom.repairshop.exceptions.EmailAlreadyExists;
import io.github.martinezdom.repairshop.exceptions.PhoneAlreadyExists;
import io.github.martinezdom.repairshop.repositories.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO createCustomer(CustomerCreateDTO dto) {
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

        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setEmail(savedCustomer.getEmail());
        customerResponseDTO.setFirstName(savedCustomer.getFirstName());
        customerResponseDTO.setLastName(savedCustomer.getLastName());
        customerResponseDTO.setPhone(savedCustomer.getPhone());
        customerResponseDTO.setId(savedCustomer.getId());
        return customerResponseDTO;
    }

    public Page<CustomerResponseDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.map(customer -> {
            CustomerResponseDTO dto = new CustomerResponseDTO();
            dto.setId(customer.getId());
            dto.setFirstName(customer.getFirstName());
            dto.setLastName(customer.getLastName());
            dto.setEmail(customer.getEmail());
            dto.setPhone(customer.getPhone());
            return dto;
        });
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    public CustomerResponseDTO updateCustomer(Long id, CustomerUpdateDTO dto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        if (!customer.getEmail().equals(dto.getEmail()) &&  customerRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExists("Email is already in use");
        }
        if (!customer.getPhone().equals(dto.getPhone()) && customerRepository.existsByPhone(dto.getPhone())) {
            throw new PhoneAlreadyExists("Phone is already in use");
        }

        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setEmail(updatedCustomer.getEmail());
        customerResponseDTO.setPhone(updatedCustomer.getPhone());
        customerResponseDTO.setId(updatedCustomer.getId());
        customerResponseDTO.setFirstName(updatedCustomer.getFirstName());
        customerResponseDTO.setLastName(updatedCustomer.getLastName());
        return customerResponseDTO;
    }

}
