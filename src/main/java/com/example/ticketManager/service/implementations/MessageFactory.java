package com.example.ticketManager.service.implementations;


import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.model.MdMessage;
import com.example.ticketManager.util.GlobalLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageFactory {

    private final WebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;
    private int status;

    public MessageFactory(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = new ObjectMapper();
        this.status = 1;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void send(MdMessage message) {

        if (status != 1) {
            message.setStatus(status);
        }

        try {
            this.webSocketHandler.sendMessageToAll(this.objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            GlobalLogger.logError("Json Object Error", e);
        }

    }

}
