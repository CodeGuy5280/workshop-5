package com.pluralsight;

public class SalesContract extends Contract {
    private double salesTaxAmount;
    private final double recordingFee = 100.0;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicle);
        this.isFinanced = isFinanced;
        this.salesTaxAmount = vehicle.getPrice() * 0.05;

        if (vehicle.getPrice() < 10000) {
            this.processingFee = 295.0;
        } else {
            this.processingFee = 495.0;
        }
    }

    public boolean isFinanced() {
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    @Override
    public double getTotalPrice() {
        double vehiclePrice = getVehicle().getPrice();
        return vehiclePrice + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinanced) {
            return 0.0;
        }

        double loanAmount = getTotalPrice();
        double monthlyPayment = 0.0;

        if (getVehicle().getPrice() >= 10000) {
            // 4.25% annual interest, 48 months
            double rate = 0.0425 / 12;
            int months = 48;
            monthlyPayment = (loanAmount * rate) / (1 - Math.pow(1 + rate, -months));
        } else {
            // 5.25% annual interest, 24 months
            double rate = 0.0525 / 12;
            int months = 24;
            monthlyPayment = (loanAmount * rate) / (1 - Math.pow(1 + rate, -months));
        }

        return monthlyPayment;
    }
}
