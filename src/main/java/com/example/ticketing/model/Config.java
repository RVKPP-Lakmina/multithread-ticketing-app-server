package com.example.ticketing.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Config {
    private final int totalTickets;
    private final int maxCapacity;

    @JsonCreator
    public Config(@JsonProperty("totalTickets") int totalTickets,
                  @JsonProperty("maxCapacity") int maxCapacity) {
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
