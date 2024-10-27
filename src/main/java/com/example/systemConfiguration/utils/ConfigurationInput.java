package com.example.systemConfiguration.utils;

import com.example.systemConfiguration.models.interfaces.ConfigurationParameter;

import java.util.Scanner;

public class ConfigurationInput {
    private final Scanner scanner = new Scanner(System.in);

    public int userPromptForValues(ConfigurationParameter parameter) {
        System.out.printf("\nEnter the number of %s: ", parameter.getItemName());
        int userInput = parameter.getInputAsInteger(scanner);

        if (parameter.isValid(userInput)) {
            return userInput;
        } else {
            Banner.errorBanner("Invalid input. Please try again.");
            return userPromptForValues(parameter);
        }
    }
}
