package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarType;

import javax.validation.constraints.NotNull;

/**
 * Converter utility class, which enables conversion between a Car and a CarViewObject
 */
public class CarConverter {

    private CarConverter() {
    }

    /**
     * The method converts the CarViewObject given in the parameter into a Car.
     *
     * @param carViewObject CarViewObject to be converted
     * @return converted Car object
     */
    public static Car carFromCarViewObject(@NotNull CarViewObject carViewObject) {
        String[] splitedCarViewObjectName = carViewObject.getFullName().split("\\s+");
        return Car.builder()
                .make(splitedCarViewObjectName[0])
                .model(splitedCarViewObjectName[1])
                .carType(CarType.valueOf(splitedCarViewObjectName[2]))
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
    public static CarViewObject carViewObjectFromCar(@NotNull Car car) {
        CarViewObject carViewObject = new CarViewObject();
        carViewObject.setFullName(car.getMake() + " " + car.getModel() + " " + car.getCarType());
        carViewObject.setFuelUsage(car.getFuelUsage());
        carViewObject.setSeats(car.getSeats());
        carViewObject.setGearbox(car.getGearbox());
        return carViewObject;
    }
}
