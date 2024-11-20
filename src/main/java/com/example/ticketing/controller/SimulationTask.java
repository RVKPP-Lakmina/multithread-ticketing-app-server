package com.example.ticketing.controller;

import com.example.ticketing.model.Customer;
import com.example.ticketing.model.TicketPool;
import com.example.ticketing.model.Vendor;
import com.example.ticketing.util.LoggerService;
import com.example.ticketing.util.Util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimulationTask {
    private final String VENDOR_PREFIX = "vendor";
    private final String CUSTOMER_PREFIX = "customer";
    private final TicketPool ticketPool;
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Future<?>> vendors;
    private final ConcurrentHashMap<String, Future<?>> customers;
    private final HashMap<String, String> users;
    private LoggerService logger;

    public SimulationTask(int initialTickets, int maxCapacity) {
        this.ticketPool = new TicketPool(initialTickets, maxCapacity);
        this.executorService = Executors.newCachedThreadPool();
        this.vendors = new ConcurrentHashMap<>();
        this.customers = new ConcurrentHashMap<>();
        this.logger = new LoggerService();
        this.users = new HashMap<>();
    }

    public void addVendor(String name, int rate) {
        if (vendors.containsKey(name)) {
            System.out.println("Vendor with ID " + name + " already exists.");
            return;
        }
        Vendor vendor = new Vendor(ticketPool, rate, logger);
        Future<?> future = executorService.submit(vendor);
        String id = this.VENDOR_PREFIX.concat("-".concat(Util.generateUniqueKey()));
        vendors.put(id, future);
        users.put(id, name);
        System.out.println("Vendor added with ID: " + id + ", rate: " + rate + " tickets/sec");
    }

    public void addCustomer(String name, int rate) {
        if (customers.containsKey(name)) {
            System.out.println("Customer with ID " + name + " already exists.");
            return;
        }
        Customer customer = new Customer(ticketPool, rate, logger);
        Future<?> future = executorService.submit(customer);
        String id = this.CUSTOMER_PREFIX.concat("-".concat(Util.generateUniqueKey()));
        customers.put(id, future);
        users.put(id, name);
        System.out.println("Customer added with ID: " + id + ", rate: " + rate + " tickets/sec");
    }

    public void removeVendor(String id) {
        Future<?> future = vendors.remove(id);
        if (future != null) {
            future.cancel(true); // Interrupt the vendor thread
            System.out.println("Vendor with ID " + id + " removed.");
        } else {
            System.out.println("No vendor found with ID " + id);
        }
    }

    public void removeCustomer(String id) {
        Future<?> future = customers.remove(id);
        if (future != null) {
            future.cancel(true); // Interrupt the customer thread
            System.out.println("Customer with ID " + id + " removed.");
        } else {
            System.out.println("No customer found with ID " + id);
        }
    }

    public void stopSimulation() {
        executorService.shutdownNow(); // Interrupt all threads
        System.out.println("Simulation stopped.");
    }

    public void shutdownSystem() {
        if (executorService != null) {
            executorService.shutdownNow();
            logger.stop();
            System.out.println("System shut down.");
        }
    }

    private int getTicketCount() {
        return ticketPool.getTotalTickets();
    }

    private int getPoolTickets() {
        return ticketPool.getPoolTickets();
    }

    public void viewSystemStatus() {
        System.out.println("Total tickets: " + getTicketCount());
        System.out.println("Tickets in pool: " + getPoolTickets());
        System.out.println("Active vendors: " + vendors.size());
        System.out.println("Active customers: " + customers.size());
    }

    public void viewUsers() {
        System.out.println("Users:\n");

        System.out.println("""
                Vendors:
                =========================""");
        viewUserByType(VENDOR_PREFIX);

        System.out.println("");

        System.out.println(
                """
                        Customers:
                        =========================""");
        viewUserByType(CUSTOMER_PREFIX);

    }

    private void viewUserByType(String type) {
        System.out.println(type + "s:");
        users.forEach((id, name) -> {
            if (id.startsWith(type)) {
                System.out.println(type + " ID: " + id + "- Name: " + name);
            }
        });
    }

}