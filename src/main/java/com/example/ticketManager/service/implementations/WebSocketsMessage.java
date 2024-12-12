package com.example.ticketManager.service.implementations;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.model.MdMessage;
import com.example.ticketManager.service.interfaces.ResponseMessage;
import com.example.ticketing.model.User;
import com.example.ticketing.stores.Store;

public class WebSocketsMessage extends MessageFactory implements ResponseMessage {

    public WebSocketsMessage(WebSocketHandler webSocketHandler) {
        super(webSocketHandler);
    }

    @Override
    public synchronized void message(String message) {
        MdMessage messageInstance = new MdMessage(null, message, false);
        this.send(messageInstance);
    }

    @Override
    public synchronized void message(String message, User user ) {
        MdMessage messageInstance = new MdMessage(user, message, Store.isIsRunning());
        this.send(messageInstance);
    }
}
