package io.github.martinezdom.repairshop.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.VehicleCreateDTO;
import io.github.martinezdom.repairshop.dtos.VehicleResponseDTO;
import io.github.martinezdom.repairshop.dtos.VehicleUpdateDTO;
import io.github.martinezdom.repairshop.entities.Customer;
import io.github.martinezdom.repairshop.entities.Vehicle;
import io.github.martinezdom.repairshop.exceptions.CustomerNotFoundException;
import io.github.martinezdom.repairshop.exceptions.VehicleNotFoundException;
import io.github.martinezdom.repairshop.exceptions.VehicleAlreadyExistsException;
import io.github.martinezdom.repairshop.repositories.CustomerRepository;
import io.github.martinezdom.repairshop.repositories.VehicleRepository;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    public VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public VehicleResponseDTO createVehicle(VehicleCreateDTO dto) {
        if (vehicleRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new VehicleAlreadyExistsException("Vehicle with this license plate already exists");
        }
        Optional<Customer> optionalOwner = customerRepository.findById(dto.getCustomerId());
        if (optionalOwner.isEmpty()) {
            throw new CustomerNotFoundException("Vehicle owner not found");
        }
        Customer owner = optionalOwner.get();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setOwner(owner);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        vehicleResponseDTO.setId(savedVehicle.getId());
        vehicleResponseDTO.setBrand(savedVehicle.getBrand());
        vehicleResponseDTO.setModel(savedVehicle.getModel());
        vehicleResponseDTO.setLicensePlate(savedVehicle.getLicensePlate());
        vehicleResponseDTO.setYear(savedVehicle.getYear());
        vehicleResponseDTO.setCustomerId(savedVehicle.getOwner().getId());
        vehicleResponseDTO
                .setCustomerName(savedVehicle.getOwner().getFirstName() + " " + savedVehicle.getOwner().getLastName());
        return vehicleResponseDTO;
    }

    public Page<VehicleResponseDTO> getAllVehicles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);
        return vehiclePage.map(vehicle -> {
            VehicleResponseDTO dto = new VehicleResponseDTO();
            dto.setId(vehicle.getId());
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
            dto.setLicensePlate(vehicle.getLicensePlate());
            dto.setYear(vehicle.getYear());
            if (vehicle.getOwner() != null) {
                dto.setCustomerId(vehicle.getOwner().getId());
                dto.setCustomerName(vehicle.getOwner().getFirstName() + " " + vehicle.getOwner().getLastName());
            }
            return dto;
        });
    }

    public VehicleResponseDTO getVehicleById(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found");
        }
        Vehicle vehicle = optionalVehicle.get();
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setYear(vehicle.getYear());
        if (vehicle.getOwner() != null) {
            dto.setCustomerId(vehicle.getOwner().getId());
            dto.setCustomerName(vehicle.getOwner().getFirstName() + " " + vehicle.getOwner().getLastName());
        }
        return dto;
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle not found");
        }
        vehicleRepository.deleteById(id);
    }

    public VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isEmpty()) {
            throw new VehicleNotFoundException("Vehicle not found");
        }
        Vehicle vehicle = optionalVehicle.get();
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        vehicleResponseDTO.setBrand(savedVehicle.getBrand());
        vehicleResponseDTO.setCustomerId(savedVehicle.getOwner().getId());
        vehicleResponseDTO.setCustomerName(savedVehicle.getOwner().getFirstName() + " " + savedVehicle.getOwner().getLastName());
        vehicleResponseDTO.setId(savedVehicle.getId());
        vehicleResponseDTO.setLicensePlate(savedVehicle.getLicensePlate());
        vehicleResponseDTO.setModel(savedVehicle.getModel());
        vehicleResponseDTO.setYear(savedVehicle.getYear());
        return vehicleResponseDTO;
    }
}
