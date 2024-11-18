package com.example.store;

import com.example.ticketingapp.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private static List<Ticket> purchasedTickets = new ArrayList<Ticket>();

    public static List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public static void addPurchasedTickets(Ticket purchasedTicket) {
        purchasedTickets.add(purchasedTicket);
    }
}
