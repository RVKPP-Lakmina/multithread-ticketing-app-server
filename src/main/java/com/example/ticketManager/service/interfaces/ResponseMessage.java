package com.example.ticketManager.service.interfaces;

import com.example.ticketing.model.User;

public interface ResponseMessage {

    void message(String message);

    void message(String message, User user);
}
