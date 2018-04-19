package com.epam.internship.carrental.service.reservation;

import javax.validation.constraints.NotNull;

/**
 * Utility class for Reservation modifications.
 */
public class ReservationUtil {
    /**
     * Private constructor to prevent instantiation.
     */
    private ReservationUtil(){}

    /**
     * Modifies the Reservation in the first parameter, with the parameters of the second one.
     *
     * @param modifiableReservation modifiable Reservation
     * @param paramsReservation new Reservation parameters
     * @return modified Rented Car
     */
    public static Reservation modifyRentedCar(@NotNull Reservation modifiableReservation, @NotNull Reservation paramsReservation){
        if (!paramsReservation.getCarId().equals(null)){
            modifiableReservation.setCarId(paramsReservation.getCarId());
        }
        if (!paramsReservation.getEndOfRental().equals(null)){
            modifiableReservation.setEndOfRental(paramsReservation.getEndOfRental());
        }
        if (!paramsReservation.getStartOfRental().equals(null)){
            modifiableReservation.setStartOfRental(paramsReservation.getStartOfRental());
        }
        return modifiableReservation;
    }
}
