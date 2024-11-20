package com.example.ticketing.model;

import com.example.ticketing.util.LoggerService;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;
    private final LoggerService logger;

    public Vendor(TicketPool ticketPool, int releaseRate, LoggerService logger) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.logger = logger;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            boolean success = ticketPool.addTickets(releaseRate);
            if (success) {
                logger.log(Thread.currentThread().getName() + " added " + releaseRate + " tickets.");
            } else {
                logger.log(Thread.currentThread().getName() + " could not add tickets (capacity reached).");
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
