package com.example.ticketingapp.threading;

import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.util.ConfigurationLoader;

import java.util.Queue;

public class Consumer implements Runnable {
    private final TicketPoolOperations ticketPool;
    private final Queue<Integer> requestQueue;
    private final int customerRetrievalRate;

    public Consumer(TicketPoolOperations ticketPool, Queue<Integer> requestQueue) {
        this.ticketPool = ticketPool;
        this.requestQueue = requestQueue; // 10
        this.customerRetrievalRate = ConfigurationLoader.getConfig().getCustomerRetrievalRate();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
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

        int ticketsToBuy = Math.min(customerRetrievalRate, requestedTickets);

        for (int i = 0; i < ticketsToBuy; i++) {
            ticketPool.buyTicket();
        }

        if (customerRetrievalRate < requestedTickets) {
            requestQueue.add(requestedTickets - customerRetrievalRate);
        }
    }

    private void sleepForMoment() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
