package com.revature.tickITPro.department;

import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
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

    @OneToMany(mappedBy="user")
    private List<User> userList;

    public Department(NewDepartmentRequest newDepartment) {
        this.departmentId = newDepartment.getId();
        this.departmentName = newDepartment.getDepartmentName();
    }
}
