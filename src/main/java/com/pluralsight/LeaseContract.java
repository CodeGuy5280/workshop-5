package com.pluralsight;

public class LeaseContract extends Contract {
    private final double leaseFee = 500.0;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    @Override
    public double getTotalPrice() {
        // Lease total price = vehicle price * 50% + lease fee
        double vehiclePrice = getVehicle().getPrice();
        return (vehiclePrice * 0.5) + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        // Lease monthly payment = 7% of vehicle price divided by 36 months
        double vehiclePrice = getVehicle().getPrice();
        double monthlyPayment = (vehiclePrice * 0.07) / 36;
        return monthlyPayment;
    }

    public double getLeaseFee() {
        return leaseFee;
    }
}

