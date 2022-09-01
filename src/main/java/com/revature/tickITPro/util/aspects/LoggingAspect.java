package com.revature.tickITPro.util.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger();
    @Pointcut("within(com.revature..*)")
    public void pcLogAll(){};

    @Before("pcLogAll()")
    public void logMethodStart(JoinPoint jp){
        String methodArguments = Arrays.toString(jp.getArgs());
        logger.info("{} successfully invoked at {} with provided arguments: {}", extractClassMethodSignature(jp), LocalDateTime.now(), methodArguments);
    }

    @AfterReturning(pointcut = "pcLogAll()", returning = "returnedObject")
    public void logMethodReturn(JoinPoint jp, Object returnedObject){
        logger.info("{} successfully returned at {} with value: {}", extractClassMethodSignature(jp), LocalDateTime.now(), returnedObject);
    }

    @AfterThrowing(pointcut = "pcLogAll()", throwing = "t")
    public void logMethodThrowable(JoinPoint jp, Throwable t){
        String throwableName = t.getClass().getName();
        logger.warn("{} was thrown in {} at {} with message: {}", throwableName, extractClassMethodSignature(jp), LocalDateTime.now(), t.getMessage());
    }

    private String extractClassMethodSignature(JoinPoint jp){
        return jp.getTarget().getClass().toString() + "#" + jp.getSignature().getName();
    }

}
