package com.example.ticketManager.configuration;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.ConcurrentTicketStore;
import com.example.ticketManager.util.Logger;
import com.example.ticketing.util.interfaces.IEventLogger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketingConfig {

    @Value("${ticketing.simulation.tickets:100}")
    private int initialTickets;

    @Value("${ticketing.simulation.interval:1000}")
    private int simulationInterval;

    @Value("${ticketing.store.maxCapacity:50}")
    private int maxCapacity;

    private final WebSocketHandler webSocketHandler;

    public TicketingConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Bean
    public IEventLogger eventLogger() {
        return new Logger(webSocketHandler);
    }

    @Bean
    public TicketingSystemSimulator simulationTask(IEventLogger logger) {
        return new SimulationTask(initialTickets, simulationInterval, logger);
    }

    @Bean
    public ConcurrentTicketStore concurrentTicketStore(IEventLogger logger) {
        return new ConcurrentTicketStore(initialTickets, maxCapacity, logger);
    }
}
