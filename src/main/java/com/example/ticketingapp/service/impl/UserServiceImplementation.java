package com.example.ticketingapp.service.impl;

import com.example.ticketingapp.exception.InvalidDataException;
import com.example.ticketingapp.model.User;
import com.example.ticketingapp.repository.UserRepository;
import com.example.ticketingapp.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        if (user != null) {
            User existingUser = userRepository.findByUsername(user.getUsername());

            if (existingUser != null) {
                throw new InvalidDataException("Username already exists");
            }

            return userRepository.save(user);
        }

        throw new InvalidDataException("User is null");
    }

    @Override
    public List<User> getRegisteredUsers() throws Exception {
        List<User> users = userRepository.findAll();

        if (users != null) {
            return users;
        }
        throw new Exception("User is null");
    }
}
