package com.example.ticketingapp.component.imples;

import com.example.store.Store;
import com.example.ticketingapp.component.AbstractTicketPool;
import com.example.ticketingapp.model.Ticket;
import com.example.ticketingapp.util.ConfigurationLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketPool extends AbstractTicketPool {

    public TicketPool() {
        super(ConfigurationLoader.getConfig().getMaxTicketCapacity());
    }

    @Override
    public boolean addTickets(int count) {
        synchronized (this) {
            if (tickets.size() + count <= maxTicketCapacity) {

                for (int i = 0; i < count; i++) {
                    tickets.add(new Ticket());
                }
                System.out.println(count + " tickets added. Total tickets: " + tickets.size());
                // Notify waiting threads that tickets are available
                notifyAll();
                return true;
            }

            System.out.println("Cannot add more tickets; pool at max capacity.");
            return false;
        }
    }

    @Override
    public boolean buyTicket() {
        List<Ticket> ticketsPurchased = Store.getPurchasedTickets();
        int totalTickets = ConfigurationLoader.getConfig().getTotalTickets();

        synchronized (this) {
            while (tickets.isEmpty()) {
                try {
                    if (ticketsPurchased.size() == totalTickets) {
                        return false;
                    }

                    System.out.println("Waiting for tickets to buy ...");

                    // Wait until tickets are added
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Thread interrupted while waiting to buy ticket.");
                }
            }

            Ticket ticket = tickets.poll();

            if (ticket != null) {
                Store.addPurchasedTickets(ticket);
                System.out.println("Ticket purchased successfully. Remaining tickets: " + tickets.size());
                return true;
            }
            System.out.println("No tickets available for purchase.");
            return false;
        }
    }
}
