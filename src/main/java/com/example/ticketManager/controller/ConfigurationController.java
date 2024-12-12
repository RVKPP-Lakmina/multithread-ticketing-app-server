package com.example.ticketManager.controller;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.service.ConfigurationService;
import com.example.ticketing.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    ConfigurationService configurationService;

    @Autowired
    ConfigurationController(WebSocketHandler webSocketHandler) {
        configurationService = new ConfigurationService(webSocketHandler);
    }

    @PostMapping
    public ResponseEntity<String> saveConfiguration(@RequestBody Config config) {
        configurationService.saveConfigs(config);
        return ResponseEntity.ok("Saved configuration");
    }
}
