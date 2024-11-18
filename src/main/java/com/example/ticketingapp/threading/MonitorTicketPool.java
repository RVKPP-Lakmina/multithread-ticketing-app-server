package com.example.ticketingapp.threading;

import com.example.ticketingapp.component.imples.TicketPool;
import com.example.ticketingapp.component.interfaces.TicketPoolOperations;
import com.example.ticketingapp.websocket.TicketPoolWebSocketHandler;

public class MonitorTicketPool {
    private TicketPoolOperations ticketPool;

    public MonitorTicketPool() {
        ticketPool = new TicketPool();
    }

}
