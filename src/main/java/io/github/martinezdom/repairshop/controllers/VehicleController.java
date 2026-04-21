package io.github.martinezdom.repairshop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.VehicleCreateDTO;
import io.github.martinezdom.repairshop.dtos.VehicleResponseDTO;
import io.github.martinezdom.repairshop.entities.Vehicle;
import io.github.martinezdom.repairshop.services.VehicleService;
import jakarta.validation.Valid;

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
        Vehicle newVehicle = vehicleService.createVehicle(dto);
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        vehicleResponseDTO.setId(newVehicle.getId());
        vehicleResponseDTO.setBrand(newVehicle.getBrand());
        vehicleResponseDTO.setModel(newVehicle.getModel());
        vehicleResponseDTO.setLicensePlate(newVehicle.getLicensePlate());
        vehicleResponseDTO.setYear(newVehicle.getYear());
        vehicleResponseDTO.setCustomerId(newVehicle.getOwner().getId());
        vehicleResponseDTO.setCustomerName(newVehicle.getOwner().getFirstName() + " " + newVehicle.getOwner().getLastName());
        return ResponseEntity.status(201).body(vehicleResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehicleResponseDTO> vehicleResponseDTO = vehicleService.getAllVehicles(page, size);
        return ResponseEntity.ok(vehicleResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        VehicleResponseDTO vehicleResponseDTO = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicleResponseDTO);
    }

}
