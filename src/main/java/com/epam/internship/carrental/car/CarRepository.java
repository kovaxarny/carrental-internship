package com.epam.internship.carrental.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    @Query(value = "SELECT car FROM Car AS car " +
            "WHERE car.make = :make")
    Iterable<Car> findByMake(@Param("make") String make);

    @Query(value = "SELECT car FROM Car AS car " +
            "LEFT JOIN RentedCar AS rented ON car.id=rented.carId WHERE rented.carId IS NULL")
    Page<Car> findAllFree(Pageable pageable);
}
