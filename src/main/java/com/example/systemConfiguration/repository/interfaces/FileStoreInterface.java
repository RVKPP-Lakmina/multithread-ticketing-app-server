package com.example.systemConfiguration.repository.interfaces;

import com.example.systemConfiguration.models.interfaces.ConfigurationParameter;

import java.io.File;
import java.util.List;

public interface FileStoreInterface {

    List<ConfigurationParameter> readFile(File file);

    void writeFile(File file);

    void writeFile(List<ConfigurationParameter> data);

    void updateFile();
}
