package com.revature.tickITPro.user.dto.response;

import com.revature.tickITPro.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String email;
    private String fName;
    private String lName;
    private String role;
    private String departmentId;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.fName = user.getFName();
        this.lName = user.getLName();
        this.role = user.getRole().toString();
        this.departmentId = user.getDepartment().getDepartmentId();
    }
}
