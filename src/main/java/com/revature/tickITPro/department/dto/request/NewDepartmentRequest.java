package com.revature.tickITPro.department.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewDepartmentRequest {

    private String departmentId;

    @NotBlank(message = "Please enter a department name")
    private String departmentName;

    public NewDepartmentRequest(String departmentName) {
        this.departmentId = UUID.randomUUID().toString();
        this.departmentName = departmentName;
    }
}
