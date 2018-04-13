package com.epam.internship.carrental.service.car;

import com.epam.internship.carrental.service.car.enums.CarGearbox;
import com.epam.internship.carrental.service.car.enums.CarType;
import lombok.*;

import javax.persistence.*;

/**
 * This is a simple entity class which models a Car object, which we use in the database.
 */
@Entity
public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
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
     * The make field is represents the maker of the car.
     */
    @Column(nullable = false)
    private String make;

    /**
     * The model field represents the car's model.
     */
    @Column(nullable = false)
    private String model;

    /**
     * The carType field represents the car's type
     * The carType can have only predefined values from
     * {@link com.epam.internship.carrental.service.car.enums.CarType}
     */
    @Enumerated(EnumType.STRING)
    private CarType carType;

    /**
     * The seats field represents the number of seats in a car.
     */
    @Column(nullable = false)
    private int seats;

    /**
     * The fuelUsage represents the cars fuel usage per 100 km.
     */
    @Column(nullable = false)
    private double fuelUsage;

    /**
     * The gearbox field represents the cars gearbox type.
     * Gearbox field has its values declared in
     * {@link com.epam.internship.carrental.service.car.enums.CarGearbox}
     */
    @Enumerated(EnumType.STRING)
    private CarGearbox gearbox;
}
