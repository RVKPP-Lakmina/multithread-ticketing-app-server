package com.example.ticketing.view;

import com.example.ticketing.controller.SimulationTask;
import com.example.ticketing.controller.interfaces.TicketingSystemSimulator;

import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) {
        new Terminal().run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command;

        try {
            System.out.println("\nWelcome to the Real-Time Ticketing System!");

            System.out.print("\nEnter total tickets: ");
            int totalTickets = scanner.nextInt();

            System.out.print("Enter max ticket capacity: ");
            int maxCapacity = scanner.nextInt();
            scanner.nextLine();
            TicketingSystemSimulator simulation = new SimulationTask(totalTickets, maxCapacity);

            System.out.println("\nType 'tsc --help' for a list of commands.\n");

            while (true) {
                System.out.print(">>:");
                command = scanner.nextLine();

                String[] parts = command.split(" ");

                if (parts.length == 0) {
                    continue;
                }

                if (parts[0].equalsIgnoreCase("exit")) {
                    simulation.shutdownSystem();
                    break;
                }

                if (parts[0].equalsIgnoreCase("restart")) {
                    System.out.println("Restarting the system...");
                    simulation.shutdownSystem();
                    Thread.sleep(2000);
                    System.out.println("");
                    System.out.println("""
                            Starting the system...
                            """);
                    new Terminal().run();
                }

                if (!parts[0].equalsIgnoreCase("tsc")) {
                    System.err.printf(" '%s' Command not recognized. Please try again.", command);
                    System.out.println("\n Type 'tsc --help' for a list of commands.");
                    continue;
                }

                try {
                    switch (parts[1]) {
                        case "start":
                            simulation.startTask();
                            break;
                        case "stop":
                            simulation.stopTask();
                            break;
                        case "add-vendor":
                            simulation.addVendor(parts[2], Integer.parseInt(parts[3]));
                            break;
                        case "add-customer":
                            simulation.addCustomer(parts[2], Integer.parseInt(parts[3]));
                            break;
                        case "remove-vendor":
                            simulation.removeVendor(parts[2]);
                            break;
                        case "remove-customer":
                            simulation.removeCustomer(parts[2]);
                            break;
                        case "--view-system":
                            simulation.viewSystemStatus();
                            break;
                        case "--view-users":
                            simulation.viewUsers();
                            break;
                        case "--help":
                            Commands.help();
                            break;
                        default:
                            System.out.println(" Unknown command.");
                    }
                } catch (Exception e) {
                    System.out.println(" Error processing command. Please try again.");
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred. Please try again.");
            run();
        } finally {
            scanner.close();
        }
    }
}
