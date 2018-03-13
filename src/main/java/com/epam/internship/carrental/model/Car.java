package com.epam.internship.carrental.model;

import com.epam.internship.carrental.enums.CarType;
import com.epam.internship.carrental.enums.Gearbox;

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
    private Gearbox gearbox;

    public Car() {
    }

    public Car(String make, String model, CarType carType, int seats, double fuelUsage, Gearbox gearbox) {
        super();
        this.make = make;
        this.model = model;
        this.carType = carType;
        this.seats = seats;
        this.fuelUsage = fuelUsage;
        this.gearbox = gearbox;
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

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
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
