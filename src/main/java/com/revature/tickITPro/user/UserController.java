package com.revature.tickITPro.user;

import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.user.dto.request.EditUserRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @GetMapping
    @Secured(isAdmin = true)
    public List<UserResponse> findAll(){return userService.readAll();}

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable String id){return userService.findById(id);}

    @GetMapping({"/query"})
    public UserResponse findByIdQuery(@RequestParam String id){return userService.findById(id);}
//changed pom file to version match for @Valid annotation
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid NewUserRequest newRegistrationRequest){
        return userService.registerUser(newRegistrationRequest);
    }

    @PutMapping
    public String update(@RequestBody EditUserRequest editUserRequest){
        userService.update(editUserRequest);
        return "User Update Applied";
    }
}
