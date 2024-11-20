package com.example.ticketing.controller;

import com.example.ticketing.model.Customer;
import com.example.ticketing.model.TicketPool;
import com.example.ticketing.model.VIPCustomer;
import com.example.ticketing.model.Vendor;
import com.example.ticketing.util.LoggerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketSystemController {
    private TicketPool ticketPool;
    private LoggerService logger;
    private ExecutorService executorService;

    public void initializeSystem(int totalTickets, int maxCapacity) {
        this.ticketPool = new TicketPool(totalTickets, maxCapacity);
        this.logger = new LoggerService();
        this.executorService = Executors.newCachedThreadPool();
    }

    public void addVendor(int releaseRate) {
        Vendor vendor = new Vendor(ticketPool, releaseRate, logger);
        executorService.submit(vendor);
        System.out.println("Vendor added with rate: " + releaseRate + " tickets/sec");
    }

    public void addCustomer(int retrievalRate, boolean isVIP) {
        Customer customer = isVIP ? new VIPCustomer(ticketPool, retrievalRate, logger)
                : new Customer(ticketPool, retrievalRate, logger);
        executorService.submit(customer);
        System.out.println("Customer added with rate: " + retrievalRate + " tickets/sec");
    }

    public void shutdownSystem() {
        if (executorService != null) {
            executorService.shutdownNow();
            logger.stop();
            System.out.println("System shut down.");
        }
    }

    public int getTicketCount() {
        return ticketPool.getTotalTickets();
    }

    public int getPoolTickets() {
        return ticketPool.getPoolTickets();
    }
}
