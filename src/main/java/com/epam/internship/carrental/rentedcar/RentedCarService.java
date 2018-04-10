package com.epam.internship.carrental.rentedcar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines methods for accessing the rented car repository.
 */
public interface RentedCarService {
    /**
     * Inserts a new rentedCar object into the RentedCar database.
     *
     * @param rentedCar     the insertable rentedCar object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    void bookCarRentalWithAuthorization(RentedCar rentedCar, String authorization);

    /**
     * Deletes a record form the RentedCar database.
     *
     * @param id            id of the removable record
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized or the id doesn't exist
     */
    void endCarRentalWithAuthorization(Long id, String authorization);

    /**
     * Modifies a record in the RentedCar database.
     *
     * @param id modifiable rented car records is
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized or the id doesn't exist
     */
    void modifyCarRentalWithAuthorization(Long id,RentedCar rentedCar, String authorization);

    /**
     * Lists all record from the RentedCar database in a pageable format.
     * @param pageable standard pageable parameters
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity containing Page of RentedCars with Response Code 200 on success, or 403 if unauthorized
     */
    Page<RentedCar> listAllCarRentalWithAuthorization(Pageable pageable, String authorization);
}
