package com.epam.internship.carrental.service.car;

import com.epam.internship.carrental.service.car.enums.CarType;

import javax.validation.constraints.NotNull;

/**
 * Converter utility class, which enables conversion between a Car and a CarVO.
 */
public final class CarConverter {

    /**
     * Private constructor to prevent instantiation.
     */
    private CarConverter() {
    }

    /**
     * The method converts the CarVO given in the parameter into a Car.
     *
     * @param carVO CarVO to be converted
     * @return converted Car object
     */
    public static Car carFromCarViewObject(@NotNull final CarVO carVO) {
        String[] splitCarViewObjectName = carVO.getFullName().split("\\s+");
        return Car.builder()
                .make(splitCarViewObjectName[0])
                .model(splitCarViewObjectName[1])
                .carType(CarType.valueOf(splitCarViewObjectName[2]))
                .seats(carVO.getSeats())
                .fuelUsage(carVO.getFuelUsage())
                .gearbox(carVO.getGearbox())
                .build();
    }

    /**
     * The method converts a Car object given in the parameter into a CarVO.
     *
     * @param car Car to be converted
     * @return converted CarVO
     */
    public static CarVO carViewObjectFromCar(@NotNull final Car car) {
        return CarVO.builder()
                .fullName(car.getMake() + " " + car.getModel() + " " + car.getCarType())
                .seats(car.getSeats())
                .fuelUsage(car.getFuelUsage())
                .gearbox(car.getGearbox())
                .build();
    }
}
