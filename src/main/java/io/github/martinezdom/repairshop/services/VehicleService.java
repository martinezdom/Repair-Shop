package io.github.martinezdom.repairshop.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.martinezdom.repairshop.dtos.VehicleCreateDTO;
import io.github.martinezdom.repairshop.entities.Customer;
import io.github.martinezdom.repairshop.entities.Vehicle;
import io.github.martinezdom.repairshop.exceptions.CustomerNotFoundException;
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

    public Vehicle createVehicle(VehicleCreateDTO dto) {
        if (vehicleRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new VehicleAlreadyExistsException("El vehículo con esa matrícula ya existe");
        }
        Optional<Customer> optionalOwner = customerRepository.findById(dto.getCustomerId());
        if (optionalOwner.isEmpty()) {
            throw new CustomerNotFoundException("No se encuentra al dueño del vehículo");
        }
        Customer owner = optionalOwner.get();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setYear(dto.getYear());
        vehicle.setOwner(owner);
        return vehicleRepository.save(vehicle);
    }

}
