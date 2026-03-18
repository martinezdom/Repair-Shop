package io.github.martinezdom.repairshop.repositories;

import org.springframework.stereotype.Repository;

import io.github.martinezdom.repairshop.entities.Vehicle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByLicensePlate(String plate);

    boolean existsByLicensePlate(String plateString);
}
