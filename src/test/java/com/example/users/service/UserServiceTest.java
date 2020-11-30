package com.example.users.service;

import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static com.example.users.common.TestUtils.mockUser;
import static com.example.users.common.TestUtils.mockUsers;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void when_getAllEmployees_thenReturnEmployeeList(){
        Mockito.when(userRepository.findAll()).thenReturn(mockUsers());
        List<User> users = userService.getAllUsers();
        Assertions.assertNotNull(users);

    }
    @Test
    public void when_EmployeeService_thenReturnEmployee(){
        Mockito.when(userRepository.findById("test")).thenReturn(Optional.ofNullable(mockUser()));
        Optional<User> user = userService.getUser("test");
        Assertions.assertNotNull(user);
    }
    @Test
    void when_createEmployee_thenReturnCreatedEmployee(){
        User user = mockUser();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User employee1 = userService.createUser(user);
        Assertions.assertEquals(user.getName(),employee1.getName());
    }
    @Test
    void when_updateEmployee_thenUpdatedEmployee(){
        User user =  mockUser();
        user.setName("test");
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
        User employee1 = userService.updateUser(user);
        Assertions.assertEquals(user.getName(),employee1.getName());
    }
    @Test
    void when_deleteEmployee_thenVerifyDeleteFunCalled(){
        User user =  mockUser();
        user.setId("test");
        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(user);
        userService.deleteUser("test");
        Mockito.verify(userRepository).deleteById("test");
    }

}
