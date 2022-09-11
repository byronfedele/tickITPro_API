package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentRepository;

import com.revature.tickITPro.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    @NotEmpty(message = "First Name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last Name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please provide a valid email")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(message = "Password must contain a minimum of eight characters, at least one letter, one number, and one special character",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    private String departmentId;

//    public NewUserRequest(String fName, String lName, String email, String password, String departmentId) {
//        this.fName = fName;
//        this.lName = lName;
//        this.email = email;
//        this.password = password;
//        this.departmentId = departmentId;
//    }
}
