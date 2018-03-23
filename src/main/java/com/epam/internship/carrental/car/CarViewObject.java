package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;

public class CarViewObject {
    private String fullName;
    private int seats;
    private double fuelUsage;
    private CarGearbox gearbox;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getFuelUsage() {
        return fuelUsage;
    }

    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage = fuelUsage;
    }

    public CarGearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(CarGearbox gearbox) {
        this.gearbox = gearbox;
    }
}
