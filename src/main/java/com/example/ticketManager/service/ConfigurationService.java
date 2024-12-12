package com.example.ticketManager.service;

import com.example.ticketManager.component.WebSocketHandler;
import com.example.ticketManager.util.Logger;
import com.example.ticketing.model.Config;
import com.example.ticketing.repository.ConfigManager;
import com.example.ticketing.util.interfaces.IEventLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.EventLogger;

public class ConfigurationService {
    private static final String CONFIG_FILE = "src/main/resources/config.json";
    private final ObjectMapper mapper;
    IEventLogger eventLogger;

    public ConfigurationService(WebSocketHandler webSocketHandler) {
        mapper = new ObjectMapper();
        eventLogger = new Logger(webSocketHandler);
    }

    public void saveConfigs(Config config) {
        try {
            ConfigManager configManager = new ConfigManager(CONFIG_FILE, this.mapper);

            configManager.saveConfig(config);
            ConfigManager.initialize();
            eventLogger.log("Configuration saved");
        } catch (Exception e) {
            eventLogger.error("Something went wrong while saving configuration");
            throw new RuntimeException(e);
        }

    }
}
