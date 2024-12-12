package com.example.ticketManager.model;

import com.example.ticketing.model.User;
import com.example.ticketing.stores.Store;

import java.util.Date;

public class MdMessage {
    private User user;
    private String message;
    private int pendingTotalTickets;
    private int ticketPoolCapacity;
    private Date date;
    private boolean activeStatus;
    private int status;

    public MdMessage(User user, String message, boolean activeStatus) {
        this.user = user;
        this.message = message;
        this.activeStatus = Store.isIsRunning();
        this.date = new Date();
        this.ticketPoolCapacity = Store.ticketsQueue().size();
        this.pendingTotalTickets = Store.getTotalTickets();
        this.status = 1;
    }

    private MdMessage(User user, String message, boolean activeStatus, int status) {
        this(user, message, activeStatus);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPendingTotalTickets() {
        return pendingTotalTickets;
    }

    public void setPendingTotalTickets(int pendingTotalTickets) {
        this.pendingTotalTickets = pendingTotalTickets;
    }

    public int getTicketPoolCapacity() {
        return ticketPoolCapacity;
    }

    public void setTicketPoolCapacity(int ticketPoolCapacity) {
        this.ticketPoolCapacity = ticketPoolCapacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
