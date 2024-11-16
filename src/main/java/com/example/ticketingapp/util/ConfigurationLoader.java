package com.example.ticketingapp.util;

import com.example.systemConfiguration.repository.Configurations;
import com.example.ticketingapp.model.model.TicketPoolConfig;

public class ConfigurationLoader {

    public static TicketPoolConfig configs = null;

    public static TicketPoolConfig getConfig() {

        if (configs == null) {
            config();
        }
        return configs;
    }

    private static void config() {
        try {
            Configurations configurations = new Configurations("ticketingAppConfiguration");
            configs = configurations.readFile();

            System.out.println(configs);

        } catch (Exception e) {
            throw new RuntimeException("something went wrong | ConfigurationLoader | config  ", e);
        }

    }

    public static void reloadConfig() {
        configs = null;
        config();
    }

}
