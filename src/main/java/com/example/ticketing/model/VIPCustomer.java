package com.example.ticketing.model;

import com.example.ticketing.util.LoggerService;

public class VIPCustomer extends Customer {

    public VIPCustomer(ConcurrentTicketStore ticketPool, int retrievalRate, String name, LoggerService logger) {
        super(ticketPool, retrievalRate, name, logger);
    }

    @Override
    public void run() {
        System.out.println("VIP Customer priority enabled!");
        super.run();
    }
}
