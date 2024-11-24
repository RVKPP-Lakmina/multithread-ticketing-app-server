package com.example.ticketManager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketManager.service.TicketingService;

@RestController
@RequestMapping("/api/ticket-service")
public class TicketingController {

    private final TicketingService ticketingService;

    public TicketingController(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

    // @PostMapping("/start")
    // public ResponseEntity<String> startSimulation() {
    // ticketingService.startSimulation();
    // return ResponseEntity.ok("Simulation started.");
    // }

    // @PostMapping("/stop")
    // public ResponseEntity<String> stopSimulation() {
    // ticketingService.stopSimulation();
    // return ResponseEntity.ok("Simulation stopped.");
    // }

    @PostMapping("/vendor")
    public ResponseEntity<String> addVendor(@RequestParam String name, @RequestParam int rate) {
        ticketingService.addVendor(name, rate);
        return ResponseEntity.ok("Vendor added: " + name);
    }

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@RequestParam String name, @RequestParam int rate) {
        ticketingService.addCustomer(name, rate);
        return ResponseEntity.ok("Customer added: " + name);
    }

    @DeleteMapping("/vendor/{id}")
    public ResponseEntity<String> removeVendor(@PathVariable String id) {
        ticketingService.removeVendor(id);
        return ResponseEntity.ok("Vendor removed: " + id);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> removeCustomer(@PathVariable String id) {
        ticketingService.removeCustomer(id);
        return ResponseEntity.ok("Customer removed: " + id);
    }

    // @GetMapping("/status")
    // public ResponseEntity<String> viewSystemStatus() {
    // return ResponseEntity.ok(ticketingService.viewSystemStatus());
    // }

    // @GetMapping("/users")
    // public ResponseEntity<String> viewUsers() {
    // return ResponseEntity.ok(ticketingService.viewUsers());
    // }
}
