package com.example.systemConfiguration.repository.imple;

import com.example.systemConfiguration.models.Configuration;
import com.example.systemConfiguration.models.interfaces.ConfigurationParameter;
import com.example.systemConfiguration.repository.interfaces.FileStoreInterface;
import com.example.systemConfiguration.utils.Banner;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public abstract class JsonFileCreate implements FileStoreInterface {
    private final String fileName;
    private final String filePath;
    final String _dirPath = "configurations/config-development/";


    protected JsonFileCreate(String fileName) {
        this.fileName = fileName;
        this.filePath = this._dirPath + this.fileName + ".json";
    }

    @Override
    public void writeFile(File file) {
    }

    @Override
    public void writeFile(List<ConfigurationParameter> data) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Integer> configMap = new HashMap<>();

            data.forEach((config) -> {
                configMap.put(config.getDateKey(), config.getValue());
            });

            objectMapper.writeValue(new File(this.filePath), configMap);
            System.out.println("JSON written to output.json");
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

}
