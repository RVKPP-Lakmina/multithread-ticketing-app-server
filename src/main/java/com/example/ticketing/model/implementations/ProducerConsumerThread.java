package com.example.ticketing.model.implementations;

import com.example.ticketing.model.User;
import com.example.ticketing.model.interfaces.ITicketPool;
import com.example.ticketing.util.interfaces.IEventLogger;

public abstract class ProducerConsumerThread implements Runnable {
    private final ITicketPool ticketPool;
    private final IEventLogger logger;
    private final int rate;
    private final String name;
    public User user;
    private boolean isBackEndService = true;

    public ProducerConsumerThread(ITicketPool ticketPool, IEventLogger logger, int rate, String name) {
        this.ticketPool = ticketPool;
        this.logger = logger;
        this.rate = rate;
        this.name = name;
    }

    public ProducerConsumerThread(ITicketPool ticketPool, IEventLogger logger, int rate, User user, boolean isBackEndService) {
        this(ticketPool, logger, rate, user.getName());
        this.isBackEndService = isBackEndService;
        this.user = user;
    }

    public void processingLogger(String message) {
        if (isBackEndService) {
            logger.log(message, user);
        } else {
            logger.log(message);
        }
    }

    public IEventLogger getLogger() {
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
