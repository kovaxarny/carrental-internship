package com.epam.internship.carrental.rentedcar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RentedCarRepository extends JpaRepository<RentedCar, Long> {
}
