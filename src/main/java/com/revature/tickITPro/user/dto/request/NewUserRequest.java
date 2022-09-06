package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentRepository;

import com.revature.tickITPro.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewUserRequest {

    @NotBlank(message = "First Name cannot be blank")
    private String fName;
    @NotBlank(message = "Last Name cannot be blank")
    private String lName;
    @Email(message = "Please provide a valid email")
    private String email;
    @Pattern(message = "Password must contain a minimum of eight characters, at least one letter, one number, and one special character",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    @NotBlank(message = "department ID cannot be blank")
    private String departmentId;

    public NewUserRequest(String fName, String lName, String email, String password, String departmentId) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
    }
}
