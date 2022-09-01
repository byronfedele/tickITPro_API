package com.revature.tickITPro.util.web;


import com.revature.tickITPro.user.User;
import com.revature.tickITPro.user.UserService;
import com.revature.tickITPro.util.web.dto.LoginCreds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    // Dependency Injection
    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authorizeUser(@RequestBody LoginCreds loginCreds, HttpSession httpSession){
        User authUser = userService.login(loginCreds.getEmail(),  loginCreds.getPassword());
        httpSession.setAttribute("authUser", authUser);
    }

    @DeleteMapping
    public void logout(HttpSession httpSession){
        httpSession.invalidate();
    }

}
