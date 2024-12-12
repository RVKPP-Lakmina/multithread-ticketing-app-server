package com.example.ticketManager.service.implementations;

import com.example.ticketManager.component.WebSocketHandler;

public class ErrorMessage extends WebSocketsMessage {

    public ErrorMessage(WebSocketHandler webSocketHandler) {
        super(webSocketHandler);
        this.setStatus(-1);

    }
}
