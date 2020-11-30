package com.example.users.service;

import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String id){
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User employee){
        return userRepository.save(employee);
    }

    public User updateUser(User employee){
        return userRepository.saveAndFlush(employee);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

}
