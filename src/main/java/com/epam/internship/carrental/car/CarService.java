package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.http.ResponseEntity;

public interface CarService {

    ResponseEntity addNewCar(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox carGearbox);
    ResponseEntity addNewCar(Car car);
    ResponseEntity getAllCars();
    ResponseEntity getCarsByMake(String make);

}
