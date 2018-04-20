package com.epam.internship.carrental.service.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for managing the repository, which contains the rented car records.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
