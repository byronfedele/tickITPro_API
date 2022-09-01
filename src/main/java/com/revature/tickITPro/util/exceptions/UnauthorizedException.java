package com.revature.tickITPro.util.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {}
    public UnauthorizedException(String s) {
        super(s);
    }
}
