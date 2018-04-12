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
     * @param rentedCarViewObject     the insertable rentedCarViewObject
     */
    void bookCarRental(RentedCarViewObject rentedCarViewObject);

    /**
     * Deletes a record form the RentedCar database.
     *
     * @param id            id of the removable record
     */
    void endCarRental(Long id);

    /**
     * Modifies a record in the RentedCar database.
     *
     * @param id modifiable rented car records is
     */
    RentedCarViewObject modifyCarRental(Long id,RentedCarViewObject rentedCarViewObject);






    /**
     * Lists all record from the RentedCar database in a pageable format.
     *
     * @param pageable standard pageable parameters
     * @return Page of RentedCarsViewObjcets
     */
    Page<RentedCarViewObject> listAllCarRental(Pageable pageable);
}
