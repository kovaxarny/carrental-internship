package com.epam.internship.carrental.controller;

import com.epam.internship.carrental.enums.CarType;
import com.epam.internship.carrental.enums.Gearbox;
import com.epam.internship.carrental.model.Car;
import com.epam.internship.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1")
public class MainController {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "token";

    @Autowired
    private CarRepository carRepository;

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewCar(@RequestParam String make, @RequestParam String model, @RequestParam CarType carType,
                     @RequestParam int seats, @RequestParam double fuelUsage, @RequestParam Gearbox gearbox) {
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setCarType(carType);
        car.setSeats(seats);
        car.setFuelUsage(fuelUsage);
        car.setGearbox(gearbox);
        carRepository.save(car);
        return "Saved";
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody
    String addNewCar(@RequestBody Car car) {
        carRepository.save(car);
        return "Saved";
    }

    @GetMapping(path = "/search")
    public @ResponseBody
    Iterable<Car> getCarsByMake(@RequestParam String make) {
        return carRepository.findByMake(make);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping(path = "/all/pages")
    public @ResponseBody
    List<Car> getAllCars(@PageableDefault Pageable pageable) {
        Page<Car> page = carRepository.findAll(pageable);
        return page.getContent();
    }

    @PostMapping(path = "/echo", consumes = "application/json")
    public @ResponseBody
    Car echoCar(@RequestBody Car car) {
        return car;
    }

    @GetMapping(path = "/car")
    public @ResponseBody
    ResponseEntity<?> getAllCarsWithAuthorization(@PageableDefault Pageable pageable, @RequestHeader("Authorization") String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        Page<Car> page = carRepository.findAll(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "/car/{carId}")
    public @ResponseBody
    ResponseEntity<?> getCarByIdWithAuthorization(@PathVariable Long carId, @RequestHeader("Authorization") String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findById(carId), HttpStatus.OK);
    }

    @PutMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> insertNewCarWithAuthorization(@RequestBody Car car, @RequestHeader("Authorization") String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
    }

    @PostMapping(path = "/car/{carId}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> updateCarByGivenParametersWithAuthorization(@PathVariable Long carId, @RequestBody Car newCarParams, @RequestHeader("Authorization") String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            return new ResponseEntity<>("No car with given Id", HttpStatus.FORBIDDEN);
        } else {
            Car carToUpdate = optionalCar.get();
            Car updatedCar = Car.updateCar(carToUpdate, newCarParams);
            return new ResponseEntity<>(carRepository.save(updatedCar), HttpStatus.OK);
        }
    }
}
