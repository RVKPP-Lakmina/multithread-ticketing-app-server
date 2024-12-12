package com.example.ticketing.controller;

import com.example.ticketManager.util.GlobalLogger;
import com.example.ticketing.model.Customer;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketing.model.User;
import com.example.ticketing.model.Vendor;
import com.example.ticketing.stores.Constants;
import com.example.ticketing.stores.Store;
import com.example.ticketing.util.LoggerService;
import com.example.ticketing.util.Util;
import com.example.ticketing.util.interfaces.IEventLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimulationTask implements TicketingSystemSimulator {
    private final ConcurrentHashMap<String, Runnable> taskDefinitions;
    private final ConcurrentTicketStore ticketPool;
    private ExecutorService executorService;
    private final ConcurrentHashMap<String, Future<?>> taskMap;
    private final HashMap<String, User> users;
    private final IEventLogger logger;
    private String VENDOR_PREFIX;
    private String CUSTOMER_PREFIX;
    private boolean isBackendService;

    public SimulationTask(int initialTickets, int maxCapacity) {
        this(initialTickets, maxCapacity, new LoggerService());
    }

    public SimulationTask(int initialTickets, int maxCapacity, IEventLogger logger) {
        this.logger = logger;
        this.ticketPool = new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
        this.executorService = Executors.newCachedThreadPool();
        this.taskMap = new ConcurrentHashMap<>();
        this.users = new HashMap<>();
        this.taskDefinitions = new ConcurrentHashMap<>();
        this.VENDOR_PREFIX = Constants.getVendorPrefix();
        this.CUSTOMER_PREFIX = Constants.getCustomerPrefix();
    }

    public SimulationTask(int initialTickets, int maxCapacity, IEventLogger logger, boolean isBackendService) {
        this(initialTickets, maxCapacity, logger);
        this.isBackendService = isBackendService;
    }

    public void startTask() {
        if (Store.isIsRunning()) {
            logger.error("Simulation is already running.");
            return;
        }
        executorService = Executors.newCachedThreadPool();

        if (users.isEmpty()) {
            logger.error("No Available active Users. Configure Users at First!!");
            return;
        }

        Store.setIsRunning(true);

        // Resume tasks
        taskDefinitions.forEach((id, task) -> {
            Future<?> future = executorService.submit(task);
            taskMap.put(id, future);
        });

        logger.log("Simulation started. Resumed all pending tasks.");
    }

    public void stopTask() {
        if (!Store.isIsRunning()) {
            logger.log("Simulation is not running.");
            return;
        }
        taskMap.forEach((id, future) -> future.cancel(true)); // Stop all active tasks
        taskMap.clear(); // Keep task definitions for resuming
        executorService.shutdownNow(); // Shut down the executor service
        executorService = null;
        Store.setIsRunning(false);
        logger.log("Simulation stopped. All tasks paused.");
    }

    public void addVendor(String name, int rate) {
        User user = createUser(name, VENDOR_PREFIX, rate, null);
        assert user != null;

        Vendor vendor = new Vendor(ticketPool, rate, logger, this.isBackendService, user);

        if (isBackendService) {
            vendor.setBackEndService(true);
        }

        addTask(user.getId(), () -> vendor);
    }

    public void addVendor(String id, String name, int rate) {
        User user = createUser(name, VENDOR_PREFIX, rate, id);
        assert user != null;
        Vendor vendor = new Vendor(ticketPool, rate, logger, this.isBackendService, user);

        if (isBackendService) {
            vendor.setBackEndService(true);
        }

        addTask(user.getId(), () -> vendor);
    }

    public void addCustomer(String name, int rate) {
        User user = createUser(name, CUSTOMER_PREFIX, rate, null);
        assert user != null;
        Customer customer = new Customer(ticketPool, rate, logger, this.isBackendService, user);

        if (isBackendService) {
            customer.setBackEndService(true);
        }

        addTask(user.getId(), () -> customer);
    }

    public void addCustomer(String id, String name, int rate) {
        User user = createUser(name, CUSTOMER_PREFIX, rate, id);
        assert user != null;

        Customer customer = new Customer(ticketPool, rate, logger, this.isBackendService, user);

        if (isBackendService) {
            customer.setBackEndService(true);
        }

        addTask(user.getId(), () -> customer);
    }

    private User createUser(String name, String prefix, int rate, String id) {
        String upperCase = prefix.substring(0, prefix.length() - 1).toUpperCase();

        if (users.containsValue(name)) {
            logger.error(upperCase + " with name " + name + " already exists.");
            return null;
        }

        User taskUser;

        if(users.containsKey(id)){
            taskUser = users.get(id);
            taskUser.setActive(true);

        }else{
            id = id != null ? id : prefix + "-" + Util.generateUniqueKey();
            taskUser = new User(name, id, rate, prefix, true);
            users.put(id, taskUser);
        }

        logger.log(upperCase + " added with ID: " + id + ", rate: " + rate + " tickets/sec", taskUser);
        return taskUser;
    }

    private void addTask(String id, RunnableSupplier taskSupplier) {
        Runnable task = taskSupplier.get();
        taskDefinitions.put(id, task); // Store task definition

        if (Store.isIsRunning()) {
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

        if(!taskMap.containsKey(id) && !users.containsKey(id)){
            logger.error("No" + id + ": User or Active task found.");
        }

        Future<?> future = taskMap.remove(id);

        if(future == null){
            logger.error("No" + id + ": No active task found.");
            return;
        }
        taskDefinitions.remove(id);
        future.cancel(true); // Interrupt the task

        User user = users.get(id);
        user.setActive(false);

        logger.log("User is Removed: " + id, user);
    }

    public void stopSimulation() {
        executorService.shutdownNow(); // Interrupt all threads
        logger.log("Simulation stopped.");
    }

    public void shutdownSystem() {
        try {
            if (executorService != null) {
                taskMap.clear();
                Store.clear();
                users.clear();
                logger.stop();
                Thread.sleep(1000);
                executorService.shutdownNow();
                System.out.println("System shut down.");
            }
        } catch (InterruptedException e) {
            GlobalLogger.logError("SimulationTask | shutdownSystem | Simulation interrupted", e);
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

        users.forEach((id, user) -> {
            if (id.startsWith(VENDOR_PREFIX)) {
                vendorList.put(id, user.getName());
            } else if (id.startsWith(CUSTOMER_PREFIX)) {
                customerList.put(id, user.getName());
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
