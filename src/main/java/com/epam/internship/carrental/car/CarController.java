package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1")
public class CarController {

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
    ResponseEntity<?> getAllCars(@PageableDefault Pageable pageable) {
        return carService.getAllCars(pageable);
    }

    @PostMapping(path = "/echo", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Car> echoCar(@RequestBody Car car) {
        return carService.echoCar(car);
    }

    @GetMapping(path = "/car")
    public @ResponseBody
    ResponseEntity<?> getAllCarsWithAuthorization(@PageableDefault Pageable pageable, @RequestHeader("Authorization") String authorization) {
        return carService.getAllCarsWithAuthorization(pageable,authorization);
    }

    @GetMapping(path = "/car/{carId}")
    public @ResponseBody
    ResponseEntity<?> getCarByIdWithAuthorization(@PathVariable Long carId, @RequestHeader("Authorization") String authorization) {
        return carService.getCarByIdWithAuthorization(carId,authorization);
    }

    @PutMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> insertNewCarWithAuthorization(@RequestBody Car car, @RequestHeader("Authorization") String authorization) {
        return carService.insertNewCarWithAuthorization(car,authorization);
    }

    @PostMapping(path = "/car/{carId}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> updateCarByGivenParametersWithAuthorization(@PathVariable Long carId, @RequestBody Car newCarParams, @RequestHeader("Authorization") String authorization) {
        return carService.updateCarByGivenParametersWithAuthorization(carId,newCarParams,authorization);
    }

    @GetMapping(path = "/free")
    public @ResponseBody
    ResponseEntity<?> getAllFreeCars(@PageableDefault Pageable pageable){
        return carService.getAllFreeCars(pageable);
    }
}
