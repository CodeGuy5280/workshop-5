package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;

    public void display() {
        init();
        System.out.println("Welcome to the Dealership App");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Enter your choice: ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    processGetAllVehicles();
                    break;
                case "2":
                    System.out.println("Feature not implemented yet. Placeholder.");
                    break;
                case "3":
                    processSellOrLeaseVehicle();
                    break;
                case "0":
                    System.out.println("Exiting the Dealership App. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select 1, 2, 3, or 0.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n--- Dealership Menu ---");
        System.out.println("1. View all vehicles");
        System.out.println("2. Other option (placeholder)");
        System.out.println("3. Sell or Lease a vehicle");
        System.out.println("0. Exit");
    }

    private void init() {
        DealershipFileManager dfm = new DealershipFileManager();
        this.dealership = dfm.getDealership("C:\\Users\\AlexJ\\pluralsight\\workshop-5\\Dealership\\WorkshopFiles\\inventory.csv");

        if (this.dealership == null) {
            System.out.println("Error: Dealership failed to load.");
        }
    }

    private void processGetAllVehicles() {
        if (dealership == null) {
            System.out.println("Dealership is not initialized.");
            return;
        }

        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found in inventory.");
        } else {
            displayVehicles(vehicles);
        }
    }

    private void displayVehicles(ArrayList<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles available in the inventory.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.getVin() + " | " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " | $" + vehicle.getPrice());
        }
    }


    private void processSellOrLeaseVehicle() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Show vehicles
        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);

        // Step 2: Select vehicle by VIN
        System.out.print("Enter the VIN of the vehicle you want to sell or lease: ");
        int vin = Integer.parseInt(scanner.nextLine().trim());

        Vehicle selectedVehicle = null;
        for (Vehicle v : vehicles) {
            if (v.getVin() == vin) {
                selectedVehicle = v;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle with VIN " + vin + " not found.");
            return;
        }

        // Step 3: Customer info
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();

        // Step 4: Sale or Lease?
        System.out.print("Is this a Sale (S) or Lease (L)? ");
        String type = scanner.nextLine();

        Contract contract = null;

        if (type.equalsIgnoreCase("S")) {
            System.out.print("Do they want to finance? (yes/no): ");
            String financeChoice = scanner.nextLine();
            boolean isFinanced = financeChoice.equalsIgnoreCase("yes");

            contract = new SalesContract(getCurrentDate(), customerName, customerEmail, selectedVehicle, isFinanced);

        } else if (type.equalsIgnoreCase("L")) {
            if (selectedVehicle.getYear() <= (java.time.LocalDate.now().getYear() - 3)) {
                System.out.println("Cannot lease vehicles over 3 years old.");
                return;
            }

            contract = new LeaseContract(getCurrentDate(), customerName, customerEmail, selectedVehicle);

        } else {
            System.out.println("Invalid choice. Please enter 'S' or 'L'.");
            return;
        }

        // Step 5: Contract summary
        System.out.println("\n--- Contract Summary ---");
        System.out.println("Customer: " + customerName);
        System.out.println("Email: " + customerEmail);
        System.out.println("Vehicle: " + selectedVehicle.getYear() + " " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
        System.out.println("Total Price: $" + contract.getTotalPrice());
        System.out.println("Monthly Payment: $" + contract.getMonthlyPayment());

        // Remove vehicle from inventory
        dealership.removeVehicle(selectedVehicle);
        System.out.println("Vehicle removed from inventory.");
    }

    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}
