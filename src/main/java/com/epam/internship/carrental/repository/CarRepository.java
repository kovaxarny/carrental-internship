package com.epam.internship.carrental.repository;

import com.epam.internship.carrental.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
