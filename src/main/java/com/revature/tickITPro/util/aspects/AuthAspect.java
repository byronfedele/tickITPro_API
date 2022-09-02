package com.revature.tickITPro.util.aspects;

import com.revature.tickITPro.user.User;
import com.revature.tickITPro.util.exceptions.UnauthorizedException;
import com.revature.tickITPro.util.web.Secured;
import com.revature.tickITPro.util.web.auth.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthAspect {

    private final TokenService tokenService;

    @Autowired
    public AuthAspect(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Order(1)
    @Around("@annotation(com.revature.tickITPro.util.web.Secured)")
    public Object securedEndpoint(ProceedingJoinPoint pjp) throws Throwable {
        // getting the signature, we know it's a MethodSignature due to our annotation Target so we can cast it and then just get the method
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Secured annotation = method.getAnnotation(Secured.class);

        String token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getHeader("Authorization");
        if (!tokenService.isTokenValid(token)) throw new UnauthorizedException("No Authorization token found");
        if (annotation.isAdmin() && !tokenService.extractTokenDetails(token).isAdmin()) throw new UnauthorizedException("Authorized Token is not an Admin, please login with an Admin account to perform this request");

        // List<String> allowedUsers = Arrays.asList(annotation.allowedUsers());
        // HttpSession session = req.getSession(false);

        // if(session == null) throw new UnauthorizedException("No Session available");
        // if(annotation.isLoggedIn() && session.getAttribute("authUser") == null) throw new UnauthorizedException("Please log in before calling this endpoint");

        // User user = (User) session.getAttribute("authUser");

        // if(!allowedUsers.isEmpty() && !allowedUsers.contains(user.getEmail())) throw new UnauthorizedException("Forbidden request made to sensitive endpoint by user: " + user.getEmail());
        // if(annotation.isAdmin() && user.getRole().equals("ADMIN")==false) throw new UnauthorizedException("Forbidden request made to admin endpoint by user: " + user.getEmail());

        return pjp.proceed(); // all this is doing is returning that JoinPoint to execute the method following the Annotation
    }

}