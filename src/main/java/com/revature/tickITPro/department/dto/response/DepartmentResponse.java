package com.revature.tickITPro.department.dto.response;

import com.revature.tickITPro.department.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentResponse {

    String departmentId;
    String departmentName;

    public DepartmentResponse(Department department) {
        this.departmentId = department.getDepartmentId();
        this.departmentName = department.getDepartmentName();
    }
}
