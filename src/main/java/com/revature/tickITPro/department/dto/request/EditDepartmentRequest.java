package com.revature.tickITPro.department.dto.request;

import com.revature.tickITPro.util.web.dto.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditDepartmentRequest extends EditResourceRequest {
    private String departmentName;
}
