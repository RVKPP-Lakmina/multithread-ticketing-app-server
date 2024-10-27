package com.example.systemConfiguration;

import com.example.systemConfiguration.models.*;
import com.example.systemConfiguration.models.interfaces.*;
import com.example.systemConfiguration.repository.Configurations;
import com.example.systemConfiguration.repository.interfaces.FileStoreInterface;
import com.example.systemConfiguration.utils.*;

import java.util.ArrayList;
import java.util.List;

public class SystemConfiguration {
    private final ConfigurationParameter totalTickets;
    private final ConfigurationParameter ticketReleaseRate;
    private final ConfigurationParameter customerRetrievalRate;
    private final ConfigurationParameter maxTicketCapacity;
    private final ConfigurationInput input;
    private final List<ConfigurationParameter> list;
    private final FileStoreInterface fileStore;

    public SystemConfiguration() {
        this.totalTickets = new TotalTickets();
        this.ticketReleaseRate = new TicketReleaseRate();
        this.customerRetrievalRate = new CustomerRetrievalRate();
        this.maxTicketCapacity = new MaxTicketCapacity();
        this.input = new ConfigurationInput();
        this.list = new ArrayList<ConfigurationParameter>(List.of(
                this.totalTickets,
                this.ticketReleaseRate,
                this.customerRetrievalRate,
                this.maxTicketCapacity
        ));
        this.fileStore = new Configurations("ticketingAppConfiguration");
    }

    public void configureSystem() {
        Banner.printBanner("Enter Values for Configuration Panel");
        this.list.forEach((object) -> object.setValue(input.userPromptForValues(object)));

        this.fileStore.writeFile(this.list);
        displayConfiguration();
    }

    private void displayConfiguration() {
        Banner.printBanner("System configured successfully with the following settings");
        this.list.forEach((object) -> System.out.println(object.getItemName() + ": " + object.getValue()));
    }

    public static void main(String[] args) {
        SystemConfiguration systemConfiguration = new SystemConfiguration();
        systemConfiguration.configureSystem();
    }
}
