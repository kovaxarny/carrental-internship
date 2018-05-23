package com.epam.internship.carrental.service.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ReservationVO {
    /**
     * The reserved car's ID.
     */
    private Long carId;

    /**
     * The date when the reservation of the car begins.
     */
    private Date startOfReservation;

    /**
     * The date when the reservation ends.
     */
    private Date endOfReservation;
}
