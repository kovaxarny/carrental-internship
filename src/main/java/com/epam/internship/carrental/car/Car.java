package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * This is a simple entity class which models a Car object, which we use in the database.
 */
@Entity
public @Data @Builder @NoArgsConstructor @AllArgsConstructor
class Car {
    /**
     * The id is used for identifying the records in the database.
     * <p>
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

    /**
     * Updates the car defined as the first parameter, with the parameters of the second car
     * which is the second parameter.
     *
     * @param carToUpdate  updateable car
     * @param newCarParams car with the new parameters
     * @return updated Car object
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
}
