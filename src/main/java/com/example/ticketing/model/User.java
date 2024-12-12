package com.example.ticketing.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private int rate;
    private String prefix;
    private boolean isActive;

    // Default constructor for MongoDB
    public User() {
    }

    public User(String name, int rate) {
        this.name = name;
        this.rate = rate;
    }

    public User(String name, int rate, String prefix) {
        this(name, rate);
        this.prefix = prefix;
    }

    public User(String name, String id, int rate) {
        this(name, rate);
        this.id = id;
    }

    public User(String name, String id, int rate, String prefix) {
       this(name, id, rate);
       this.prefix = prefix;
    }

    public User(String name, String id, int rate, String prefix, boolean isActive) {
       this(name, id, rate, prefix);
       this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
