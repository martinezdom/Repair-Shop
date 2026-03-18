package io.github.martinezdom.repairshop.exceptions;

public class VehicleAlreadyExistsException extends RuntimeException {
    public VehicleAlreadyExistsException(String msg) {
        super(msg);
    }
}
