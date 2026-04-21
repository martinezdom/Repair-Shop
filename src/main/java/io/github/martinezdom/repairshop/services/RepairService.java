package io.github.martinezdom.repairshop.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.RepairCreateDTO;
import io.github.martinezdom.repairshop.dtos.RepairResponseDTO;
import io.github.martinezdom.repairshop.dtos.RepairUpdateDTO;
import io.github.martinezdom.repairshop.entities.Repair;
import io.github.martinezdom.repairshop.entities.User;
import io.github.martinezdom.repairshop.entities.Vehicle;
import io.github.martinezdom.repairshop.enums.RepairStatus;
import io.github.martinezdom.repairshop.exceptions.InvalidStatusException;
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

    public RepairResponseDTO createRepair(RepairCreateDTO dto) {
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

        Repair savedRepair = repairRepository.save(repair);

        RepairResponseDTO responseDTO = new RepairResponseDTO();
        responseDTO.setId(savedRepair.getId());
        responseDTO.setCost(savedRepair.getCost());
        responseDTO.setDescription(savedRepair.getDescription());
        responseDTO.setEntryDate(savedRepair.getEntryDate());
        responseDTO.setMechanicName(savedRepair.getMechanic().getUsername());
        responseDTO.setStatus(savedRepair.getStatus());
        responseDTO.setVehicleLicensePlate(savedRepair.getVehicle().getLicensePlate());

        return responseDTO;
    }

    public Page<RepairResponseDTO> getAllRepairs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Repair> repairPage = repairRepository.findAll(pageable);
        return repairPage.map(repair -> {
            RepairResponseDTO dto = new RepairResponseDTO();
            dto.setId(repair.getId());
            dto.setCost(repair.getCost());
            dto.setDescription(repair.getDescription());
            dto.setEntryDate(repair.getEntryDate());
            dto.setMechanicName(repair.getMechanic().getUsername());
            dto.setStatus(repair.getStatus());
            dto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
            return dto;
        });
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

    public RepairResponseDTO updateRepair(Long id, RepairUpdateDTO dto) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()) {
            throw new RepairNotFoundException("Repair not found");
        }
        Repair repair = optionalRepair.get();
        if (dto.getCost() != null) {
            repair.setCost(dto.getCost());
        }
        repair.setStatus(dto.getStatus());
        repairRepository.save(repair);
        RepairResponseDTO repairResponseDto = new RepairResponseDTO();
        repairResponseDto.setId(repair.getId());
        repairResponseDto.setCost(repair.getCost());
        repairResponseDto.setDescription(repair.getDescription());
        repairResponseDto.setEntryDate(repair.getEntryDate());
        repairResponseDto.setMechanicName(repair.getMechanic().getUsername());
        repairResponseDto.setStatus(repair.getStatus());
        repairResponseDto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
        return repairResponseDto;
    }

    public void deleteRepair(Long id) {
        if (!repairRepository.existsById(id)) {
            throw new RepairNotFoundException("Repair not found");
        }
        repairRepository.deleteById(id);
    }

    public Page<RepairResponseDTO> getRepairsByStatus(String statusText, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        RepairStatus statusEnum;

        try {
            statusEnum = RepairStatus.valueOf(statusText.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException("The status: '" + statusText + "' is not valid");
        }

        Page<Repair> repairPage = repairRepository.findByStatus(statusEnum, pageable);

        return repairPage.map(repair -> {
            RepairResponseDTO dto = new RepairResponseDTO();
            dto.setId(repair.getId());
            dto.setCost(repair.getCost());
            dto.setDescription(repair.getDescription());
            dto.setEntryDate(repair.getEntryDate());
            dto.setMechanicName(repair.getMechanic().getUsername());
            dto.setStatus(repair.getStatus());
            dto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
            return dto;
        });
    }

    public Page<RepairResponseDTO> getMyRepairs(Long mechanicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Repair> repairsPage = repairRepository.findByMechanicId(mechanicId, pageable);
        return repairsPage.map(repair -> {
            RepairResponseDTO dto = new RepairResponseDTO();
            dto.setId(repair.getId());
            dto.setCost(repair.getCost());
            dto.setDescription(repair.getDescription());
            dto.setEntryDate(repair.getEntryDate());
            dto.setMechanicName(repair.getMechanic().getUsername());
            dto.setStatus(repair.getStatus());
            dto.setVehicleLicensePlate(repair.getVehicle().getLicensePlate());
            return dto;
        });

    }
}
