package com.revature.tickITPro.util.aspects;

import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import com.revature.tickITPro.util.exceptions.ResourcePersistanceException;
import com.revature.tickITPro.util.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorExceptionAspect {
    @ExceptionHandler({InvalidUserInputException.class, ResourcePersistanceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleBadRequest(Exception e){
        return "Exception thrown... " + e.getClass().getName() + " " + e.getMessage();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException e){
        return "Exception thrown... " + e.getClass().getName() + " " + e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e){
        e.printStackTrace();
        return "Error occured in program, check log for more detail" + e.getClass().getName() + " with message: " + e.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException ue){
        return "Exception thrown for unauthorized access..." + ue.getClass().getName() + " " + ue.getMessage();
    }
}
