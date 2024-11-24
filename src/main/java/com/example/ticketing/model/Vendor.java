package com.example.ticketing.model;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.implementations.ProducerConsumerThread;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.LoggerService;

public class Vendor extends ProducerConsumerThread {

    public Vendor(ITicketPool ticketPool, int releaseRate, String vendorName, LoggerService logger) {
        super(ticketPool, logger, releaseRate, vendorName);
    }

    @Override
    public void process() {
        ITicketPool ticketPool = this.getTicketPool();
        LoggerService logger = this.getLogger();

        TicketStatusEnum success = ticketPool.addTickets(getRate());

        switch (success) {
            case FULL:
                logger.log("Pool is full. Vendors are waiting.");
                break;
            case EMPTY:
                logger.log(
                        "Vendors have No Tickets to Sale!");
                Thread.currentThread().interrupt();
                break;
            case ERROR:
                logger.log("An error occurred while adding tickets.");
                break;
            case SUCCESS:
                logger.log("Tickets are added successfully by " + getName());
                break;
            default:
                logger.log("System Forcefully Stopped!");
                break;
        }
    }
}
