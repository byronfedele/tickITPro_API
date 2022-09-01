package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.util.web.dto.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditUserRequest extends EditResourceRequest {

    private String email;
    private String password;
    private String fName;
    private String lName;
    private String role;               // admin should be able to update user roles
    private String departmentId;

}
