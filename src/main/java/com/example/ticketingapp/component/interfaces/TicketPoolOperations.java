package com.example.ticketingapp.component.interfaces;

public interface TicketPoolOperations {

    boolean addTickets(int count);

    boolean buyTicket();

    int getTicketCount();
}
