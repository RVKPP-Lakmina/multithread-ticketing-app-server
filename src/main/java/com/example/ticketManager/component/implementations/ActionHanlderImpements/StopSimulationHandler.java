package com.example.ticketManager.component.implementations.ActionHanlderImpements;

import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;

public class StopSimulationHandler implements ActionHandler {

    private final TicketingService ticketingService;

    public StopSimulationHandler(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

    @Override
    public void handle(String[] params) {
        ticketingService.stopSimulation();
    }
}
