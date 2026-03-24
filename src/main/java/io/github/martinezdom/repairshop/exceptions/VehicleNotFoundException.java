package io.github.martinezdom.repairshop.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String msg) {
        super(msg);
    }
}
