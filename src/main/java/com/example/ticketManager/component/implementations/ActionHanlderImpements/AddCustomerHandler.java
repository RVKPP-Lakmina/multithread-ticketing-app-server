package com.example.ticketManager.component.implementations.ActionHanlderImpements;

import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;
import com.example.ticketing.util.interfaces.IEventLogger;

public class AddCustomerHandler implements ActionHandler {

    private final TicketingService ticketingService;
    private final IEventLogger logger;

    public AddCustomerHandler(TicketingService ticketingService, IEventLogger logger) {
        this.ticketingService = ticketingService;
        this.logger = logger;
    }

    @Override
    public void handle(String[] params) {
        if (params.length == 2) {
            String name = params[0];
            int rate = Integer.parseInt(params[1]);
            ticketingService.addCustomer(name, rate);
        } else {
            logger.error("Invalid parameters for addCustomer");
        }
    }
}
