package com.revature.tickITPro.util.exceptions;

public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException() {}
    public InvalidUserInputException(String s) {
        super(s);
    }
}
