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

    @NotBlank(message = "Please do not leave first name blank")
    private String fName;
    @NotBlank(message = "Please do not leave last name blank")
    private String lName;
    @Email(message = "Please provide a valid email")
    private String email;
    @Pattern(message = "Minimum eight characters, at least one letter, one number and one special character",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;

    private String departmentId;

    public NewUserRequest(String fName, String lName, String email, String password, String departmentId) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
    }
}
