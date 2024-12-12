package com.example.ticketManager.component.implementations.ActionHanlderImpements;

import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;
import com.example.ticketing.util.interfaces.IEventLogger;

public class RemoveUserHandler implements ActionHandler {

    private final TicketingService ticketingService;
    private final IEventLogger logger;

    public RemoveUserHandler(TicketingService ticketingService, IEventLogger logger) {
        this.ticketingService = ticketingService;
        this.logger = logger;
    }

    @Override
    public void handle(String[] params) {
        if (params.length == 1) {
            String id = params[0];
            ticketingService.removeUser(id);
        } else {
            logger.error("Invalid parameters for RemoveUser");
        }
    }
}
