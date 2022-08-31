package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentRepository;
import com.revature.tickITPro.department.DepartmentService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class NewUserRequest {

    private String userId;              //Notice: ID will be generated from a combo of random numbers and users' initials
    private String fName;
    private String lName;
    private String email;

//    @Pattern(message = "Minimum eight characters, at least one letter, one number and one special character",regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    private Department department;          // user will choose one of the department from drop-down
    private Role role;
    private DepartmentRepository departmentRepository;

    // this constructor takes in department name as argument to map out the departmentID (which we need for the users table)
    // we will have to use a method from the DepartmentRepository (or service?)
    // to find the departmentID by departmentName (?) (the inverse, I know lol)

    public NewUserRequest(String fName, String lName, String email, String password, DepartmentName departmentName) {

        this.userId = Long.toString(((long) (Math.random() * 10000000001L))) + fName.charAt(0) + lName.charAt(0);
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.department = (Department) departmentRepository.findByDepartmentName(departmentName.toString());        // this would return an iterable, we need to cast it as Department
        this.role = Role.USER;              // everyone will have USER as role upon registration
    }

    // public enums for roles
    public enum Role{

        ADMINISTRATOR, USER, IT_PRO

    }

    // public enums for departments
    public enum DepartmentName{

        HUMAN_RESOURCE, MARKETING,
        SALES, FINANCE, RESEARCH_DEVELOPMENT,
        OPERATIONS_MANAGEMENT, INFORMATION_TECHNOLOGY

    }

}
