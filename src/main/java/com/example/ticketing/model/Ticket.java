package com.example.ticketing.model;

public class Ticket {
    private static int ticketCount = 0;
    private final int ticketNumber;

    public Ticket() {
        ticketNumber = ticketCount++;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

}