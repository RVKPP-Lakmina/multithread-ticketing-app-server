package com.example.ticketing.controller.interfaces;

/**
 * Interface for simulation tasks in the ticketing system.
 * Provides methods to manage vendors and customers, control the simulation,
 * and retrieve system status and ticket information.
 */
public interface TicketingSystemSimulator {

    /**
     * Adds a vendor to the simulation.
     * 
     * @param id   the unique identifier of the vendor
     * @param rate the rate at which the vendor operates
     */
    public void addVendor(String id, int rate);

    /**
     * Adds a customer to the simulation.
     * 
     * @param id   the unique identifier of the customer
     * @param rate the rate at which the customer operates
     */
    public void addCustomer(String id, int rate);

    /**
     * Removes a vendor from the simulation.
     * 
     * @param id the unique identifier of the vendor to be removed
     */
    public void removeVendor(String id);

    /**
     * Removes a customer from the simulation.
     * 
     * @param id the unique identifier of the customer to be removed
     */
    public void removeCustomer(String id);

    /**
     * Stops the simulation.
     */
    public void stopSimulation();

    /**
     * Shuts down the entire system.
     */
    public void shutdownSystem();

    /**
     * Views users by their type (vendor or customer).
     * 
     * @param type the type of user to view (vendor or customer)
     */
    public void viewUserByType(String type);

    /**
     * Views all users in the system.
     */
    public void viewUsers();

    /**
     * Views the current status of the system.
     */
    public void viewSystemStatus();
}
