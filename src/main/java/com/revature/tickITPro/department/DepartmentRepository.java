package com.revature.tickITPro.department;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {

    // this method was TEMPORARILY created just to call it in the NewUserRequest (see that class).
    // may need to be modified (it is taking a department name to find the department ID (not sure if it'll work)).
    @Query(value = "FROM Department WHERE department_name = :departmentName")
    Optional<Department> findByDepartmentName(String departmentName);
}
