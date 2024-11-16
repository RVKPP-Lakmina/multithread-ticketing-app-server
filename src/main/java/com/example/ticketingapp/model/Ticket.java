package com.example.ticketingapp.model;

public class Ticket {
    private int id;
    private String description;
    private int price;

    public Ticket() {
    }

    public Ticket(int id, String description, int price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public Ticket(String description, int price) {
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}