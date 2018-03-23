package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarType;
import com.epam.internship.carrental.car.enums.CarGearbox;

import javax.persistence.*;

/**
 * This is a simple entity class which models a Car object, which we use in the database.
 */
@Entity
public class Car {
    /**
     * The id is used for identifying the records in the database.
     *
     * It's value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The make field is used for defining the maker of the car.
     */
    @Column(nullable = false)
    private String make;

    /**
     * The model field represents the car's model.
     */
    @Column(nullable = false)
    private String model;

    /**
     * The carType can have only predefined values from
     * {@link com.epam.internship.carrental.car.enums.CarType}
     */
    @Enumerated(EnumType.STRING)
    private CarType carType;

    /**
     * The seats field holds the number of seats in a car.
     */
    @Column(nullable = false)
    private int seats;

    /**
     * The field fuelUsage is has the cars fuel usage per 100 km.
     */
    @Column(nullable = false)
    private double fuelUsage;

    /**
     * Gearbox field has its values declared in
     * {@link com.epam.internship.carrental.car.enums.CarGearbox}
     */
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

    /**
     * Updates the car defined as the first parameter, with the parameters of the second car
     * which is the second parameter.
     *
     * @param carToUpdate The updateable car
     * @param newCarParams The car with the new parameters
     * @return the updated Car object
     */
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
