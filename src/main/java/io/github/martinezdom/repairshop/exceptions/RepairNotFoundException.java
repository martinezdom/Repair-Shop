package io.github.martinezdom.repairshop.exceptions;

public class RepairNotFoundException extends RuntimeException {
    public RepairNotFoundException(String msg) {
        super(msg);
    }
}
