package com.example.ticketing.controller;

import com.example.ticketing.model.Customer;
import com.example.ticketing.model.User;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketing.model.Vendor;
import com.example.ticketing.util.LoggerService;
import com.example.ticketing.util.Util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimulationTask implements TicketingSystemSimulator {
    private static final String VENDOR_PREFIX = "vendor";
    private static final String CUSTOMER_PREFIX = "customer";

    private final ConcurrentHashMap<String, Runnable> taskDefinitions;
    private final ConcurrentTicketStore ticketPool;
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Future<?>> taskMap;
    private final HashMap<String, String> users;
    private final LoggerService logger;
    private boolean isRunning;

    public SimulationTask(int initialTickets, int maxCapacity) {
        this.logger = new LoggerService();
        this.ticketPool = new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
        this.executorService = Executors.newCachedThreadPool();
        this.taskMap = new ConcurrentHashMap<>();
        this.users = new HashMap<>();
        this.isRunning = false;
        this.taskDefinitions = new ConcurrentHashMap<>();

    }

    public void addVendor(String name, int rate) {
        addTask(name, rate, VENDOR_PREFIX, () -> new Vendor(ticketPool, rate, name, logger));
    }

    public void addCustomer(String name, int rate) {
        addTask(name, rate, CUSTOMER_PREFIX, () -> new Customer(ticketPool, rate, name, logger));
    }

    private void addTask(String name, int rate, String prefix, RunnableSupplier taskSupplier) {
        if (users.containsValue(name)) {
            System.out.println(
                    prefix.substring(0, prefix.length() - 1).toUpperCase() + " with name " + name + " already exists.");
            return;
        }
        String id = prefix + "-" + Util.generateUniqueKey();
        Runnable task = taskSupplier.get();
        taskDefinitions.put(id, task); // Store task definition
        users.put(id, name);

        if (isRunning) {
            Future<?> future = executorService.submit(task);
            taskMap.put(id, future); // Start task immediately if running
        }

        System.out.println(prefix.substring(0, prefix.length() - 1).toUpperCase() + " added with ID: " + id + ", rate: "
                + rate + " tickets/sec");
    }

    public void removeVendor(String id) {
        removeTask(id, VENDOR_PREFIX);
    }

    public void removeCustomer(String id) {
        removeTask(id, CUSTOMER_PREFIX);
    }

    private void removeTask(String id, String prefix) {
        Future<?> future = taskMap.remove(id); // Remove active task
        taskDefinitions.remove(id); // Remove task definition for resuming
        users.remove(id); // Remove from user list

        if (future != null) {
            future.cancel(true); // Interrupt the task
            System.out.println(prefix.substring(0, prefix.length() - 1).toUpperCase() + " with ID " + id + " removed.");
        } else {
            System.out.println("No " + prefix.substring(0, prefix.length() - 1).toUpperCase() + " found with ID " + id);
        }
    }

    public void stopSimulation() {
        executorService.shutdownNow(); // Interrupt all threads
        System.out.println("Simulation stopped.");
    }

    public void shutdownSystem() {
        if (executorService != null) {
            taskMap.clear();
            users.clear();
            executorService.shutdownNow();
            logger.stop();
            System.out.println("System shut down.");
        }
    }

    public void viewSystemStatus() {
        System.out.println("Total tickets: " + ticketPool.getTotalTickets());
        System.out.println("Tickets in pool: " + ticketPool.getPoolTickets());
        System.out.println("Active tasks: " + taskMap.size());
    }

    public void viewUsers() {
        System.out.println("Users:");
        System.out.println("""
                Vendors:
                =========================""");
        viewUserByType(VENDOR_PREFIX);

        System.out.println("""
                Customers:
                =========================""");
        viewUserByType(CUSTOMER_PREFIX);
    }

    public void viewUserByType(String type) {
        users.forEach((id, name) -> {
            if (id.startsWith(type)) {
                System.out.println(type + " ID: " + id + " - Name: " + name);
            }
        });
    }

    private interface RunnableSupplier {
        Runnable get();
    }
}
