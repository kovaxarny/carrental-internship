package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarType;
import com.epam.internship.carrental.car.enums.CarGearbox;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    private CarType carType;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = false)
    private double fuelUsage;

    @Enumerated(EnumType.STRING)
    private CarGearbox gearbox;

    public Car() {
    }

    public Car(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox gearbox) {
        super();
        this.make = make;
        this.model = model;
        this.carType = carType;
        this.seats = seats;
        this.fuelUsage = fuelUsage;
        this.gearbox = gearbox;
    }

    public static Car updateCar(Car carToUpdate, Car newCarParams) {
        if (newCarParams.getCarType() != null) carToUpdate.setCarType(newCarParams.getCarType());
        if (newCarParams.getFuelUsage() != 0.0) carToUpdate.setFuelUsage(newCarParams.getFuelUsage());
        if (newCarParams.getGearbox() != null) carToUpdate.setGearbox(newCarParams.getGearbox());
        if (newCarParams.getMake() != null) carToUpdate.setMake(newCarParams.getMake());
        if (newCarParams.getModel() != null) carToUpdate.setModel(newCarParams.getModel());
        if (newCarParams.getSeats() != 0) carToUpdate.setSeats(newCarParams.getSeats());
        return carToUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
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

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", carType=" + carType +
                ", seats=" + seats +
                ", fuelUsage=" + fuelUsage +
                ", gearbox=" + gearbox +
                '}';
    }
}
