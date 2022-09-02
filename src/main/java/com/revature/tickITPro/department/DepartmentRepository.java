package com.revature.tickITPro.department;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {

    // this method takes in a department name, returns a Department (both name and id)
    @Query(value = "FROM Department WHERE department_name = :departmentName")
    Optional<Department> findByDepartmentName(String departmentName);
}
