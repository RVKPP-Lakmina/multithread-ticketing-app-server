package com.example.ticketManager.component.implementations.ActionHanlderImpements;

import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;

public class StartSimulationHandler implements ActionHandler {
    private final TicketingService ticketingService;

    public StartSimulationHandler(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

    @Override
    public void handle(String[] params) {
        ticketingService.startSimulation();
    }
}
