package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DealershipFileManager {
    public Dealership getDealership(String filePath) {
        Dealership dealership = new Dealership("Automotive1", "450 E Vermont St", "303-445-6321");
        try (BufferedReader csvReader = new BufferedReader(new FileReader("inventory.csv"))) {
            String line;
            while ((line = csvReader.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 8) {
                    int vin = Integer.parseInt(fields[0]);
                    int year = Integer.parseInt(fields[1]);
                    String make = fields[2];
                    String model = fields[3];
                    String vehicleType = fields[4];
                    String color = fields[5];
                    int odometer = Integer.parseInt(fields[6]);
                    double price = Double.parseDouble(fields[7]);

                    Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                    dealership.addVehicle(vehicle);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in CSV: " + e.getMessage());
        }
        return dealership;
    }
    //save dealership
    public ArrayList<Vehicle> saveDealership(){
        return null;
    }
}