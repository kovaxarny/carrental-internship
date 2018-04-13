package com.epam.internship.carrental.service.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for managing the repository, which contains the rented car records.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
