package com.example.ticketing.model;

public class User {
    private String id;
    private String name;
    private int rate;

    public User(String name, String id, int rate) {
        this.name = name;
        this.id = id;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
