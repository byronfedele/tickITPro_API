package com.revature.tickITPro.user;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentService;
import com.revature.tickITPro.department.dto.request.NewDepartmentRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

public class UserServiceTestSuite {

    private static UserService sut;
    private static UserRepository userRepository;
    private static DepartmentService departmentService;

    @BeforeAll
    static void init() {
        userRepository = mock(UserRepository.class);
        departmentService = mock(DepartmentService.class);

        sut = new UserService(userRepository,departmentService);
    }

    @Test
    public void test_isUserValid_returnTrue_givenValidUser() {
        NewUserRequest userRequest = new NewUserRequest("fName","lName","valid@mail.com","GreatPass#33","");
        NewDepartmentRequest departmentRequest = new NewDepartmentRequest("departmentName");
        User validUser = new User(userRequest);
        validUser.setDepartmentId(new Department(departmentRequest));

        boolean actualResult = sut.isUserValid(validUser);

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void test_isUserValid_throwInvalidUserInputException_givenInvalidUser() {
        Department validDepartment = new Department(new NewDepartmentRequest("departmentName"));
        NewUserRequest userRequest = new NewUserRequest("fName","lName","valid@mail.com","GreatPass#33","");

        List<User> invalidUserList = new ArrayList<>();

        User invalidUser1 = null;
        User invalidUser2 = new User(userRequest);
        User invalidUser3 = new User(userRequest);
        invalidUser3.setDepartmentId(validDepartment);
        invalidUser3.setUserId(null);
        User invalidUser4 = new User(userRequest);
        invalidUser4.setDepartmentId(validDepartment);
        invalidUser4.setEmail(null);
        User invalidUser5 = new User(userRequest);
        invalidUser5.setDepartmentId(validDepartment);
        invalidUser5.setFName(null);
        User invalidUser6 = new User(userRequest);
        invalidUser6.setDepartmentId(validDepartment);
        invalidUser6.setLName(null);
        User invalidUser7 = new User(userRequest);
        invalidUser7.setDepartmentId(validDepartment);
        invalidUser7.setPassword(null);
        User invalidUser8 = new User(userRequest);
        invalidUser8.setDepartmentId(validDepartment);
        invalidUser8.setUserId(" ");
        User invalidUser9 = new User(userRequest);
        invalidUser9.setDepartmentId(validDepartment);
        invalidUser9.setEmail(" ");
        User invalidUser10 = new User(userRequest);
        invalidUser10.setDepartmentId(validDepartment);
        invalidUser10.setFName(" ");
        User invalidUser11 = new User(userRequest);
        invalidUser11.setDepartmentId(validDepartment);
        invalidUser11.setLName(" ");
        User invalidUser12 = new User(userRequest);
        invalidUser12.setDepartmentId(validDepartment);
        invalidUser12.setPassword(" ");


        invalidUserList.add(invalidUser1);
        invalidUserList.add(invalidUser2);
        invalidUserList.add(invalidUser3);
        invalidUserList.add(invalidUser4);
        invalidUserList.add(invalidUser5);
        invalidUserList.add(invalidUser6);
        invalidUserList.add(invalidUser7);
        invalidUserList.add(invalidUser8);
        invalidUserList.add(invalidUser9);
        invalidUserList.add(invalidUser10);
        invalidUserList.add(invalidUser11);
        invalidUserList.add(invalidUser12);

        for (User testUser : invalidUserList) {
            try {
                sut.isUserValid(testUser);
                fail("invalid user verified as valid user");
            } catch (InvalidUserInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                fail("Unhandled Exception");
            }
        }
    }

    @Test
    public void test_registerUser_returnsUserResponse_givenValidNewUserRequest() {
        Department validDepartment = new Department(new NewDepartmentRequest("departmentName"));
        NewUserRequest userRequest = new NewUserRequest("fName","lName","valid@mail.com","GreatPass#33", validDepartment.getDepartmentId());
        User validUser = new User(userRequest);
        validUser.setDepartmentId(validDepartment);


        when(userRepository.save(any(User.class))).thenReturn(validUser);
        when(departmentService.getDepartment(anyString())).thenReturn((validDepartment));

        UserResponse userResponse = sut.registerUser(userRequest);

        assertEquals(userRequest.getEmail(),userResponse.getEmail());
        assertEquals(userRequest.getFName(),userResponse.getFName());
        assertEquals(userRequest.getLName(),userResponse.getLName());
        assertEquals(userResponse.getRole().toString(),User.Role.USER.toString());
        assertEquals(validDepartment.getDepartmentId(),userResponse.getDepartmentId());
    }

    @Test
    public void test_registerUser_throwsInvalidUserException_givenInvalidNewUserRequest() {
        Department validDepartment = new Department(new NewDepartmentRequest("departmentName"));
        NewUserRequest invalidUserRequest = new NewUserRequest("","","","", validDepartment.getDepartmentId());

        when(departmentService.getDepartment(anyString())).thenReturn((validDepartment));

        try {
            sut.registerUser(invalidUserRequest);
            fail("Somehow, registerUser processed invalidUserRequest as valid");
        } catch (InvalidUserInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail("Unhandled Exception");
        }
    }

    @Test
    public void test_registerUser_throwsResourceNotFoundException_givenInvalidDepartmentId() {
        NewUserRequest invalidUserRequest = new NewUserRequest("fName","lName","valid@mail.com","GreatPass#33", "wrongId");

        when(departmentService.getDepartment(anyString())).thenThrow(new ResourceNotFoundException());

        try {
            sut.registerUser(invalidUserRequest);
            fail("Somehow, registerUser processed invalidUserRequest as valid");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail("Unhandled Exception");
        }
    }

    @Test
    public void test_isEmailAvailable_returnsTrue_givenAvailableEmail() {

        when(userRepository.checkEmail(anyString())).thenReturn(Optional.empty());

        boolean actualResult = sut.isEmailAvailable("availableEmail");

        assertTrue(actualResult);
    }

    @Test
    public void test_isEmailAvailable_throwsInvalidUserInputException_givenUnavailableEmail() {
        when(userRepository.checkEmail(anyString())).thenReturn(Optional.of(new User()));

        try {
            sut.isEmailAvailable("unavailableEmail");
            fail();
        } catch (InvalidUserInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail("Unhandled Exception");
        }
    }

}
