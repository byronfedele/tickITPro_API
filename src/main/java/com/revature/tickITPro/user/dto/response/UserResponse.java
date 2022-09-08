package com.revature.tickITPro.user.dto.response;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Department departmentId;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole().toString();
        this.departmentId = user.getDepartment();
    }
}