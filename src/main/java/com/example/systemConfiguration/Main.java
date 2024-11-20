package com.example.systemConfiguration;

import com.example.store.Store;
import com.example.systemConfiguration.service.SystemConfiguration;
import com.example.systemConfiguration.service.TicketSimulationManager;

public class Main {
    public static void main(String[] args) {
        TicketSimulationManager manager = new TicketSimulationManager();
        manager.runSimulation();

        System.out.printf("issued Tickets: %d", Store.getPurchasedTickets().size());
    }
}
