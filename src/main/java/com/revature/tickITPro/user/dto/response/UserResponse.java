package com.revature.tickITPro.user.dto.response;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.user.dto.request.NewUserRequest;

import javax.persistence.*;

public class UserResponse {

    private String userId;
    private String email;
    private String password;
    private String fName;
    private String lName;
    private String role;       // this role is coming from the NewUserRequest (careful not to import Java's Role)
    private String departmentId;

    public UserResponse(NewUserRequest newUserRequest, Department department) {
        this.role = newUserRequest.getRole().toString();
//        this.departmentId = department.getDepartmentId()
    }
}
