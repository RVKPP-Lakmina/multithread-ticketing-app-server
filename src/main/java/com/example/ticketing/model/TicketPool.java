package com.example.ticketing.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import com.example.ticketingapp.model.Ticket;

public class TicketPool {
    private int totalTickets;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); // Vendors wait when pool is full
    private final Condition notEmpty = lock.newCondition(); // Customers wait when pool is empty
    protected final Queue<Ticket> tickets = new ConcurrentLinkedQueue<Ticket>();

    public TicketPool(int totalTickets, int maxCapacity) {
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds tickets to the pool in a thread-safe manner.
     *
     * @param count Number of tickets to add.
     * @return true if tickets were successfully added; false if the pool would
     *         exceed max capacity.
     */
    public boolean addTickets(int count) {
        lock.lock();
        try {
            if (totalTickets == 0) {
                return false;
            }

            while (tickets.size() >= maxCapacity) {
                notFull.await(); // Wait if pool is full
            }

            if (tickets.size() + count <= maxCapacity) {
                for (int i = 0; i < count; i++) {
                    tickets.add(new Ticket());
                }
                totalTickets -= count;
                notEmpty.signalAll(); // Notify customers that tickets are available
                return true;
            }

            return false; // Exceeding capacity
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes tickets from the pool in a thread-safe manner.
     *
     * @param count Number of tickets to remove.
     * @return true if tickets were successfully removed; false if not enough
     *         tickets are available.
     */
    public boolean removeTickets(int count) {
        lock.lock();
        try {
            if (totalTickets == 0 && tickets.size() == 0) {
                return false;
            }

            while (tickets.size() > 0) {
                notEmpty.await(); // Wait if not enough tickets
            }

            if (tickets.size() > 0) {
                for (int i = 0; i < count; i++) {
                    tickets.poll();
                }
                notFull.signalAll(); // Notify vendors that pool is not full
                return true;
            }
            return false; // Not enough tickets
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Retrieves the current total number of tickets in a thread-safe manner.
     *
     * @return Total tickets currently in the pool.
     */
    public int getTotalTickets() {
        lock.lock();
        try {
            return totalTickets;
        } finally {
            lock.unlock();
        }
    }

    public int getPoolTickets() {
        lock.lock();
        try {
            return this.tickets.size();
        } finally {
            lock.unlock();
        }
    }
}
