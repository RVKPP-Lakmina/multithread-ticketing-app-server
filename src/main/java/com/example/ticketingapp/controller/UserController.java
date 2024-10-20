package com.example.ticketingapp.controller;

import com.example.ticketingapp.exception.InvalidDataException;
import com.example.ticketingapp.model.User;
import com.example.ticketingapp.service.UserService;
import com.example.ticketingapp.util.GlobalLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@RequestBody User user) throws Exception {

        if (user != null) {
            userService.registerUser(user);
            GlobalLogger.logInfo(user.getUsername() + "(" + user.getId() + ")" + " has been registered");
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        throw new InvalidDataException("User cannot be null");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws Exception {
        List<User> users = userService.getRegisteredUsers();

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
