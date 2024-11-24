package com.example.ticketing.model;

public class User {
    private String id;
    private String name;
    private Runnable task;

    public User(String id, String name, Runnable task) {
        this.id = id;
        this.name = name;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Runnable getTask() {
        return task;
    }
}
