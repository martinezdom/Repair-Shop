package io.github.martinezdom.repairshop.repositories;

import org.springframework.stereotype.Repository;

import io.github.martinezdom.repairshop.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
}       