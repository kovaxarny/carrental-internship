package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A ViewObject class for the {@link com.epam.internship.carrental.rentedcar.RentedCar} class, for masking some information.
 */
public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CarViewObject {

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
     * Gearbox of a car, with possible values from {@link com.epam.internship.carrental.car.enums.CarGearbox}.
     */
    private CarGearbox gearbox;
}
