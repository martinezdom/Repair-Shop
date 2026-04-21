package io.github.martinezdom.repairshop.dtos;

import java.math.BigDecimal;

public class DashboardResponseDTO {
    private Long pendingCars;
    private BigDecimal totalRevenue;

    public Long getPendingCars() {
        return pendingCars;
    }

    public void setPendingCars(Long pendingCars) {
        this.pendingCars = pendingCars;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}
