package com.epam.internship.carrental.rentedcar;

/**
 * Utility class for RentedCar modifications.
 */
public class RentedCarUtilities {
    /**
     * Private constructor to prevent instantiation.
     */
    private RentedCarUtilities(){}

    /**
     * Modifies the RentedCar in the first parameter, with the parameters of the second one.
     *
     * @param modifiableRentedCar modifiable RentedCar
     * @param paramsRentedCar new RentedCar parameters
     * @return modified Rented Car
     */
    public static RentedCar modifyRentedCar(RentedCar modifiableRentedCar, RentedCar paramsRentedCar){
        if (!paramsRentedCar.getCarId().equals(null)){
            modifiableRentedCar.setCarId(paramsRentedCar.getCarId());
        }
        if (!paramsRentedCar.getEndOfRental().equals(null)){
            modifiableRentedCar.setEndOfRental(paramsRentedCar.getEndOfRental());
        }
        if (!paramsRentedCar.getStartOfRental().equals(null)){
            modifiableRentedCar.setStartOfRental(paramsRentedCar.getStartOfRental());
        }
        return modifiableRentedCar;
    }
}
