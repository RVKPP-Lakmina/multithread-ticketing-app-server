package com.example.ticketManager.model;

import com.example.ticketing.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketsMessage {

    private User user;
    private String message;
    private int pendingTotalTickets;
    private int ticketPoolCapacity;

    public WebSocketsMessage(User user, String message, int pendingTotalTickets, int ticketPoolCapacity) {
        this.user = user;
        this.message = message;
        this.pendingTotalTickets = pendingTotalTickets;
        this.ticketPoolCapacity = ticketPoolCapacity;
    }
}
