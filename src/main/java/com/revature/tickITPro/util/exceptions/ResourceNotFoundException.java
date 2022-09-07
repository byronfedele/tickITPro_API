package com.revature.tickITPro.util.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {super("Requested Resource could not be found");}
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
