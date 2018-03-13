package com.epam.internship.carrental.repository;

import com.epam.internship.carrental.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends PagingAndSortingRepository<Car,Long> {

    @Query(value = "SELECT car FROM Car AS car " +
            "WHERE car.make = :make")
    public Iterable<Car> findByMake(@Param("make") String make);
}
