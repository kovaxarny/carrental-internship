package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
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
public class CarController {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "token";

    @Autowired
    private CarRepository carRepository;

    private CarServiceImpl carService;

    public CarController() {
    }

    @Autowired
    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<?> addNewCar(@RequestParam String make, @RequestParam String model, @RequestParam CarType carType,
                     @RequestParam int seats, @RequestParam double fuelUsage, @RequestParam CarGearbox carGearbox) {
        return carService.addNewCar(make,model,carType,seats,fuelUsage,carGearbox);
    }

    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> addNewCar(@RequestBody Car car) {
        return carService.addNewCar(car);
    }

    @GetMapping(path = "/search")
    public @ResponseBody
    ResponseEntity<?> getCarsByMake(@RequestParam String make) {
        return carService.getCarsByMake(make);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<?> getAllCars() {
        return carService.getAllCars();
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

    @GetMapping(path = "/free")
    public @ResponseBody
    ResponseEntity<?> getAllFreeCars(@PageableDefault Pageable pageable){
        return new ResponseEntity<>(carRepository.findAllFree(pageable),HttpStatus.OK);
    }
}
