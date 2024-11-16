package com.example.ticketingapp.controller;

import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.threading.Consumer;
import com.example.ticketingapp.threading.Vendor;
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
