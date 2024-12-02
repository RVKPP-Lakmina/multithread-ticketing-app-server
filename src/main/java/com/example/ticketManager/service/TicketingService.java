package com.example.ticketManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;

import java.util.Map;

@Service
public class TicketingService {

    private final TicketingSystemSimulator simulationTask;

    @Autowired
    public TicketingService(TicketingSystemSimulator simulationTask) {
        this.simulationTask = simulationTask;
    }

     public void startSimulation() {
     simulationTask.startTask();
     }

     public void stopSimulation() {
     simulationTask.stopTask();
     }

    public void addVendor(String name, int rate) {
        simulationTask.addVendor(name, rate);
    }

    public void addCustomer(String name, int rate) {
        simulationTask.addCustomer(name, rate);
    }

    public void removeVendor(String id) {
        simulationTask.removeVendor(id);
    }

    public void removeCustomer(String id) {
        simulationTask.removeCustomer(id);
    }

     public Map<String, Map<String, String>> viewActiveUsers() {
     return simulationTask.viewUserByType();
     }

    // public String viewUsers() {
    // return simulationTask.viewUsers();
    // }
}
