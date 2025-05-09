package com.pluralsight;

import java.util.ArrayList;



public class Dealership {
    String name;
    String address;
    String phone;

    private ArrayList<Vehicle>inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<Vehicle>();
    }

    public ArrayList<Vehicle> getAllVehicles() {
        return inventory;
    }


    private ArrayList<Vehicle>getVehiclesByPrice(){
        return null;
    }


    private ArrayList<Vehicle>getVehicleByMakeModel(){
        return null;
    }


    private ArrayList<Vehicle>getVehicleByYear(){
        return null;
    }


    private ArrayList<Vehicle>getVehicleByColor(){
        return null;
    }


    private ArrayList<Vehicle>getVehicleByMileage(){
        return null;
    }


    private ArrayList<Vehicle>getVehicleByType(){
        return null;
    }


    //add vehicle
    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }
    //remove vehicle
    public void removeVehicle(){

    }
}
