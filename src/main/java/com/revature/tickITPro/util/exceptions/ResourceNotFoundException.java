package com.revature.tickITPro.util.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {}
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
