package com.revature.tickITPro.department.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewDepartmentRequest {

    @NotBlank(message = "Department Name cannot be blank")
    private String departmentName;

    public NewDepartmentRequest(String departmentName) {
        this.departmentName = departmentName;
    }
}
