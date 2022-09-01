package com.revature.tickITPro.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(value = "FROM User where email = :email")
    Optional<User> checkEmail(String email);

    @Query(value = "FROM User where email = :email AND password = :password")
    Optional<User> loginCredentialCheck(String email, String password);


}
