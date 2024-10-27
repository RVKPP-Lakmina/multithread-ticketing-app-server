package com.example.systemConfiguration.models.interfaces;

import java.util.Scanner;

public interface ConfigurationParameter {
    void setValue(int value);

    int getValue();

    String getItemName();

    int getInputAsInteger(Scanner scanner);

    boolean isValid(int value);

    String getDateKey();
}