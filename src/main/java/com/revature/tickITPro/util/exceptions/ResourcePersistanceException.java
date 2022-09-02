package com.revature.tickITPro.util.exceptions;

public class ResourcePersistanceException extends RuntimeException {
    public ResourcePersistanceException() {super("Resource could not be persisted to the database");}
    public ResourcePersistanceException(String s) {
        super(s);
    }
}
