package com.example.ticketManager.controller;

import com.example.ticketManager.util.GlobalLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ticketManager.service.TicketingService;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket-service")
public class TicketingController {

    private final TicketingService ticketingService;
    private final GlobalLogger globalLogger;

    public TicketingController(TicketingService ticketingService, GlobalLogger globalLogger) {
        this.ticketingService = ticketingService;
        this.globalLogger = globalLogger;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSimulation() {
        ticketingService.startSimulation();
        return ResponseEntity.ok("Simulation started.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSimulation() {
        ticketingService.stopSimulation();
        return ResponseEntity.ok("Simulation stopped.");
    }

    @PostMapping("/vendor/single")
    public ResponseEntity<String> addVendor(@RequestParam String name, @RequestParam int rate) {
        ticketingService.addVendor(name, rate);
        return ResponseEntity.ok("Vendor added: " + name);
    }

    @PostMapping("/vendor/batch")
    public ResponseEntity<String> addVendor(@RequestBody Map<String, Integer> vendors) {
        GlobalLogger.logInfo(vendors.toString());
        vendors.forEach(ticketingService::addVendor);

        return ResponseEntity.ok("Vendors have been added! ");
    }

    @PostMapping("/customer/single")
    public ResponseEntity<String> addCustomer(@RequestParam String name, @RequestParam int rate) {
        ticketingService.addCustomer(name, rate);
        return ResponseEntity.ok("Customer added: " + name);
    }

    @PostMapping("/customer/batch")
    public ResponseEntity<String> addCustomer(@RequestBody Map<String, Integer> customers) {
        System.out.println(customers);

        customers.forEach(ticketingService::addCustomer);

        return ResponseEntity.ok("Customers have been added! ");
    }

    @GetMapping("/active-users")
    public ResponseEntity<Map<String, Map<String, String>>> activeUsers() {
        return ResponseEntity.ok(ticketingService.viewActiveUsers());
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
