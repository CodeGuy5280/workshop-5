package com.pluralsight;

import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory = new ArrayList<>();

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return inventory;
    }

    public void removeVehicle(Vehicle vehicle) {
        inventory.remove(vehicle);
    }
}
