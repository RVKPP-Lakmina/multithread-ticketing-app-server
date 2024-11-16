package com.example.ticketingapp.component.imples;

import com.example.ticketingapp.component.AbstractTicketPool;
import com.example.ticketingapp.model.Ticket;
import com.example.ticketingapp.util.ConfigurationLoader;
import org.springframework.stereotype.Component;

@Component
public class TicketPool extends AbstractTicketPool {

    public TicketPool() {
        super(ConfigurationLoader.getConfig().getMaxTicketCapacity());
    }

    @Override
    public boolean addTickets(int count) {
        synchronized (lock) {
            if (tickets.size() + count <= maxTicketCapacity) {

                for (int i = 0; i < count; i++) {
                    tickets.add(new Ticket());
                }
                System.out.println(count + " tickets added. Total tickets: " + tickets.size());
                // Notify waiting threads that tickets are available
                lock.notifyAll();
                return true;
            }

            System.out.println("Cannot add more tickets; pool at max capacity.");
            return false;
        }
    }

    @Override
    public boolean buyTicket() {
        synchronized (lock) {
            while (tickets.isEmpty()) {
                try {
                    System.out.println("Waiting for tickets to buy ...");
                    // Wait until tickets are added
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException("Thread interrupted while waiting to buy ticket.");
                }
            }

            Ticket ticket = tickets.poll();

            if (ticket != null) {
                System.out.println("Ticket purchased successfully. Remaining tickets: " + tickets.size());
                return true;
            }
            System.out.println("No tickets available for purchase.");
            return false;
        }
    }
}
