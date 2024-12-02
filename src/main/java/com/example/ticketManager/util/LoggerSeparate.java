package com.example.ticketManager.util;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.model.WebSocketsMessage;
import com.example.ticketing.model.User;
import com.example.ticketing.stores.Store;
import com.example.ticketing.util.interfaces.IEventLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class LoggerSeparate implements IEventLogger {

    private final WebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoggerSeparate(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void log(String message) {
        webSocketHandler.sendMessageToAll(message);
    }

    public void log(String message, User user) {

        try {
            WebSocketsMessage socketMessage = new WebSocketsMessage(user, message, Store.getTotalTickets(), Store.ticketsQueue().size());
            webSocketHandler.sendMessageToAll(objectMapper.writeValueAsString(socketMessage));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void error(String message) {
        throw new RuntimeException("Error: " + message);
    }

    @Override
    public void stop() {
        // Do nothing
    }
}
