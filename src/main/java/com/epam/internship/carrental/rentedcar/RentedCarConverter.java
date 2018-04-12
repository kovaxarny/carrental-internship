package com.epam.internship.carrental.rentedcar;

import javax.validation.constraints.NotNull;

public final class RentedCarConverter {

    /**
     * Private constructor to prevent instantiation.
     */
    private RentedCarConverter(){

    }

    /**
     * The method converts the RentedCarViewObject given in the parameter into a RentedCar.
     *
     * @param rentedCarViewObject RentedCarViewObject to be converted
     * @return converted RentedCar
     */
    public static RentedCar rentedCarFromRentedCarViewObject(@NotNull final RentedCarViewObject rentedCarViewObject){
        return RentedCar.builder()
                .carId(rentedCarViewObject.getCarId())
                .startOfRental(rentedCarViewObject.getStartOfRental())
                .endOfRental(rentedCarViewObject.getEndOfRental())
                .build();
    }

    /**
     * The method converts the RentedCar given in the parameter into a RentedCarViewObject.
     *
     * @param rentedCar RentedCar to be converted
     * @return converted RentedCarViewObject
     */
    public static RentedCarViewObject rentedCarViewObjectFromRentedCar(@NotNull final RentedCar rentedCar){
        return RentedCarViewObject.builder()
                .carId(rentedCar.getCarId())
                .startOfRental(rentedCar.getStartOfRental())
                .endOfRental(rentedCar.getEndOfRental())
                .build();
    }
}
