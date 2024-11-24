package com.example.ticketing.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import com.example.ticketing.enumeration.TicketStatusEnum;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.LoggerService;
import com.example.ticketingapp.model.Ticket;

public class ConcurrentTicketStore implements ITicketPool {
    private int totalTickets;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); // Vendors wait when pool is full
    private final Condition notEmpty = lock.newCondition(); // Customers wait when pool is empty
    protected final Queue<Ticket> tickets = new ConcurrentLinkedQueue<Ticket>();
    private LoggerService logger;

    public ConcurrentTicketStore(int totalTickets, int maxCapacity, LoggerService logger) {
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
        this.logger = logger;
    }

    /**
     * Adds tickets to the pool in a thread-safe manner.
     *
     * @param count Number of tickets to add.
     * @return true if tickets were successfully added; false if the pool would
     *         exceed max capacity.
     */
    public TicketStatusEnum addTickets(int count) {
        lock.lock();
        int ticketsCount;
        try {
            if (totalTickets == 0) {
                return TicketStatusEnum.EMPTY;
            }

            if (count > totalTickets) {
                this.logger.log("Not enough tickets to add. Only " + totalTickets + " tickets left.");
            }

            ticketsCount = Math.min(count, totalTickets);

            while (tickets.size() >= maxCapacity) {
                this.logger.log("Pool is full. Vendor " + "is waiting.");
                notFull.await(); // Wait if pool is full
            }

            int ticketsToAdd = Math.min(ticketsCount, maxCapacity - tickets.size());

            for (int i = 0; i < ticketsToAdd; i++) {
                tickets.add(new Ticket());
                totalTickets = totalTickets - 1;
            }

            if (ticketsCount > ticketsToAdd) {
                return addTickets(ticketsCount - ticketsToAdd);
            }

            notEmpty.signalAll(); // Notify customers that tickets are available
            return TicketStatusEnum.SUCCESS;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return TicketStatusEnum.ERROR;
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
    public TicketStatusEnum purchaseTickets(int count) {
        lock.lock();
        int ticketsCount;
        try {
            if (totalTickets == 0 && tickets.size() == 0) {
                return TicketStatusEnum.EMPTY;
            }

            if (totalTickets + tickets.size() < count) {
                return TicketStatusEnum.NOTENOUGH;
            }

            while (tickets.size() == 0) {
                this.logger.log("waiiting for Tickets");
                notEmpty.await(); // Wait if not enough tickets
            }

            ticketsCount = Math.min(count, tickets.size());

            for (int i = 0; i < ticketsCount; i++) {
                tickets.poll();
            }

            if (ticketsCount < count) {
                return purchaseTickets(count - ticketsCount);
            }

            notFull.signalAll(); // Notify vendors that pool is not full
            return TicketStatusEnum.SUCCESS;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return TicketStatusEnum.ERROR;
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
