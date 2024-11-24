package com.example.ticketManager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketing.util.LoggerService;

@Configuration
public class TicketingConfig {

    @Bean
    public TicketingSystemSimulator simulationTask() {
        return new SimulationTask(100, 1000);
    }

    @Bean
    public ConcurrentTicketStore concurrentTicketStore() {
        int initialTickets = 100;
        int maxCapacity = 50;
        LoggerService logger = new LoggerService();
        return new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
    }

}
