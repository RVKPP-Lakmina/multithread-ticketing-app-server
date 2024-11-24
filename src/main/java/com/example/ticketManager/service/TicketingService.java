package com.example.ticketManager.service;

import org.springframework.stereotype.Service;

import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;

@Service
public class TicketingService {

    private final TicketingSystemSimulator simulationTask;

    public TicketingService(SimulationTask simulationTask) {
        this.simulationTask = simulationTask;
    }

    // public void startSimulation() {
    // simulationTask.start();
    // }

    // public void stopSimulation() {
    // simulationTask.stop();
    // }

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

    // public void viewSystemStatus() {
    // return simulationTask.viewSystemStatus();
    // }

    // public String viewUsers() {
    // return simulationTask.viewUsers();
    // }

}
