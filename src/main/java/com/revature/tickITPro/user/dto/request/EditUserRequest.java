package com.revature.tickITPro.user.dto.request;

import com.revature.tickITPro.util.web.auth.dto.request.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditUserRequest extends EditResourceRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;               // admin should be able to update user roles
    private String departmentId;

}
