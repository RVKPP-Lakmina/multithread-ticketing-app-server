package com.example.ticketingapp.component;

import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.model.Ticket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractTicketPool implements TicketPoolOperations {

    protected final Queue<Ticket> tickets = new ConcurrentLinkedQueue<Ticket>();
    protected final int maxTicketCapacity;
    protected final ReentrantLock lock = new ReentrantLock();

    public AbstractTicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public abstract boolean addTickets(int count);

    @Override
    public abstract boolean buyTicket();

    @Override
    public int getTicketCount() {
        synchronized (lock) {
            return tickets.size();
        }
    }

}
