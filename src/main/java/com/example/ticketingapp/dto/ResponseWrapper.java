package com.example.ticketingapp.dto;

import com.example.ticketingapp.model.User;

public class ResponseWrapper {

    private User user;
    private String message;

    public ResponseWrapper() {

    }

    public ResponseWrapper(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public ResponseWrapper(User user) {
        this.user = user;
    }

    public ResponseWrapper(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
