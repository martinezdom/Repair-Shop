package io.github.martinezdom.repairshop.repositories;

import org.springframework.stereotype.Repository;

import io.github.martinezdom.repairshop.entities.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
