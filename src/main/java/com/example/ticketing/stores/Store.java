package com.example.ticketing.stores;

import com.example.ticketing.model.Ticket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Store {
    private static final Queue<Ticket> tickets = new ConcurrentLinkedQueue<Ticket>();
    private static int totalTickets;
    private static int maxCapacity;


    public static Queue<Ticket> ticketsQueue() {
        return Store.tickets;
    }

    public static int getTotalTickets() {
        return Store.totalTickets;
    }

    public static int getMaxCapacity() {
        return maxCapacity;
    }

    public static void setMaxCapacity(int maxCapacity) {
        Store.maxCapacity = maxCapacity;
    }

    public static void setTotalTickets(int totalTickets) {
        Store.totalTickets = totalTickets;
    }

    public static void clear() {
        tickets.clear();
        totalTickets = 0;
        maxCapacity = 0;
    }
}
