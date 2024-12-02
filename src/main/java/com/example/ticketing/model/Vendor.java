package com.example.ticketing.model;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.implementations.ProducerConsumerThread;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.interfaces.IEventLogger;

public class Vendor extends ProducerConsumerThread {

    public Vendor(ITicketPool ticketPool, int releaseRate, String vendorName, IEventLogger logger) {
        super(ticketPool, logger, releaseRate, vendorName);
    }

    public Vendor(ITicketPool ticketPool, int releaseRate,
                  IEventLogger logger,
                  boolean isBackEndService, User user
    ) {
        super(ticketPool, logger, releaseRate, user, isBackEndService);
    }


    @Override
    public void process() {
        ITicketPool ticketPool = this.getTicketPool();
        TicketStatusEnum success = ticketPool.addTickets(getRate());

        switch (success) {
            case FULL:
                processingLogger("Pool is full. Vendors are waiting.");
                break;
            case EMPTY:
                processingLogger(
                        "Vendors have No Tickets to Sale!");
                Thread.currentThread().interrupt();
                break;
            case ERROR:
                processingLogger("An error occurred while adding tickets.");
                break;
            case SUCCESS:
                processingLogger("Tickets are added successfully by " + getName());
                break;
            default:
                processingLogger("System Forcefully Stopped!");
                break;
        }
    }
}
