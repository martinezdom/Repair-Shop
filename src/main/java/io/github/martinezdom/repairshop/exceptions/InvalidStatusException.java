package io.github.martinezdom.repairshop.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String msg) {
        super(msg);
    }
}
