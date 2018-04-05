package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarType;

import javax.validation.constraints.NotNull;

/**
 * Converter utility class, which enables conversion between a Car and a CarViewObject.
 */
public final class CarConverter {

    /**
     * Private constructor to prevent instantiation.
     */
    private CarConverter() {
    }

    /**
     * The method converts the CarViewObject given in the parameter into a Car.
     *
     * @param carViewObject CarViewObject to be converted
     * @return converted Car object
     */
    public static Car carFromCarViewObject(@NotNull final CarViewObject carViewObject) {
        String[] splitCarViewObjectName = carViewObject.getFullName().split("\\s+");
        return Car.builder()
                .make(splitCarViewObjectName[0])
                .model(splitCarViewObjectName[1])
                .carType(CarType.valueOf(splitCarViewObjectName[2]))
                .seats(carViewObject.getSeats())
                .fuelUsage(carViewObject.getFuelUsage())
                .gearbox(carViewObject.getGearbox())
                .build();
    }

    /**
     * Converts a Car object into a CarViewObject.
     *
     * @param car Car to be converted
     * @return converted CarViewObject
     */
    public static CarViewObject carViewObjectFromCar(@NotNull final Car car) {
        CarViewObject carViewObject = new CarViewObject();
        carViewObject.setFullName(car.getMake() + " " + car.getModel() + " " + car.getCarType());
        carViewObject.setFuelUsage(car.getFuelUsage());
        carViewObject.setSeats(car.getSeats());
        carViewObject.setGearbox(car.getGearbox());
        return carViewObject;
    }
}