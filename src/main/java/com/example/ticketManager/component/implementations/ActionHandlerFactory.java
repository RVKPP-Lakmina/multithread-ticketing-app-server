package com.example.ticketManager.component.implementations;

import com.example.ticketManager.component.implementations.ActionHanlderImpements.*;
import com.example.ticketManager.component.interfaces.ActionHandler;
import com.example.ticketManager.service.TicketingService;
import com.example.ticketing.util.interfaces.IEventLogger;

public class ActionHandlerFactory {

    public static ActionHandler create(String action, TicketingService ticketingService, IEventLogger logger) {
        switch (action) {
            case "startSimulation":
                return new StartSimulationHandler(ticketingService);
            case "stopSimulation":
                return new StopSimulationHandler(ticketingService);
            case "addVendor":
                return new AddVendorHandler(ticketingService, logger);
            case "addCustomer":
                return new AddCustomerHandler(ticketingService, logger);
            case "removeUser":
                return new RemoveUserHandler(ticketingService, logger);
            default:
                return null;
        }
    }
}
