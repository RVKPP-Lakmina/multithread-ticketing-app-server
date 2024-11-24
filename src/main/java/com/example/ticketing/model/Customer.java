package com.example.ticketing.model;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.implementations.ProducerConsumerThread;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.LoggerService;

public class Customer extends ProducerConsumerThread {

    public Customer(
            ITicketPool ticketPool,
            int retrievalRate,
            String customerName,
            LoggerService logger) {

        super(
                ticketPool,
                logger,
                retrievalRate,
                customerName);
    }

    @Override
    public void process() {
        ITicketPool ticketPool = this.getTicketPool();
        LoggerService logger = this.getLogger();
        TicketStatusEnum success = ticketPool.purchaseTickets(getRate());

        switch (success) {
            case FULL:
                logger.log("Pool is full. Customer " + getName() + " is waiting.");
                break;
            case EMPTY:
                logger.log("Sold out!");
                Thread.currentThread().interrupt(); // Mark thread as interrupted
                break; // Exit loop
            case ERROR:
                logger.log("An error occurred while removing tickets.");
                break;
            case SUCCESS:
                logger.log("Tickets are purchased successfully by " + getName());
                break;
            case NOTENOUGH:
                logger.log(
                        "Not enough tickets to purchase. Only " + ticketPool.getPoolTickets() + " tickets left.");
                Thread.currentThread().interrupt();
                break;
            default:
                logger.log("An unexpected error occurred.");
                break;
        }
    }

}
