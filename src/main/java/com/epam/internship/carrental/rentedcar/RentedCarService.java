package com.epam.internship.carrental.rentedcar;

import org.springframework.http.ResponseEntity;

public interface RentedCarService {
    ResponseEntity bookCarRentalWithAuthorization(RentedCar rentedCar, String authorization);
    ResponseEntity endCarRentalWithAuthorization(Long id, String authorization);
}
