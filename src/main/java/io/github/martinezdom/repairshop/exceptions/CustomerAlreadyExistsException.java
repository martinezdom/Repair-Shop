package io.github.martinezdom.repairshop.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String msg) {
        super(msg);
    }
}
