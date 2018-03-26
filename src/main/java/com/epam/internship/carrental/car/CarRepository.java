package com.epam.internship.carrental.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface to access the database of cars
 */
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    /**
     * The query is used to retrieve all cars made by the maker specified in the parameter.
     * @param make make of cars to be searched to
     * @return Iterable which contains the cars by the maker
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "WHERE car.make = :make")
    Iterable<Car> findByMake(@Param("make") String make);

    /**
     * Retrieves all free cars from the database.
     * @param pageable standard pageable parameter
     * @return Page of cars
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "LEFT JOIN RentedCar AS rented ON car.id=rented.carId WHERE rented.carId IS NULL")
    Page<Car> findAllFree(Pageable pageable);
}
