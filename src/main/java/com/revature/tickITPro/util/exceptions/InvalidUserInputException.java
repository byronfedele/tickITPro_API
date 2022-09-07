package com.revature.tickITPro.util.exceptions;

public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException() {super("Invalid User input");}
    public InvalidUserInputException(String s) {
        super(s);
    }
}
