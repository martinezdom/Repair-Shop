package io.github.martinezdom.repairshop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.VehicleCreateDTO;
import io.github.martinezdom.repairshop.dtos.VehicleResponseDTO;
import io.github.martinezdom.repairshop.dtos.VehicleUpdateDTO;
import io.github.martinezdom.repairshop.services.VehicleService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        VehicleResponseDTO vehicleResponseDTO = vehicleService.createVehicle(dto);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody VehicleUpdateDTO dto) {
        VehicleResponseDTO vehicleResponseDTO = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(vehicleResponseDTO);
    }
}
