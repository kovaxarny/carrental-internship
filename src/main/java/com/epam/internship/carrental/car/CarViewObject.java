package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;

/**
 * A ViewObject class for the {@link com.epam.internship.carrental.car.Car} class, for masking some information.
 */
public class CarViewObject {

    /**
     * Full name of a car in a maker model carType format.
     */
    private String fullName;

    /**
     * Number of seats in a car.
     */
    private int seats;

    /**
     * FuelUsage of a car in liters/100km.
     */
    private double fuelUsage;

    /**
     * Gearbox of a car, with possible values from {@link com.epam.internship.carrental.car.enums.CarGearbox}
     */
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
