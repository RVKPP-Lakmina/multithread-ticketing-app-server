package com.example.ticketManager.util;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.service.implementations.ErrorMessage;
import com.example.ticketManager.service.implementations.WebSocketsMessage;
import com.example.ticketManager.service.interfaces.ResponseMessage;
import com.example.ticketing.model.User;
import com.example.ticketing.stores.Store;
import com.example.ticketing.util.interfaces.IEventLogger;

public abstract class LoggerSeparate implements IEventLogger {

    ResponseMessage responseMessage;
    ResponseMessage responseErrMessage;

    public LoggerSeparate(WebSocketHandler webSocketHandler) {
        this.responseMessage = new WebSocketsMessage(webSocketHandler);
        responseErrMessage = new ErrorMessage(webSocketHandler);
    }

    @Override
    public void log(String message) {
        responseMessage.message(message);
    }

    public void log(String message, User user) {
        responseMessage.message(message, user);
    }

    @Override
    public void error(String message) {
        responseErrMessage.message(message);
    }

    @Override
    public void stop(){}
}
