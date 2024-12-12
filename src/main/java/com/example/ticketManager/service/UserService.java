package com.example.ticketManager.service;

import com.example.ticketManager.dao.UserDao;
import com.example.ticketManager.util.exception.InvalidDataException;
import com.example.ticketing.model.User;
import com.example.ticketManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    List<User> cachedUsers = null;

    UserDao userDao;
    @Autowired
    private UserRepository userRepository;

    UserService() {
        this.userDao = new UserDao();
    }

    public List<User> getAllUsers() {

        if (cachedUsers == null) {
            cachedUsers = userRepository.findAll();
        }
        return this.cachedUsers;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        User userWithId = userDao.saveUserLogic(user);
        this.cachedUsers = null;
        return userRepository.save(userWithId);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}