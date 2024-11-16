package com.example.ticketingapp.threading;

import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.util.ConfigurationLoader;

import java.util.Queue;

public class Vendor implements Runnable {
    private final TicketPoolOperations ticketPool;
    private final Queue<Integer> ticketReleasingQueue;
    private final int ticketReleaseRate;

    public Vendor(TicketPoolOperations ticketPool, Queue<Integer> ticketReleasingQueue) {
        this.ticketPool = ticketPool;
        this.ticketReleasingQueue = ticketReleasingQueue;
        this.ticketReleaseRate = ConfigurationLoader.getConfig().getTicketReleaseRate();
    }

    private void sleepForMoment() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processTicketReleasing() {
        if (!ticketReleasingQueue.isEmpty()) {
            Integer requestedReleasedTickets = ticketReleasingQueue.poll();

            if (requestedReleasedTickets != null) {
                releaseTicket(requestedReleasedTickets);
            }
        }
    }

    private void releaseTicket(int requestedReleasedTickets) {
        int ticketsToAdd = Math.min(ticketReleaseRate, requestedReleasedTickets);
        ticketPool.addTickets(ticketsToAdd);

        if (requestedReleasedTickets > ticketReleaseRate) {
            ticketReleasingQueue.add(requestedReleasedTickets - ticketReleaseRate);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            processTicketReleasing();
            sleepForMoment();
        }
    }
}
