package com.revature.tickITPro.department;

import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Column(nullable = false, name = "department_id")
    private String departmentId;

    @Column(nullable = false, name = "department_name")
    private String departmentName;

    public Department(NewDepartmentRequest newDepartment) {
        this.departmentId = newDepartment.getId();
        this.departmentName = newDepartment.getDepartmentName();
    }
}
