package io.github.martinezdom.repairshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.VehicleCreateDTO;
import io.github.martinezdom.repairshop.services.VehicleService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VehicleCreateDTO dto) {
        vehicleService.createVehicle(dto);
        return ResponseEntity.status(201).body("Vehicle registered successfully");
    }

}
