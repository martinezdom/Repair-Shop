package io.github.martinezdom.repairshop.repositories;

import org.springframework.stereotype.Repository;

import io.github.martinezdom.repairshop.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}