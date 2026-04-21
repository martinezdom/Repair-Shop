package io.github.martinezdom.repairshop.exceptions;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists(String msg) {
        super(msg);
    }
}
