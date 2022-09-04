package com.revature.tickITPro.user;

import com.revature.tickITPro.department.DepartmentService;
import com.revature.tickITPro.user.dto.request.EditUserRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserInputException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import com.revature.tickITPro.util.exceptions.ResourcePersistanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//services we need: Login, registerUser,readAll, findById, isEmailAvailable,update
@Service
public class UserService {

    private final UserRepository userRepository;
//    private final DepartmentService departmentService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse registerUser(NewUserRequest newUserRequest) throws InvalidUserInputException, ResourcePersistanceException {
        User newUser = new User(newUserRequest);
        isEmailAvailable(newUserRequest.getEmail());
        return new UserResponse(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    public void isEmailAvailable(String email) {
        if (userRepository.checkEmail(email).isPresent())
            throw new InvalidUserInputException("Email: " + email + " already in use. Please log in or create an alternate account with a different email.");
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
        if(notNullOrEmpty.test(editUser.getRole())) updateUser.setRole(User.Role.valueOf(editUser.getRole()));
//        if(notNullOrEmpty.test(editUser.getDepartmentId())) updateUser.setDepartmentId(editUser.getDepartmentId());  // waiting for findByDepartmentId from DeparmentSevice
        if (notNullOrEmpty.test(editUser.getEmail())) {
            isEmailAvailable(editUser.getEmail());
            updateUser.setEmail(editUser.getEmail());
        }
    }

    public boolean isUserValid(User testUser) {
        Predicate<String> notNullOrEmpty = (str) -> str != null && !str.trim().equals("");
        return (testUser != null &&
                testUser.getDepartmentId() != null &&
                notNullOrEmpty.test(testUser.getUserId()) &&
                notNullOrEmpty.test(testUser.getEmail()) &&
                notNullOrEmpty.test(testUser.getFName()) &&
                notNullOrEmpty.test(testUser.getLName()) &&
                notNullOrEmpty.test(testUser.getPassword()) &&
                notNullOrEmpty.test(testUser.getRole().toString()));
    }
}