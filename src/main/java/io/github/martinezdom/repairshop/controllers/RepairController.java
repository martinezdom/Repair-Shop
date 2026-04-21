package io.github.martinezdom.repairshop.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.RepairCreateDTO;
import io.github.martinezdom.repairshop.dtos.RepairResponseDTO;
import io.github.martinezdom.repairshop.dtos.RepairUpdateDTO;
import io.github.martinezdom.repairshop.entities.User;
import io.github.martinezdom.repairshop.services.RepairService;
import jakarta.validation.Valid;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/repairs")
public class RepairController {
    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RepairCreateDTO dto) {
        RepairResponseDTO responseDTO = repairService.createRepair(dto); 
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<RepairResponseDTO> repairsDtoList = repairService.getAllRepairs(page, size);
        return ResponseEntity.ok(repairsDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        RepairResponseDTO repairResponseDto = repairService.getRepairById(id);
        return ResponseEntity.ok(repairResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody RepairUpdateDTO dto, @PathVariable Long id) {
        RepairResponseDTO repairResponseDTO = repairService.updateRepair(id, dto);
        return ResponseEntity.ok(repairResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<RepairResponseDTO> repairResponseDTO = repairService.getRepairsByStatus(status, page, size);
        return ResponseEntity.ok(repairResponseDTO);
    }

    @GetMapping("/my-repairs")
    public ResponseEntity<?> getMyRepairs(@AuthenticationPrincipal User currentUser, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<RepairResponseDTO> repairResponseDTO = repairService.getMyRepairs(currentUser.getId(), page, size);
        return ResponseEntity.ok(repairResponseDTO);
    }

}
