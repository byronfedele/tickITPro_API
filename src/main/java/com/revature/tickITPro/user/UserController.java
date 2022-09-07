package com.revature.tickITPro.user;

import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.user.dto.request.EditUserRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(exposedHeaders = "Authorization")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping("/all")
    @Secured(isAdmin = true)
    public List<UserResponse> findAll() {
        return userService.readAll();
    }

    @GetMapping("/{id}")
    @Secured(isAdmin = true)
    public UserResponse findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @GetMapping
    @Secured
    public UserResponse findCurrentUser(){
        User sessionUser = userService.getSessionUser();

        if (sessionUser != null) {
            return userService.findById(sessionUser.getUserId());
        } else {
            throw new InvalidUserInputException("You must be logged in to view your account");
        }
    }
//changed pom file to version match for @Valid annotation
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid NewUserRequest newRegistrationRequest){
        return userService.registerUser(newRegistrationRequest);
    }

    // Users should only be able to update their own accounts
    @PutMapping
    @Secured
    public UserResponse updateSessionUser(@RequestBody EditUserRequest editUserRequest){
        User sessionUser = userService.getSessionUser();

        if (sessionUser != null) {
            editUserRequest.setId(sessionUser.getUserId());
            return userService.update(editUserRequest);
        } else {
            throw new InvalidUserInputException("You must be logged in to update your account");
        }
    }

    // Users that aren't ADMIN should only be able to delete their own accounts
    @DeleteMapping
    @Secured
    public String deleteSessionUser(HttpServletResponse resp) {
        User sessionUser = userService.getSessionUser();

        if (sessionUser != null) {
            userService.remove(sessionUser.getUserId());
            userService.logout();
            resp.setHeader("Authorization",null);
            return "Your account has been deleted and you have been logged out.";
        } else {
            return "You must be logged in to delete your account";
        }

    }

    // Admin Users can delete any account, specified in the path variable
    @DeleteMapping("/{id}")
    @Secured(isAdmin = true)
    public String deleteById(@PathVariable String id) {
        userService.remove(id);
        return "User with id \'" + id + "\' has been deleted";
    }
}
