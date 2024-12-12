package com.example.ticketing.repository;

import com.example.ticketing.model.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class UserDataManager {
    private final String userDataFilePath;
    private final ObjectMapper objectMapper;

    public UserDataManager(String userDataFilePath, ObjectMapper objectMapper) {
        this.userDataFilePath = userDataFilePath;
        this.objectMapper = objectMapper;
    }

    public UserData loadUserData() {
        try {
            return objectMapper.readValue(new File(userDataFilePath), UserData.class);
        } catch (IOException e) {
            return new UserData(); // Return empty UserData if file not found or error occurs
        }
    }

    public void saveUserData(UserData userData) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(userDataFilePath), userData);
        } catch (IOException e) {
            System.err.println("Failed to save user data.");
        }
    }
}

