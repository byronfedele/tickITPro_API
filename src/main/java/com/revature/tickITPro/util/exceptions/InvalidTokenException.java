package com.revature.tickITPro.util.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid token provided, could not parse claims");
    }

    public InvalidTokenException(String s) {
        super(s);
    }
}
