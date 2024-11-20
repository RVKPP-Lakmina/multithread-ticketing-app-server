package com.example.systemConfiguration.threading;

import com.example.store.Store;
import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.model.Ticket;
import com.example.ticketingapp.util.ConfigurationLoader;

import java.util.List;
import java.util.Queue;

public class Consumer implements Runnable {
    private final TicketPoolOperations ticketPool;
    private final Queue<Integer> requestQueue;
    private final int customerRetrievalRate;
    private volatile boolean running = true;
    private final int totalTicket;
    private final int totalTicketPurchased;

    public Consumer(TicketPoolOperations ticketPool, Queue<Integer> requestQueue) {
        this.ticketPool = ticketPool;
        this.requestQueue = requestQueue; // 10
        this.customerRetrievalRate = ConfigurationLoader.getConfig().getCustomerRetrievalRate();
        this.totalTicket = ConfigurationLoader.getConfig().getTotalTickets();
        this.totalTicketPurchased = Store.getPurchasedTickets().size();
    }

    @Override
    public void run() {
        List<Ticket> tickets = Store.getPurchasedTickets();

        while (true) {
            if (tickets.size() == this.totalTicket) {

                System.out.println("No Tickets left to process");
                break;
            }

            processPurchaseRequests();

            sleepForMoment();
        }
    }

    private void processPurchaseRequests() {
        if (!requestQueue.isEmpty()) {
            Integer requestedTicket = requestQueue.poll();

            if (requestedTicket != null) {
                purchaseTickets(requestedTicket);
            }
        }
    }

    private void purchaseTickets(int requestedTickets) {

        boolean hasTicket = (totalTicketPurchased + requestedTickets) <= this.totalTicket;

        if (!hasTicket) {
            System.out.println("No Tickets left to process");
            return;
        }

        int ticketsToBuy = Math.min(customerRetrievalRate, requestedTickets);

        for (int i = 0; i < ticketsToBuy; i++) {
            ticketPool.buyTicket();
        }

        if (customerRetrievalRate < requestedTickets) {
            requestQueue.add(requestedTickets - customerRetrievalRate);
        }
    }

    public void stop() {
        running = false; // Set the flag too false to stop the loop
    }

    private void sleepForMoment() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}