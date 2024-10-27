package com.example.systemConfiguration.models;

import java.io.Serializable;

public class Configuration implements Serializable {
    private String key;
    private int value;

    public Configuration(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + ": " + this.value;
    }
}
