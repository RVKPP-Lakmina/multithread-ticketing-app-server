package com.example.ticketManager.service;

import com.example.ticketing.model.User;
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

    public void addVendor(User user) {
        simulationTask.addVendor(user.getId(), user.getName(), user.getRate());
    }

    public void addCustomer(String name, int rate) {
        simulationTask.addCustomer(name, rate);
    }

    public void addCustomer(User user) {
        simulationTask.addCustomer(user.getId(), user.getName(), user.getRate());
    }

    public void removeVendor(String id) {
        simulationTask.removeVendor(id);
    }

    public void removeCustomer(String id) {
        simulationTask.removeCustomer(id);
    }

    public void removeUser(String id) {
        if (id.startsWith("vendor")) {
            this.removeVendor(id);
        }
        if (id.startsWith("customer")) {
            this.removeCustomer(id);
        }
    }

    public Map<String, Map<String, String>> viewActiveUsers() {
        return simulationTask.viewUserByType();
    }

//     public User getUser(String id) {}

    // public String viewUsers() {
    // return simulationTask.viewUsers();
    // }
}
