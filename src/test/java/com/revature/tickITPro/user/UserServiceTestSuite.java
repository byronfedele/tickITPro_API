package com.revature.tickITPro.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTestSuite {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public boolean test_isUserValid_returnTrue_givenValidUser() {

    }

    @Test
    public boolean test_isUserValid_returnTrue_givenValidUser() {

    }
}
