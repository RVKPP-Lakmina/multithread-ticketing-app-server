package com.example.ticketingapp.controller;

import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.threading.Consumer;
import com.example.ticketingapp.threading.MonitorTicketPool;
import com.example.ticketingapp.threading.Vendor;
import com.example.ticketingapp.websocket.TicketPoolWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final Queue<Integer> ticketAdditionQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Integer> ticketPurchaseQueue = new ConcurrentLinkedQueue<>();


    @Autowired
    public TicketController(TicketPoolOperations ticketPool, Vendor producer, Consumer consumer) {
        new Thread(producer).start();
        new Thread(consumer).start();
        TicketPoolWebSocketHandler webSocketHandler = new TicketPoolWebSocketHandler();
        this.monitorTicketPool(webSocketHandler, ticketPool);
    }

    public void monitorTicketPool(TicketPoolWebSocketHandler webSocketHandler, TicketPoolOperations ticketPool) {
        new Thread(() -> {
            while (true) {
                String status = getTicketPoolStatus(ticketPool); // Method to get current status of the ticket pool
                webSocketHandler.broadcast(status);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private String getTicketPoolStatus(TicketPoolOperations ticketPool) {
        // Logic to generate a string representation of the ticket pool status
        return "Current Ticket Pool Status: " + ticketPool.getTicketCount(); // Example output
    }

    @PostMapping("/add")
    public String requestTickets(@RequestParam int count) {
        ticketAdditionQueue.add(count);
        return "Ticket addition request for " + count + " tickets queued.";
    }

    @PostMapping("/buy")
    public String requestBuyTicket(@RequestParam int count) {
        ticketPurchaseQueue.add(count);
        return "Ticket purchase request for " + count + " tickets queued.";
    }
}
