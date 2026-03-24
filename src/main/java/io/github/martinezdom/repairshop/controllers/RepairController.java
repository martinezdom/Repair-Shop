package io.github.martinezdom.repairshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.RepairCreateDTO;
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
        return ResponseEntity.status(201).body(newRepair);
    }

}
