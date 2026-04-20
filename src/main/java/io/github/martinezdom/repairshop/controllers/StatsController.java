package io.github.martinezdom.repairshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.martinezdom.repairshop.dtos.DashboardResponseDTO;
import io.github.martinezdom.repairshop.services.StatsService;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDTO> getStats() {
        DashboardResponseDTO response = statsService.getDashboardStats();
        return ResponseEntity.ok(response);
    }
}