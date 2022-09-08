package com.revature.tickITPro.department;

import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.user.User;
import com.revature.tickITPro.user.UserRepository;
import com.revature.tickITPro.user.UserService;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentServiceTestSuite {

    private static DepartmentService departmentService;
    private static DepartmentRepository departmentRepository;

    @BeforeAll
    static void init() {
        departmentRepository = mock(DepartmentRepository.class);
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    public void test_isDepartmentValid(){
        NewDepartmentRequest departmentRequest = new NewDepartmentRequest("departmentName");
        Department validDepartment = new Department(departmentRequest);

        boolean actualResult = departmentService.isDepartmentValid(validDepartment);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void test_isDepartmentAvailable(){
        when(departmentRepository.findByDepartmentName(anyString())).thenReturn(Optional.empty());
        boolean actualResult = departmentService.isDepartmentAvailable("availableDepartment");

        assertTrue(actualResult);
    }


}
