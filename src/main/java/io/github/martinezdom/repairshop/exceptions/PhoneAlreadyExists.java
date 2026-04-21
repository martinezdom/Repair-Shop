package io.github.martinezdom.repairshop.exceptions;

public class PhoneAlreadyExists extends RuntimeException {
    public PhoneAlreadyExists(String msg) {
        super(msg);
    }
}
