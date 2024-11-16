package com.example.systemConfiguration.repository.imple;

import com.example.systemConfiguration.models.interfaces.ConfigurationParameter;
import com.example.systemConfiguration.repository.interfaces.FileStoreInterface;
import com.example.systemConfiguration.utils.Banner;
import com.example.ticketingapp.model.model.TicketPoolConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonFileCreate implements FileStoreInterface {
    private final String fileName;
    private final String filePath;
    private final String dirPath = "src/main/resources/configurations/config-development/";

    protected JsonFileCreate(String fileName) {
        this.fileName = fileName;
        this.filePath = dirPath + this.fileName + ".json";

        // Ensure the directory exists
        File directory = new File(dirPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + dirPath);
            }
        }
    }

    @Override
    public void writeFile(File file) {
        // Placeholder implementation if needed
    }

    @Override
    public void writeFile(List<ConfigurationParameter> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Integer> configMap = new HashMap<>();

            // Populate the configuration map
            for (ConfigurationParameter config : data) {
                configMap.put(config.getDateKey(), config.getValue());
            }

            // Write the JSON to the file
            File jsonFile = new File(filePath);
            objectMapper.writeValue(jsonFile, configMap);

            System.out.println("JSON written to " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            Banner.errorBanner(e.getMessage());
        }
    }

    @Override
    public void updateFile() {
    }

    @Override
    public List<ConfigurationParameter> readFile(File file) {
        return new ArrayList<>();
    }

    @Override
    public TicketPoolConfig readFile() {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(this.filePath), TicketPoolConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong | JsonFileCreate |  readFile", e);
        }
    }
}
