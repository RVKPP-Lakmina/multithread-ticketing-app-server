package com.example.ticketing.controller;

import com.example.ticketing.model.Customer;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketing.model.User;
import com.example.ticketing.model.Vendor;
import com.example.ticketing.stores.Store;
import com.example.ticketing.util.LoggerService;
import com.example.ticketing.util.Util;
import com.example.ticketing.util.interfaces.IEventLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimulationTask implements TicketingSystemSimulator {
    private static final String VENDOR_PREFIX = "vendor";
    private static final String CUSTOMER_PREFIX = "customer";

    private final ConcurrentHashMap<String, Runnable> taskDefinitions;
    private final ConcurrentTicketStore ticketPool;
    private ExecutorService executorService;
    private final ConcurrentHashMap<String, Future<?>> taskMap;
    private final HashMap<String, String> users;
    private final IEventLogger logger;
    private boolean isRunning;

    public SimulationTask(int initialTickets, int maxCapacity) {
        this(initialTickets, maxCapacity, new LoggerService());
    }

    public SimulationTask(int initialTickets, int maxCapacity, IEventLogger logger) {
        this.logger = logger;
        this.ticketPool = new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
        this.executorService = Executors.newCachedThreadPool();
        this.taskMap = new ConcurrentHashMap<>();
        this.users = new HashMap<>();
        this.isRunning = false;
        this.taskDefinitions = new ConcurrentHashMap<>();
    }

    public void startTask() {
        if (isRunning) {
            System.out.println("Simulation is already running.");
            return;
        }
        executorService = Executors.newCachedThreadPool();

        if (users.isEmpty()) {
            logger.log("No Available active Users.");
        }

        isRunning = true;

        // Resume tasks
        taskDefinitions.forEach((id, task) -> {
            Future<?> future = executorService.submit(task);
            taskMap.put(id, future);
        });

        System.out.println("Simulation started. Resumed all pending tasks.");
    }

    public void stopTask() {
        if (!isRunning) {
            System.out.println("Simulation is not running.");
            return;
        }
        taskMap.forEach((id, future) -> future.cancel(true)); // Stop all active tasks
        taskMap.clear(); // Keep task definitions for resuming
        executorService.shutdownNow(); // Shut down the executor service
        executorService = null;
        isRunning = false;
        System.out.println("Simulation stopped. All tasks paused.");
    }

    public void addVendor(String name, int rate) {
        User user = createUser(name, VENDOR_PREFIX, rate);
        assert user != null;
        addTask(user.getId(), () -> new Vendor(ticketPool, rate, logger, true, user));
    }

    public void addCustomer(String name, int rate) {
        User user = createUser(name, CUSTOMER_PREFIX, rate);
        assert user != null;
        addTask(user.getId(), () -> new Customer(ticketPool, rate, logger, true, user));
    }

    private User createUser(String name, String prefix, int rate) {
        String upperCase = prefix.substring(0, prefix.length() - 1).toUpperCase();
        if (users.containsValue(name)) {
            System.out.println(upperCase + " with name " + name + " already exists.");
            return null;
        }
        String id = prefix + "-" + Util.generateUniqueKey();
        users.put(id, name);

        System.out.println(upperCase + " added with ID: " + id + ", rate: " + rate + " tickets/sec");

        return new User(name, id, rate);
    }

    private void addTask(String id, RunnableSupplier taskSupplier) {
        Runnable task = taskSupplier.get();
        taskDefinitions.put(id, task); // Store task definition

        if (isRunning) {
            Future<?> future = executorService.submit(task);
            taskMap.put(id, future); // Start task immediately if running
        }
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
            Store.clear();
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

    public Map<String, Map<String, String>> viewUserByType() {
        Map<String, String> vendorList = new HashMap<>();
        Map<String, String> customerList = new HashMap<>();
        Map<String, Map<String, String>> userList = new HashMap<>();

        users.forEach((id, name) -> {
            if (id.startsWith(VENDOR_PREFIX)) {
                vendorList.put(id, name);
            } else if (id.startsWith(CUSTOMER_PREFIX)) {
                customerList.put(id, name);
            }
        });

        userList.put("Vendors", vendorList);
        userList.put("Customers", customerList);

        return userList;
    }

    public void viewUserByType(String type) {
        users.forEach((id, name) -> {
            if (id.startsWith(type)) {
                System.out.println(type + " ID: " + id + " - Name: " + name);
            }
        });
    }

    @FunctionalInterface
    private interface RunnableSupplier {
        Runnable get();
    }
}
