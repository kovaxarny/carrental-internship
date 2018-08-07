package com.epam.internship.carrental.service.reservation;

import javax.validation.constraints.NotNull;

public final class ReservationToVOConverter {

    /**
     * Private constructor to prevent instantiation.
     */
    private ReservationToVOConverter(){

    }

    /**
     * The method converts the ReservationVO given in the parameter into a Reservation.
     *
     * @param reservationVO ReservationVO to be converted
     * @return converted Reservation
     */
    public static Reservation reservationFromReservationVO(@NotNull final ReservationVO reservationVO){
        return Reservation.builder()
                .carId(reservationVO.getCarId())
                .startOfReservation(reservationVO.getStartOfReservation())
                .endOfReservation(reservationVO.getEndOfReservation())
                .build();
    }

    /**
     * The method converts the Reservation given in the parameter into a ReservationVO.
     *
     * @param reservation Reservation to be converted
     * @return converted ReservationVO
     */
    public static ReservationVO reservationVOFromReservation(@NotNull final Reservation reservation){
        return ReservationVO.builder()
                .carId(reservation.getCarId())
                .startOfReservation(reservation.getStartOfReservation())
                .endOfReservation(reservation.getEndOfReservation())
                .build();
    }
}
