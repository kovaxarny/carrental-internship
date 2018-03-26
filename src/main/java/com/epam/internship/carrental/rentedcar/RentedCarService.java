package com.epam.internship.carrental.rentedcar;

import org.springframework.http.ResponseEntity;

/**
 * Defines methods for accessing the rented car repository.
 */
public interface RentedCarService {
    /**
     * Inserts a new rentedCar object into the RentedCar database.
     * @param rentedCar the insertable rentedCar object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    ResponseEntity bookCarRentalWithAuthorization(RentedCar rentedCar, String authorization);

    /**
     * Deletes a record form the RentedCar database.
     * @param id id of the removable record
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized or the id doesn't exist
     */
    ResponseEntity endCarRentalWithAuthorization(Long id, String authorization);
}
