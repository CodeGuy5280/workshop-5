package com.pluralsight;


public class Vehicle {
//fields
    int vin;
    int year;
    String make;
    String model;
    String vehicleType;
    String color;
    int odometer;
    double price;

    public void setVin(int vin) {
        this.vin = vin;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //constructor
public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
    this.vin = vin;
    this.year = year;
    this.make = make;
    this.model = model;
    this.vehicleType = vehicleType;
    this.color = color;
    this.odometer = odometer;
    this.price = price;
}
Vehicle vehicle = new Vehicle(314313, 2005, "Ford", "Explorer", "SUV", "black", 45000,67000);

//getters
    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
