package com.example.ticketing.model;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.implementations.ProducerConsumerThread;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.interfaces.IEventLogger;

public class Customer extends ProducerConsumerThread {
    public Customer(
            ITicketPool ticketPool,
            int retrievalRate,
            String customerName,
            IEventLogger logger) {

        super(
                ticketPool,
                logger,
                retrievalRate,
                customerName);
    }

    public Customer(
            ITicketPool ticketPool,
            int retrievalRate,
            IEventLogger logger,
            boolean isBackEndService, User user

    ) {

        super(
                ticketPool,
                logger,
                retrievalRate,
                user,
                isBackEndService
        );
    }

    @Override
    public void process() {
        ITicketPool ticketPool = this.getTicketPool();
        IEventLogger logger = this.getLogger();
        TicketStatusEnum success = ticketPool.purchaseTickets(getRate());

        switch (success) {
            case FULL:
                processingLogger("Pool is full. Customer " + getName() + " is waiting.");
                break;
            case EMPTY:
                processingLogger("Sold out!");
                Thread.currentThread().interrupt(); // Mark thread as interrupted
                break; // Exit loop
            case ERROR:
                processingLogger("An error occurred while removing tickets.");
                break;
            case SUCCESS:
                processingLogger("Tickets are purchased successfully by " + getName());
                break;
            case NOTENOUGH:
                processingLogger(
                        "Not enough tickets to purchase. Only " + ticketPool.getPoolTickets() + " tickets left.");
                Thread.currentThread().interrupt();
                break;
            default:
                processingLogger("An unexpected error occurred.");
                break;
        }
    }

}
