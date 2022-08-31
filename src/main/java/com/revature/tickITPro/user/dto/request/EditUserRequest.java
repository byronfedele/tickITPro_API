package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.department.Department;

import javax.persistence.*;

public class EditUserRequest {

    private String email;
    private String password;
    private String fName;
    private String lName;
    private NewUserRequest.Role role;       // this role is coming from the NewUserRequest (careful not to import Java's Role)
    private Department departmentId;

}
