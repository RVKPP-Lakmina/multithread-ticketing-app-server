package com.example.ticketing.model;

import com.example.ticketing.util.LoggerService;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final LoggerService logger;

    public Customer(TicketPool ticketPool, int retrievalRate, LoggerService logger) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.logger = logger;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            boolean success = ticketPool.removeTickets(retrievalRate);
            if (success) {
                logger.log(Thread.currentThread().getName() + " purchased " + retrievalRate + " tickets.");
            } else {
                logger.log(Thread.currentThread().getName() + " failed to purchase tickets.");
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
