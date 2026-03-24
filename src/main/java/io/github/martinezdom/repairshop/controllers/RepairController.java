package io.github.martinezdom.repairshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.RepairCreateDTO;
import io.github.martinezdom.repairshop.dtos.RepairResponseDTO;
import io.github.martinezdom.repairshop.entities.Repair;
import io.github.martinezdom.repairshop.services.RepairService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/repairs")
public class RepairController {
    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairCreateDTO dto) {
        Repair newRepair = repairService.createRepair(dto);
        RepairResponseDTO repairResponseDTO = new RepairResponseDTO();
        repairResponseDTO.setMechanicName(newRepair.getMechanic().getUsername());
        repairResponseDTO.setDescription(newRepair.getDescription());
        repairResponseDTO.setEntryDate(newRepair.getEntryDate());
        repairResponseDTO.setStatus(newRepair.getStatus());
        repairResponseDTO.setVehicleLicensePlate(newRepair.getVehicle().getLicensePlate());
        repairResponseDTO.setId(newRepair.getId());
        repairResponseDTO.setCost(newRepair.getCost());
        return ResponseEntity.status(201).body(repairResponseDTO);
    }

}
