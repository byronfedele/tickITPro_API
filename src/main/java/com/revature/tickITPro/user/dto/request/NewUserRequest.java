package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentRepository;

import com.revature.tickITPro.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor

public class NewUserRequest {

    private String userId;
    private String fName;
    private String lName;
    private String email;

//    @Pattern(message = "Minimum eight characters, at least one letter, one number and one special character",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    private User.Role role;

    public NewUserRequest(String fName, String lName, String email, String password) {
        this.userId = UUID.randomUUID().toString();
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.role = User.Role.USER;
    }
}
