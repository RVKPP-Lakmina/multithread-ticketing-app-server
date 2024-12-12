package com.example.ticketing.model;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private final List<User> vendors;
    private final List<User> customers;

    public UserData() {
        this.vendors = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public List<User> getVendors() {
        return vendors;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void addVendor(User vendor) {
        vendors.add(vendor);
    }

    public void addCustomer(User customer) {
        customers.add(customer);
    }

    public void removeVendor(String vendorId) {
        vendors.removeIf(v -> v.getId().equals(vendorId));
    }

    public void removeCustomer(String customerId) {
        customers.removeIf(c -> c.getId().equals(customerId));
    }

    @Override
    public String toString() {
        return "Vendors: " + vendors + "\nCustomers: " + customers;
    }
}

