package com.example.systemConfiguration.threading;

import com.example.store.Store;
import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.model.Ticket;
import com.example.ticketingapp.util.ConfigurationLoader;

import java.util.List;
import java.util.Queue;

public class Vendor implements Runnable {
    private final TicketPoolOperations ticketPool;
    private final Queue<Integer> ticketReleasingQueue;
    private final int ticketReleaseRate;
    private volatile boolean running = true;
    private final int totalTicket;

    public Vendor(TicketPoolOperations ticketPool, Queue<Integer> ticketReleasingQueue) {
        this.ticketPool = ticketPool;
        this.ticketReleasingQueue = ticketReleasingQueue;
        this.ticketReleaseRate = ConfigurationLoader.getConfig().getTicketReleaseRate();
        this.totalTicket = ConfigurationLoader.getConfig().getTotalTickets();
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
        List<Ticket> tickets = Store.getPurchasedTickets();

        while (true) {
            if (tickets.size() == this.totalTicket) {

                System.out.println("No Tickets left to process");
                break;
            }
            processTicketReleasing();
            sleepForMoment();
        }
    }

    public void stop() {
        running = false; // Set the flag to false to stop the loop
    }
}
