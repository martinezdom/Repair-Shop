package io.github.martinezdom.repairshop.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.RepairCreateDTO;
import io.github.martinezdom.repairshop.dtos.RepairResponseDTO;
import io.github.martinezdom.repairshop.entities.Repair;
import io.github.martinezdom.repairshop.entities.User;
import io.github.martinezdom.repairshop.entities.Vehicle;
import io.github.martinezdom.repairshop.enums.RepairStatus;
import io.github.martinezdom.repairshop.exceptions.RepairNotFoundException;
import io.github.martinezdom.repairshop.exceptions.UserNotFoundException;
import io.github.martinezdom.repairshop.exceptions.VehicleNotFoundException;
import io.github.martinezdom.repairshop.repositories.RepairRepository;
import io.github.martinezdom.repairshop.repositories.UserRepository;
import io.github.martinezdom.repairshop.repositories.VehicleRepository;

@Service
public class RepairService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final RepairRepository repairRepository;

    public RepairService(VehicleRepository vehicleRepository, UserRepository userRepository,
            RepairRepository repairRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.repairRepository = repairRepository;
    }

    public Repair createRepair(RepairCreateDTO dto) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(dto.getVehicleId());
        if (optionalVehicle.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found");
        }
        Optional<User> optionalMechanic = userRepository.findById(dto.getMechanicId());
        if (optionalMechanic.isEmpty()) {
            throw new UserNotFoundException("Mechanic not found");
        }
        Vehicle vehicle = optionalVehicle.get();
        User mechanic = optionalMechanic.get();
        Repair repair = new Repair();
        repair.setDescription(dto.getDescription());
        repair.setVehicle(vehicle);
        repair.setMechanic(mechanic);
        repair.setStatus(RepairStatus.PENDIENTE);
        repair.setEntryDate(LocalDateTime.now());
        return repairRepository.save(repair);
    }

    public List<RepairResponseDTO> getAllRepairs() {
        List<Repair> repairs = repairRepository.findAll();
        List<RepairResponseDTO> responseDtos = new ArrayList<>();
        for (Repair repair : repairs) {
            RepairResponseDTO dto = new RepairResponseDTO();
            dto.setId(repair.getId());
            dto.setCost(repair.getCost());
            dto.setDescription(repair.getDescription());
            dto.setEntryDate(repair.getEntryDate());
            dto.setMechanicName(repair.getMechanic().getUsername());
            dto.setStatus(repair.getStatus());
            dto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
            responseDtos.add(dto);
        }
        return responseDtos;
    }

    public RepairResponseDTO getRepairById(Long id) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()) {
            throw new RepairNotFoundException("Repair not found");
        }
        Repair repair = optionalRepair.get();
        RepairResponseDTO dto = new RepairResponseDTO();
        dto.setId(repair.getId());
        dto.setCost(repair.getCost());
        dto.setDescription(repair.getDescription());
        dto.setEntryDate(repair.getEntryDate());
        dto.setMechanicName(repair.getMechanic().getUsername());
        dto.setStatus(repair.getStatus());
        dto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
        return dto;
    }
}
