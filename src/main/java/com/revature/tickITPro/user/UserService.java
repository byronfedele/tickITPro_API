package com.revature.tickITPro.user;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.department.DepartmentService;
import com.revature.tickITPro.ticket.dto.Requests.NewTicketRequest;
import com.revature.tickITPro.user.dto.request.EditUserRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import com.revature.tickITPro.util.exceptions.ResourcePersistanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//services we need: Login, registerUser,readAll, findById, isEmailAvailable,update
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentService departmentService;

    @Autowired
    public UserService(UserRepository userRepository, DepartmentService departmentService) {
        this.userRepository = userRepository;
        this.departmentService = departmentService;
    }

    @Transactional(readOnly = true)
    public UserResponse registerUser(NewUserRequest newUserRequest) throws InvalidUserInputException, ResourcePersistanceException {
        User newUser = new User(newUserRequest);
        newUser.setDepartmentId(departmentService.getDepartment(newUserRequest.getDepartmentId()));
        isUserValid(newUser);
        isEmailAvailable(newUserRequest.getEmail());
        return new UserResponse(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        if (userRepository.checkEmail(email).isPresent())
            throw new InvalidUserInputException("Email: " + email + " already in use. Please log in or create an alternate account with a different email.");
        return true;
    }

    @Transactional
    public User login(String email, String password){
        User user = userRepository.loginCredentialCheck(email, password).orElseThrow(ResourceNotFoundException::new);
        return user;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> readAll() {
        return ((Collection<User>) userRepository.findAll())
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse findById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    @Transactional(readOnly = true)
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
        return user;
    }

    @Transactional
    public void remove(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void update(EditUserRequest editUser) throws InvalidUserInputException {

        User updateUser = userRepository.findById(editUser.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");

        if (notNullOrEmpty.test(editUser.getFName())) updateUser.setFName(editUser.getFName());
        if (notNullOrEmpty.test(editUser.getLName())) updateUser.setLName(editUser.getLName());
        if (notNullOrEmpty.test(editUser.getPassword())) updateUser.setPassword(editUser.getPassword());
        if (notNullOrEmpty.test(editUser.getRole())) updateUser.setRole(User.Role.valueOf(editUser.getRole()));
        if (notNullOrEmpty.test(editUser.getDepartmentId())) updateUser.setDepartmentId(departmentService.getDepartment(editUser.getDepartmentId()));
        if (notNullOrEmpty.test(editUser.getEmail())) {
            isEmailAvailable(editUser.getEmail());
            updateUser.setEmail(editUser.getEmail());
        }
    }

    public boolean areEnumsValid(User user)throws InvalidUserInputException {
        List<String> roleEnums = Arrays.asList("ADMIN", "USER", "IT_PRO");
        List<Boolean> checkRoleEnums = roleEnums.stream().map(str -> str.equals(user.getRole().toString())).collect(Collectors.toList());
        if(!checkRoleEnums.contains(true)){
            throw new InvalidUserInputException(
                    "Role was not a valid entry please try the following : " +
                            roleEnums.stream().map(Object::toString).collect(Collectors.joining(",")) // this will produce all available role enums
            );
        }
        return true;
    }

    public boolean isUserValid(User testUser) {
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");
        if (testUser == null) throw new InvalidUserInputException("Inputted user was null");
        if (testUser.getDepartmentId() == null) throw new InvalidUserInputException("Department associated with inputted user was null");
        if (!notNullOrEmpty.test(testUser.getUserId())) throw new InvalidUserInputException("Inputted userId was empty or null");
        if (!notNullOrEmpty.test(testUser.getEmail())) throw new InvalidUserInputException("Inputted email was empty or null");
        if (!notNullOrEmpty.test(testUser.getFName())) throw new InvalidUserInputException("Inputted first name was empty or null");
        if (!notNullOrEmpty.test(testUser.getLName())) throw new InvalidUserInputException("Inputted last name was empty or null");
        if (!notNullOrEmpty.test(testUser.getPassword())) throw new InvalidUserInputException("Inputted password was empty or null");
        return true;
    }
}