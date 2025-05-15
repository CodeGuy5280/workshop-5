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
                    System.out.println("Feature not implemented yet.");
                    break;
                case "3":
                    processSellOrLeaseVehicle();  // New feature added here
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
        System.out.println("3. Sell/Lease a vehicle");  // New option
        System.out.println("0. Exit");
    }
    private void processSellOrLeaseVehicle() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter VIN of the vehicle: ");
        int vin = Integer.parseInt(scanner.nextLine());

        Vehicle selectedVehicle = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                selectedVehicle = v;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Is this a Sale or Lease? (Enter 'sale' or 'lease'): ");
        String contractType = scanner.nextLine().toLowerCase();

        String date = java.time.LocalDate.now().toString();

        Contract contract = null;

        if (contractType.equals("sale")) {
            System.out.print("Will the customer finance the vehicle? (yes/no): ");
            boolean isFinanced = scanner.nextLine().equalsIgnoreCase("yes");

            contract = new SalesContract(date, customerName, customerEmail, selectedVehicle, isFinanced);
        } else if (contractType.equals("lease")) {
            int currentYear = java.time.LocalDate.now().getYear();
            if (currentYear - selectedVehicle.getYear() > 3) {
                System.out.println("Cannot lease vehicles older than 3 years.");
                return;
            }

            contract = new LeaseContract(date, customerName, customerEmail, selectedVehicle);
        } else {
            System.out.println("Invalid contract type.");
            return;
        }

        // Display contract summary
        System.out.println("\n--- Contract Summary ---");
        System.out.println("Customer: " + customerName);
        System.out.println("Email: " + customerEmail);
        System.out.println("Vehicle: " + selectedVehicle.getYear() + " " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
        System.out.println("Total Price: $" + contract.getTotalPrice());
        System.out.println("Monthly Payment: $" + contract.getMonthlyPayment());

        // Remove vehicle from inventory after sale/lease
        dealership.getAllVehicles().remove(selectedVehicle);
        System.out.println("Vehicle removed from inventory.");
    }


}