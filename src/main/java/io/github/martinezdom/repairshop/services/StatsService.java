package io.github.martinezdom.repairshop.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.DashboardResponseDTO;
import io.github.martinezdom.repairshop.enums.RepairStatus;
import io.github.martinezdom.repairshop.repositories.RepairRepository;

@Service
public class StatsService {
    private final RepairRepository repairRepository;

    public StatsService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

public DashboardResponseDTO getDashboardStats() {
        Long totalCars = repairRepository.countByStatus(RepairStatus.PENDIENTE);
        BigDecimal totalRevenue = repairRepository.sumCostByStatus(RepairStatus.TERMINADO);
        
        DashboardResponseDTO dto = new DashboardResponseDTO();
        dto.setPendingCars(totalCars);
        dto.setTotalRevenue(totalRevenue == null ? BigDecimal.ZERO : totalRevenue);
        
        return dto;
    }
}
