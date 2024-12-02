package com.example.ticketManager.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.interfaces.ITicketPool;

@Controller
public class WebSocketController {

    // This class is a placeholder for the WebSocketController class
    // in the ticketManager.controller package.
    // You can add code to this class to implement the WebSocketController.
    // For example, you can add methods to handle WebSocket messages
    // and broadcast them to clients.

    // You can also use this class to handle WebSocket subscriptions
    // and publish messages to specific topics.

    // For more information on how to implement a WebSocket controller
    // in Spring Boot, refer to the Spring Framework documentation:
    // https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket

    // For more information on how to use WebSocket in Spring Boot,
    // refer to the Spring Boot documentation:
    // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-websockets
    private final ITicketPool ticketStore;

    public WebSocketController(ITicketPool ticketStore) {
        this.ticketStore = ticketStore;
    }

    @MessageMapping("/add-tickets")
    @SendTo("/topic/ticket-updates")
    public String addTickets(int count) {
        TicketStatusEnum status = ticketStore.addTickets(count);
        return switch (status) {
            case SUCCESS -> "Successfully added " + count + " tickets.";
            case EMPTY -> "No tickets available to add.";
            case ERROR -> "Error occurred while adding tickets.";
            default -> "Unknown status.";
        };
    }

    @MessageMapping("/purchase-tickets")
    @SendTo("/topic/ticket-updates")
    public String purchaseTickets(int count) {
        TicketStatusEnum status = ticketStore.purchaseTickets(count);
        return switch (status) {
            case SUCCESS -> "Successfully purchased " + count + " tickets.";
            case NOTENOUGH -> "Not enough tickets available.";
            case EMPTY -> "No tickets available to purchase.";
            case ERROR -> "Error occurred while purchasing tickets.";
            default -> "Unknown status.";
        };
    }
}
