package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;

    public UserInterface() {
        // Constructor can remain empty
    }

    public void display() {
        init();
        System.out.println("Loading dealerships...");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Enter your choice: ");
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    processGetAllVehicles();
                    break;
                case "2":
                    // You can add another process method here
                    System.out.println("Feature not implemented yet.");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private void init() {
        DealershipFileManager dfm = new DealershipFileManager();
        this.dealership = dfm.getDealership("path/to/inventory.csv");  // Replace with actual path
    }

    private void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle); // Make sure toString() is implemented in Vehicle
        }
    }

    private void processGetAllVehicles() {
        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    private void showMenu() {
        System.out.println("\n--- Dealership Menu ---");
        System.out.println("1. View all vehicles");
        System.out.println("2. Other option");
        System.out.println("0. Exit");
    }
}