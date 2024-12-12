package com.example.ticketManager.configuration;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketManager.util.Logger;
import com.example.ticketing.model.Config;
import com.example.ticketing.repository.ConfigManager;
import com.example.ticketing.stores.Store;
import com.example.ticketing.util.interfaces.IEventLogger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketingConfig {

    private int initialTickets;

    @Value("${ticketing.simulation.interval:1000}")
    private int simulationInterval;

    private int maxCapacity;

    private final WebSocketHandler webSocketHandler;

    public TicketingConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
        ConfigManager.initialize();
        this.initialTickets = Store.getTotalTickets();
        this.maxCapacity = Store.getMaxCapacity();
    }


    @Bean
    public IEventLogger eventLogger() {
        return new Logger(webSocketHandler);
    }

    @Bean
    public TicketingSystemSimulator simulationTask(IEventLogger logger) {
        return new SimulationTask(initialTickets, simulationInterval, logger, true);
    }

    @Bean
    public ConcurrentTicketStore concurrentTicketStore(IEventLogger logger) {
        return new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
    }
}
