package com.revature.tickITPro.util.web.auth;


import com.revature.tickITPro.user.User;
import com.revature.tickITPro.user.UserService;
import com.revature.tickITPro.util.web.auth.dto.request.LoginCreds;
import com.revature.tickITPro.util.web.auth.dto.response.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    // Dependency Injection
    @Autowired
    public AuthController(UserService userService, TokenService tokenService){
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Principal authorizeUser(@RequestBody LoginCreds loginCreds, HttpServletResponse resp){
        User authUser = userService.login(loginCreds.getEmail(),  loginCreds.getPassword());
        Principal payload = new Principal(authUser);
        String token = tokenService.generateToken(payload);
        resp.setHeader("Authorization",token);
        return payload;
    }

    @DeleteMapping
    public void logout(HttpSession httpSession){
        httpSession.invalidate();
    }

}
