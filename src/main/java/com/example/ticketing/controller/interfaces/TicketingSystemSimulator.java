package com.example.ticketing.controller.interfaces;

import java.util.Map;

/**
 * Interface for simulation tasks in the ticketing system.
 * Provides methods to manage vendors and customers, control the simulation,
 * and retrieve system status and ticket information.
 */
public interface TicketingSystemSimulator {

    /**
     * Starts the simulation.
     */

    void startTask();

    /**
     * Stops the simulation.
     */
    void stopTask();

    /**
     * Adds a vendor to the simulation.
     *
     * @param name   the unique identifier of the vendor
     * @param rate the rate at which the vendor operates
     */
    void addVendor(String name, int rate);

    void addVendor(String id, String name, int rate);

    /**
     * Adds a customer to the simulation.
     *
     * @param name   the unique identifier of the customer
     * @param rate the rate at which the customer operates
     */
    void addCustomer(String name, int rate);

    void addCustomer(String id, String name, int rate);

    /**
     * Removes a vendor from the simulation.
     *
     * @param id the unique identifier of the vendor to be removed
     */
    void removeVendor(String id);

    /**
     * Removes a customer from the simulation.
     *
     * @param id the unique identifier of the customer to be removed
     */
    void removeCustomer(String id);

    /**
     * Stops the simulation.
     */
    void stopSimulation();

    /**
     * Shuts down the entire system.
     */
    void shutdownSystem();

    /**
     * Views users by their type (vendor or customer).
     *
     * @param type the type of user to view (vendor or customer)
     */
    void viewUserByType(String type);

    Map<String, Map<String, String>> viewUserByType();

    /**
     * Views all users in the system.
     */
    void viewUsers();

    /**
     * Views the current status of the system.
     */
    void viewSystemStatus();
}
