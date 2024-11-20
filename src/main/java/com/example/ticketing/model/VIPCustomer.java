package com.example.ticketing.model;

import com.example.ticketing.util.LoggerService;

public class VIPCustomer extends Customer {

    public VIPCustomer(TicketPool ticketPool, int retrievalRate, LoggerService logger) {
        super(ticketPool, retrievalRate, logger);
    }

    @Override
    public void run() {
        System.out.println("VIP Customer priority enabled!");
        super.run();
    }
}
