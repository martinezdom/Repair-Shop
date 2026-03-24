package io.github.martinezdom.repairshop.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.github.martinezdom.repairshop.enums.RepairStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "repairs")
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RepairStatus status;
    @Column(nullable = false)
    private LocalDateTime entryDate;
    @Column
    private LocalDateTime exitDate;
    @Column
    private BigDecimal cost;
    @JoinColumn(name = "vehicle_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vehicle vehicle;
    @JoinColumn(name = "mechanic_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User mechanic;

    public Repair() {

    }

    public Repair(String description, RepairStatus status, LocalDateTime entryDate, LocalDateTime exitDate,
            BigDecimal cost, Vehicle vehicle, User mechanic) {
        this.description = description;
        this.status = status;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.cost = cost;
        this.vehicle = vehicle;
        this.mechanic = mechanic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RepairStatus getStatus() {
        return status;
    }

    public void setStatus(RepairStatus status) {
        this.status = status;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        this.exitDate = exitDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getMechanic() {
        return mechanic;
    }

    public void setMechanic(User mechanic) {
        this.mechanic = mechanic;
    }

}
