package com.example.ticketingapp.service;

import com.example.ticketingapp.component.imples.TicketPool;
import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.threading.Consumer;
import com.example.ticketingapp.threading.Vendor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class TicketConfig {

    @Bean
    public Queue<Integer> ticketAdditionQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Bean
    public Queue<Integer> ticketPurchaseQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @Bean
    public Vendor vendor(TicketPoolOperations ticketPool, Queue<Integer> ticketAdditionQueue) {
        return new Vendor(ticketPool, ticketAdditionQueue); // Use ticketReleaseRate value here
    }

    @Bean
    public Consumer consumer(TicketPoolOperations ticketPool, Queue<Integer> ticketPurchaseQueue) {
        return new Consumer(ticketPool, ticketPurchaseQueue); // Use customerRetrievalRate value here
    }
}