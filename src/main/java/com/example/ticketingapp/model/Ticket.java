package com.example.ticketingapp.model;

import com.example.ticketingapp.util.GlobalLogger;

import java.util.Date;

public class Ticket {
    private int ticketID;
    private double ticketPrice;
    private Boolean isAvailable;
    private Date timesStamp;

    public Ticket(int ticketID, double ticketPrice, Boolean isAvailable, Date timesStamp) {
        this.ticketID = ticketID;
        this.ticketPrice = ticketPrice;
        this.isAvailable = isAvailable;
        this.timesStamp = timesStamp;
    }

    public int getTicketID() {
        return ticketID;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
        GlobalLogger.logInfo(this.ticketID + "Ticket price set to " + ticketPrice);
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
        GlobalLogger.logInfo(this.ticketID + "Ticket available set to " + isAvailable);
    }

    public Date getTimesStamp() {
        return timesStamp;
    }

    public void setTimesStamp(Date timesStamp) {
        this.timesStamp = timesStamp;
        GlobalLogger.logInfo(this.ticketID + "Ticket time set to " + timesStamp);
    }
}