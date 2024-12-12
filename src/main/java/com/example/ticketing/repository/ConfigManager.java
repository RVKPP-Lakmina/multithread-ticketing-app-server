package com.example.ticketing.repository;

import com.example.ticketing.model.Config;
import com.example.ticketing.stores.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static final String CONFIG_FILE = "src/main/resources/config.json";
    private final String configFilePath;
    private final ObjectMapper objectMapper;

    public ConfigManager(String configFilePath, ObjectMapper objectMapper) {
        this.configFilePath = configFilePath;
        this.objectMapper = objectMapper;
    }

    public static void initialize() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ConfigManager configManager = new ConfigManager(CONFIG_FILE, mapper);

        Config config;
        try {
            config = configManager.loadConfig();

            if (config == null) {
                throw new RuntimeException("Config file not found");
            }

        } catch (RuntimeException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            config = new Config(1000, 100);
            configManager.saveConfig(config); // Save default configuration
        }

        Store.setTotalTickets(config.getTotalTickets());
        Store.setMaxCapacity(config.getMaxCapacity());
    }

    public Config loadConfig() {
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            return null;
        }
        try {
            return objectMapper.readValue(configFile, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse configuration file: " + e.getMessage(), e);
        }
    }

    public void saveConfig(Config config) {
        try {
            objectMapper.writeValue(new File(configFilePath), config);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }
}
