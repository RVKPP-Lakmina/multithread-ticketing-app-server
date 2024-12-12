package com.example.ticketing.view;

import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;
import com.example.ticketing.model.Config;
import com.example.ticketing.model.User;
import com.example.ticketing.model.UserData;
import com.example.ticketing.repository.ConfigManager;
import com.example.ticketing.repository.UserDataManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class Terminal {

    private static final String CONFIG_FILE = "src/main/resources/config.json";
    private static final String USERS_FILE = "src/main/resources/users.json";
    private final ObjectMapper objectMapper;
    private final Scanner scanner;

    public Terminal() {
        this.objectMapper = new ObjectMapper();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Terminal().run();
    }

    public void run() {
        try {
            System.out.println("\nWelcome to the Real-Time Ticketing System!");

            ConfigManager configManager = new ConfigManager(CONFIG_FILE, objectMapper);
            UserDataManager userDataManager = new UserDataManager(USERS_FILE, objectMapper);

            UserData userData = userDataManager.loadUserData();

            Config config = configManager.loadConfig();
            Config userConfig = promptForConfig(config);

            TicketingSystemSimulator simulation = new SimulationTask(userConfig.getTotalTickets(), userConfig.getMaxCapacity());

            if (promptForPreviousUsers(userData)) {
                initializeUsers(simulation, userData);
            } else {
                userData = new UserData();
            }

            processCommands(simulation, userData, userDataManager);

        } catch (Exception e) {
            System.err.println("An error occurred. Restarting the terminal.");
            run();
        } finally {
            scanner.close();
        }
    }

    private Config promptForConfig(Config config) {
        if (config != null && prompt("Previous configuration found:\n" + config + "\nUse previous configuration? (yes/no): ")) {
            return config;
        }

        int totalTickets = promptInt("Enter total tickets: ");
        int maxCapacity = promptInt("Enter max ticket capacity: ");
        return new Config(totalTickets, maxCapacity);
    }

    private boolean promptForPreviousUsers(UserData userData) {

        if(userData.getVendors().isEmpty() && userData.getCustomers().isEmpty()) {
            return true;
        }

        if (userData != null && prompt("Previous users found:\n" + userData + "\nUse previous users? (yes/no): ")) {
            return true;
        }


        return false;
    }

    private void initializeUsers(TicketingSystemSimulator simulation, UserData userData) {
        userData.getVendors().forEach(v -> simulation.addVendor(v.getId(), v.getName(), v.getRate()));
        userData.getCustomers().forEach(c -> simulation.addCustomer(c.getId(), c.getName(), c.getRate()));
    }

    private void processCommands(TicketingSystemSimulator simulation, UserData userData, UserDataManager userDataManager) {
        System.out.println("\nType 'tsc --help' for a list of commands.\n");

        while (true) {
            String command = promptString(">>: ");
            String[] parts = command.split(" ");

            if (parts.length == 0) {
                continue;
            }

            try {
                switch (parts[0].toLowerCase()) {
                    case "exit":
                        simulation.shutdownSystem();
                        return;
                    case "restart":
                        restartSystem();
                        return;
                    case "tsc":
                        handleTscCommand(parts, simulation, userData, userDataManager);
                        break;
                    default:
                        System.err.printf(" '%s' Command not recognized.%n", command);
                        break;
                }
            } catch (Exception e) {
                System.out.println(" Error processing command. Please try again.");
            }
        }
    }

    private void handleTscCommand(String[] parts, TicketingSystemSimulator simulation, UserData userData, UserDataManager userDataManager) {
        switch (parts[1].toLowerCase()) {
            case "start":
                simulation.startTask();
                break;
            case "stop":
                simulation.stopTask();
                break;
            case "add-vendor":
                String vendorId = parts[2];
                int vendorRate = Integer.parseInt(parts[3]);
                simulation.addVendor(vendorId, vendorRate);
                userData.addVendor(new User(vendorId, vendorRate));
                userDataManager.saveUserData(userData);
                break;
            case "add-customer":
                String customerId = parts[2];
                int customerRate = Integer.parseInt(parts[3]);
                simulation.addCustomer(customerId, customerRate);
                userData.addCustomer(new User(customerId, customerRate));
                userDataManager.saveUserData(userData);
                break;
            case "remove-vendor":
                simulation.removeVendor(parts[2]);
                userData.removeVendor(parts[2]);
                userDataManager.saveUserData(userData);
                break;
            case "remove-customer":
                simulation.removeCustomer(parts[2]);
                userData.removeCustomer(parts[2]);
                userDataManager.saveUserData(userData);
                break;
            case "--view-system":
                simulation.viewSystemStatus();
                break;
            case "--view-users":
                System.out.println(userData);
                break;
            case "--help":
                Commands.help();
                break;
            default:
                System.out.println(" Unknown command.");
        }
    }

    private void restartSystem() {
        System.out.println("Restarting the system...");
        run();
    }

    private boolean prompt(String message) {
        System.out.print(message);
        boolean answer = scanner.nextLine().toLowerCase().equals("yes");
        return answer;
    }

    private int promptInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    private String promptString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}