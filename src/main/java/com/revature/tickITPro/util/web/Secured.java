package com.revature.tickITPro.util.web;
import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secured {
    boolean isAdmin() default false;
    boolean isLoggedIn() default true;
    String[] allowedUsers() default {};
}