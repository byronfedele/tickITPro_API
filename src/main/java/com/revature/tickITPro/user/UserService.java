package com.revature.tickITPro.user;

import com.revature.tickITPro.user.dto.request.EditUserRequest;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import com.revature.tickITPro.user.dto.response.UserResponse;
import com.revature.tickITPro.util.exceptions.InvalidUserException;
import com.revature.tickITPro.util.exceptions.ResourceNotFoundException;
import com.revature.tickITPro.util.exceptions.ResourcePersistanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//services we need: Login, register,readAll, findById, isEmailAvailable,update
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserResponse newUser(NewUserRequest newUserRequest) throws InvalidUserException, ResourcePersistanceException{
        User newUser = new User(newUserRequest);
        isEmailAvailable(newUserRequest.getEmail());
        return new UserResponse(userRepository.save(newUser));
    }

    @Transactional(readOnly = true)
    public void isEmailAvailable(String email){
        if(userRepository.checkEmail(email).isPresent()) throw new InvalidUserInputExpection("Email: " + email+ " already in use. Please log in or create an alternate account with a different email.")
    }

    @Transactional(readOnly = true)
    public List<UserResponse> readAll(){
        return  ((Collection<User>) userRepository  .findAll())
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse findById(String email){
        User user = userRepository.findById(email).orElseThrow(ResourceNotFoundException::new);
        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }

    @Transactional
    public void remove(String email){
        userRepository.deleteById(email);
    }


    @Transactional
    public void update (EditUserRequest editUser) throws InvalidUserInputExpection{

        User updateUser = userRepository.findById(editUser.getId()).orElseThrow(ResourceNotFoundException::new);
        Predicate<String> notNullOrEmpty = (str) -> != null && !str.trim().equals("");

        if(notNullOrEmpty.test(editUser.getFirstName.())) foundUser.setFullName(editUser.getFirstName());

        if(notNullOrEmpty.test(editUser.getLastName.())) foundUser.setFullName(editUser.getFirstName());

        if(notNullOrEmpty.test(editUser.getPassword.())) foundUser.setPassword(editUser.getFirstName());

        if(notNullOrEmpty.test(editUser.getEmail())){
            isEmailAvailable(editUser.getEmail());
            foundUser.setEmail(editUser.getEmail());
        }