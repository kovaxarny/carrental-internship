package com.epam.internship.carrental.rentedcar;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for managing the repository, which contains the rented car records.
 */
public interface RentedCarRepository extends JpaRepository<RentedCar, Long> {
}
