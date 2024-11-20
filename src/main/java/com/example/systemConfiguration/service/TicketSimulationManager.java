package com.example.systemConfiguration.service;

import com.example.systemConfiguration.threading.Consumer;
import com.example.systemConfiguration.threading.Vendor;
import com.example.systemConfiguration.utils.Banner;
import com.example.systemConfiguration.utils.ConfigurationInput;
import com.example.ticketingapp.component.imples.TicketPool;
import com.example.ticketingapp.component.interfaces.TicketPoolOperations;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketSimulationManager {
    private final SystemConfiguration systemConfiguration;
    private final TicketPoolOperations ticketPoolOperations;
    private final ExecutorService executor;
    private final Queue<Thread> vendorThreads;
    private final Queue<Thread> customerThreads;
    private final Queue<Integer> ticketAdditionQueue;
    private final Queue<Integer> ticketPurchaseQueue;
    private final ConfigurationInput input;

    public TicketSimulationManager() {
        this.systemConfiguration = new SystemConfiguration();
        this.ticketPoolOperations = new TicketPool();
        this.executor = Executors.newFixedThreadPool(10);
        this.vendorThreads = new ConcurrentLinkedQueue<>();
        this.customerThreads = new ConcurrentLinkedQueue<>();
        this.ticketAdditionQueue = new ConcurrentLinkedQueue<>();
        this.ticketPurchaseQueue = new ConcurrentLinkedQueue<>();
        this.input = new ConfigurationInput();
    }

    public void runSimulation() {
        configureSystem();
        if (!validateInitialTicketCount()) return;

        Banner.printBanner("Start the Simulation process");

        int noOfVendors = getValidatedInput("No of Vendors: ");
        int noOfCustomers = getValidatedInput("No of Customers: ");

        createAndStartThreads(noOfVendors, Vendor.class);
        createAndStartThreads(noOfCustomers, Consumer.class);

        waitForThreadsCompletion(vendorThreads);
        waitForThreadsCompletion(customerThreads);

        System.out.println("Ticket handling operations completed.");
    }

    private void configureSystem() {
        systemConfiguration.configureSystem();
    }

    private boolean validateInitialTicketCount() {
        int initialTicketCount = systemConfiguration.getTotalTickets().getValue();
        if (initialTicketCount <= 0) {
            System.out.println("Invalid initial ticket count. Aborting...");
            executor.shutdown();
            return false;
        }
        ticketAdditionQueue.add(initialTicketCount);
        ticketPurchaseQueue.add(initialTicketCount);
        return true;
    }

    private int getValidatedInput(String prompt) {
        int input = this.input.promptForInt(prompt);
        return input > 0 ? input : 1;
    }

    private void createAndStartThreads(int count, Class<?> type) {
        for (int i = 0; i < count; i++) {
            Runnable task = createTaskInstance(type, i);
            Thread thread = new Thread(task, type.getSimpleName().toLowerCase() + "-" + i);
            (type == Vendor.class ? vendorThreads : customerThreads).add(thread);
            thread.start();
        }
    }

    private Runnable createTaskInstance(Class<?> type, int index) {
        if (type == Vendor.class) {
            return new Vendor(ticketPoolOperations, ticketAdditionQueue);
        } else if (type == Consumer.class) {
            return new Consumer(ticketPoolOperations, ticketPurchaseQueue);
        }
        throw new IllegalArgumentException("Unsupported thread type: " + type.getName());
    }

    private void waitForThreadsCompletion(Queue<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + thread.getName());
                Thread.currentThread().interrupt();
            }
        }
    }
}
