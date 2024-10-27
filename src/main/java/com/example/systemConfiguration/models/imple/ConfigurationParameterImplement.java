package com.example.systemConfiguration.models.imple;

import com.example.systemConfiguration.models.interfaces.ConfigurationParameter;
import com.example.systemConfiguration.utils.Banner;

import java.util.Scanner;

public abstract class ConfigurationParameterImplement implements ConfigurationParameter {
    private int value;
    private String name = "";
    private String datakey = "";

    @Override
    public void setValue(int value) {

        if (isValid(value)) {
            this.value = value;
            System.out.printf("%s set to %d\n", name, this.value);
        } else {
            Banner.errorBanner("Invalid input. Please enter a positive integer.");
        }
    }

    @Override
    public String getDateKey() {
        return this.datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public boolean isValid(int value) {
        return value > 0;
    }

    @Override
    public int getInputAsInteger(Scanner scanner) {

        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next();
            return -1;
        }
    }
}
