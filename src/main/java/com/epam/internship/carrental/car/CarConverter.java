package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarType;

import javax.validation.constraints.NotNull;

/**
 * Converter utility class, which enables conversion between a Car and a CarViewObject
 */
public class CarConverter {
    /**
     * The method converts the CarViewObject given in the parameter into a Car.
     *
     * @param carViewObject CarViewObject to be converted
     * @return converted Car object
     */
    public static Car CarFromCarViewObject (@NotNull CarViewObject carViewObject){
        Car car = new Car();
        String[] splitedCarViewObjectName = carViewObject.getFullName().split("\\s+");
        car.setMake(splitedCarViewObjectName[0]);
        car.setModel(splitedCarViewObjectName[1]);
        car.setCarType(CarType.valueOf(splitedCarViewObjectName[2]));
        car.setSeats(carViewObject.getSeats());
        car.setFuelUsage(carViewObject.getFuelUsage());
        car.setGearbox(carViewObject.getGearbox());
        return car;
    }

    /**
     * Converts a Car object into a CarViewObject.
     *
     * @param car Car to be converted
     * @return converted CarViewObject
     */
    public static CarViewObject carViewObjectFromCar (@NotNull Car car){
        CarViewObject carViewObject = new CarViewObject();
        carViewObject.setFullName(car.getMake() + " " + car.getModel() + " " + car.getCarType());
        carViewObject.setFuelUsage(car.getFuelUsage());
        carViewObject.setSeats(car.getSeats());
        carViewObject.setGearbox(car.getGearbox());
        return carViewObject;
    }
}
