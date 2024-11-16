package com.example.ticketingapp.model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class TicketPoolConfig {
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
}
