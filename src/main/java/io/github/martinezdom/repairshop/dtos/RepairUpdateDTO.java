package io.github.martinezdom.repairshop.dtos;

import java.math.BigDecimal;

import io.github.martinezdom.repairshop.enums.RepairStatus;
import jakarta.validation.constraints.NotNull;

public class RepairUpdateDTO {
    private BigDecimal cost;
    @NotNull
    private RepairStatus status;

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public RepairStatus getStatus() {
        return status;
    }

    public void setStatus(RepairStatus status) {
        this.status = status;
    }

}
