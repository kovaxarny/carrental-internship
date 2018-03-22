package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CarService {

    ResponseEntity addNewCar(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox carGearbox);
    ResponseEntity addNewCar(Car car);
    ResponseEntity getAllCars();
    ResponseEntity getCarsByMake(String make);
    ResponseEntity getAllCars(Pageable pageable);
    ResponseEntity echoCar(Car car);
    ResponseEntity getAllCarsWithAuthorization(Pageable pageable, String authorization);
    ResponseEntity getCarByIdWithAuthorization(Long carId, String authorization);
    ResponseEntity insertNewCarWithAuthorization(Car car, String authorization);
    ResponseEntity updateCarByGivenParametersWithAuthorization(Long carId, Car newCarParams, String authorization);
    ResponseEntity getAllFreeCars(Pageable pageable);
}
