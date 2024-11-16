package com.example.ticketingapp.service.interfaces;

import com.example.ticketingapp.model.User;

import java.util.List;

public interface UserService {

    public User registerUser(User user) throws Exception;

    public List<User> getRegisteredUsers() throws Exception;
}
