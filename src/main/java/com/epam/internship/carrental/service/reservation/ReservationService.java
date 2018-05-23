package com.epam.internship.carrental.service.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines methods for accessing the reservation repository.
 */
public interface ReservationService {
    /**
     * Inserts a new reservation object into the Reservation database.
     *
     * @param reservationVO     the insertable reservationVO
     */
    void bookCarReservation(ReservationVO reservationVO);

    /**
     * Deletes a record form the Reservation database.
     *
     * @param id            id of the removable record
     */
    void endCarReservation(Long id);

    /**
     * Modifies a record in the Reservation database.
     *
     * @param id modifiable reservations id
     */
    ReservationVO modifyCarReservation(Long id, ReservationVO reservationVO);






    /**
     * Lists all record from the Reservation database in a pageable format.
     *
     * @param pageable standard pageable parameters
     * @return Page of Reservations
     */
    Page<ReservationVO> listAllReservation(Pageable pageable);
}
