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
     * @return modified Reservation
     */
    public static Reservation modifyReservation(@NotNull Reservation modifiableReservation, @NotNull Reservation paramsReservation){
        if (paramsReservation.getCarId() != null){
            modifiableReservation.setCarId(paramsReservation.getCarId());
        }
        if (paramsReservation.getEndOfReservation() != null){
            modifiableReservation.setEndOfReservation(paramsReservation.getEndOfReservation());
        }
        if (paramsReservation.getStartOfReservation() != null){
            modifiableReservation.setStartOfReservation(paramsReservation.getStartOfReservation());
        }
        return modifiableReservation;
    }
}
