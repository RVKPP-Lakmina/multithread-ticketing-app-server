package com.example.ticketing.model.implementations;

import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.LoggerService;

public abstract class ProducerConsumerThread implements Runnable {
    private final ITicketPool ticketPool;
    private final LoggerService logger;
    private final int rate;
    private final String name;

    public ProducerConsumerThread(ITicketPool ticketPool, LoggerService logger, int rate, String name) {
        this.ticketPool = ticketPool;
        this.logger = logger;
        this.rate = rate;
        this.name = name;
    }

    public LoggerService getLogger() {
        return logger;
    }

    public ITicketPool getTicketPool() {
        return ticketPool;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            process();
            sleep();
        }

    }

    public abstract void process();

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
