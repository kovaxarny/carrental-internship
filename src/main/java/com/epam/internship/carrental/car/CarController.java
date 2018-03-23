package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * CarController is providing a REST Api endpoint.
 */
@Controller
@RequestMapping(path = "/api/v1")
public class CarController {

    /**
     * This field stores the instance of a CarService.
     */
    private CarServiceImpl carService;

    /**
     * Empty constructor for the class.
     */
    public CarController() {
    }

    /**
     * Autowired constructor fo the class.
     * @param carService The service which provides access to the service layer.
     */
    @Autowired
    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    /**
     * Method GET
     * URL /api/v1/add
     * Adds a new Car to the database, where the Car is defined in the request parameters.
     *
     * Sample call: /api/v1/add?make=Dacia&model=1310&carType=Sedan&seats=5&fuelUsage=12.7&carGearbox=Manual
     *
     * @param make The maker of the car to be added.
     * @param model The model of the car to be added.
     * @param carType The carType of the car to be added.
     * @param seats The number of seats in the car to be added.
     * @param fuelUsage The fuel usage in liter/100km of the car to be added.
     * @param carGearbox The gearbox of the car to be added.
     * @return A ResponseEntity with Response Code 200 on success.
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<?> addNewCar(@RequestParam String make, @RequestParam String model, @RequestParam CarType carType,
                     @RequestParam int seats, @RequestParam double fuelUsage, @RequestParam CarGearbox carGearbox) {
        return carService.addNewCar(make,model,carType,seats,fuelUsage,carGearbox);
    }

    /**
     * Method POST
     * URL /api/v1/add
     * Adds a new Car to the database. The car is defined in the body of the request in JSON format.
     *
     * Sample call /api/v1/add
     *
     * @param car The car to be added
     * @return A ResponseEntity with Response Code 200 on success.
     */
    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> addNewCar(@RequestBody Car car) {
        return carService.addNewCar(car);
    }

    /**
     * Method GET
     * URL /api/v1/search
     * Used to get all cars by a given maker.
     *
     * Sample call /api/v1/add?make=Dacia
     *
     * @param make The maker of cars to be retrieved
     * @return A ResponseEntity which contains an Iterable list of cars made by the maker,
     *          and Response Code 200 on success.
     */
    @GetMapping(path = "/search")
    public @ResponseBody
    ResponseEntity<?> getCarsByMake(@RequestParam String make) {
        return carService.getCarsByMake(make);
    }

    /**
     * Method GET
     * URL /api/v1/all
     * Gets all car from the database.
     *
     * Sample call /api/v1/all
     * @return A ResponseEntity which contains an Iterable list of all cars,
     *          and Response Code 200 on success.
     */
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


    @GetMapping(path = "/car/allCarVo")
    public @ResponseBody
    ResponseEntity<?> getAllCarViewObject(@PageableDefault Pageable pageable){
        return carService.getAllCarViewObject(pageable);
    }

    @PutMapping(path = "/car/carVO", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> insertNewCarFromViewObject(@RequestBody CarViewObject carViewObject) {
        return carService.insertNewCarFromViewObject(carViewObject);
    }
}
