package com.example.ticketManager.component;

import com.example.ticketManager.component.implementations.ActionHandlerFactory;
import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;
import com.example.ticketManager.util.Logger;
import com.example.ticketing.util.interfaces.IEventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final TicketingService ticketingService;
    private final IEventLogger logger;

    @Autowired
    WebSocketHandler(@Lazy TicketingService ticketingService) {
        this.ticketingService = ticketingService;
        logger = new Logger(this);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session); // Add the session to the active sessions set
        logger.log("Connected! HandShake!!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Message received: " + payload);

        String[] parts = payload.split(":", 2);
        if (parts.length < 2) {
            logger.error("Invalid message format");
            return;
        }

        String action = parts[0];
        String[] params = parts[1].split(",");

        ActionHandler actionHandler = ActionHandlerFactory.create(action, ticketingService, logger);

        if (actionHandler != null) {
            actionHandler.handle(params);
        } else {
            logger.error("Unknown action: " + action);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session); // Remove the session when the connection is closed
        System.out.println("Connection closed: " + session.getId());
    }

    public void sendMessageToAll(String message) {
        synchronized (sessions) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}