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
                    getAllVehicles();
                    break;
                case "2":
                    addOrDeleteVehicle(scanner);
                    break;
                case "3":
                    sellOrLeaseVehicle(scanner);
                    break;
                case "0":
                    System.out.println("Exiting the Dealership App. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select 1, 2, 3, or 0.");
            }
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n--- Dealership Menu ---");
        System.out.println("1. View all vehicles");
        System.out.println("2. Add or Delete a vehicle");
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

    private void getAllVehicles() {
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

    private void addOrDeleteVehicle(Scanner scanner) {
        System.out.println("\nAdd or Delete a Vehicle");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Delete Vehicle");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                addVehicle(scanner);
                break;
            case "2":
                deleteVehicle(scanner);
                break;
            default:
                System.out.println("Invalid option, returning to main menu.");
        }
    }

    private void addVehicle(Scanner scanner) {
        try {
            System.out.print("Enter VIN (number): ");
            int vin = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Make: ");
            String make = scanner.nextLine().trim();

            System.out.print("Enter Model: ");
            String model = scanner.nextLine().trim();

            System.out.print("Enter Vehicle Type: ");
            String vehicleType = scanner.nextLine().trim();

            System.out.print("Enter Color: ");
            String color = scanner.nextLine().trim();

            System.out.print("Enter Odometer reading (number): ");
            int odometer = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            Vehicle newVehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
            dealership.addVehicle(newVehicle);

            System.out.println("Vehicle added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    private void deleteVehicle(Scanner scanner) {
        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles available to delete.");
            return;
        }

        displayVehicles(vehicles);

        System.out.print("Enter the VIN of the vehicle to delete: ");
        try {
            int vinToDelete = Integer.parseInt(scanner.nextLine().trim());

            Vehicle vehicleToDelete = null;
            for (Vehicle v : vehicles) {
                if (v.getVin() == vinToDelete) {
                    vehicleToDelete = v;
                    break;
                }
            }

            if (vehicleToDelete != null) {
                dealership.removeVehicle(vehicleToDelete);
                System.out.println("Vehicle removed successfully.");
            } else {
                System.out.println("Vehicle with VIN " + vinToDelete + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid VIN entered.");
        }
    }

    private void sellOrLeaseVehicle(Scanner scanner) {
        if (dealership == null) {
            System.out.println("Dealership is not initialized.");
            return;
        }

        ArrayList<Vehicle> vehicles = dealership.getAllVehicles();
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles available for sale or lease.");
            return;
        }

        displayVehicles(vehicles);

        System.out.print("Enter the VIN of the vehicle you want to sell or lease: ");
        try {
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

            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine();

            System.out.print("Enter customer email: ");
            String customerEmail = scanner.nextLine();

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

            System.out.println("\n--- Contract Summary ---");
            System.out.println("Customer: " + customerName);
            System.out.println("Email: " + customerEmail);
            System.out.println("Vehicle: " + selectedVehicle.getYear() + " " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
            System.out.println("Total Price: $" + contract.getTotalPrice());
            System.out.println("Monthly Payment: $" + contract.getMonthlyPayment());

            dealership.removeVehicle(selectedVehicle);
            System.out.println("Vehicle removed from inventory.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid VIN entered.");
        }
    }

    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}
