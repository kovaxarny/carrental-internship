package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * CarController providing a REST Api endpoint.
 */
@Controller
@RequestMapping(path = "/api/v1")
public class CarController {

    /**
     * This field stores the instance of a CarService.
     */
    private final CarServiceImpl carService;

    /**
     * Autowired constructor for the class.
     *
     * @param carService service which provides access to the service layer.
     */
    @Autowired
    public CarController(final CarServiceImpl carService) {
        this.carService = carService;
    }

    /**
     * Adds a new Car to the database, where the Car is defined in the request parameters.
     * <pre>
     * Method GET
     * URL /api/v1/add
     * </pre>
     * Sample call: /api/v1/add?make=Dacia&model=1310&carType=Sedan&seats=5&fuelUsage=12.7&carGearbox=Manual
     *
     * @param make       maker of the car to be added.
     * @param model      model of the car to be added.
     * @param carType    carType of the car to be added.
     * @param seats      number of seats in the car to be added.
     * @param fuelUsage  fuel usage in liter/100km of the car to be added.
     * @param carGearbox gearbox of the car to be added.
     * @return ResponseEntity with Response Code 200 on success.
     */
    @GetMapping(path = "/add")
    public @ResponseBody
    ResponseEntity addNewCar(@RequestParam final String make,
                             @RequestParam final String model,
                             @RequestParam final CarType carType,
                             @RequestParam final int seats,
                             @RequestParam final double fuelUsage,
                             @RequestParam final CarGearbox carGearbox) {
        return carService.addNewCar(make, model, carType,
                seats, fuelUsage, carGearbox);
    }

    /**
     * Adds a new Car to the database. The car is defined in the body of the request in JSON format.
     * <pre>
     * Method POST
     * URL /api/v1/add
     * </pre>
     * Sample call /api/v1/add
     *
     * @param car car to be added
     * @return ResponseEntity with Response Code 200 on success.
     */
    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody
    ResponseEntity addNewCar(@RequestBody final Car car) {
        return carService.addNewCar(car);
    }

    /**
     * Used to get all cars by a given maker.
     * <pre>
     * Method GET
     * URL /api/v1/search
     * </pre>
     * Sample call /api/v1/add?make=Dacia
     *
     * @param make maker of cars to be retrieved
     * @return ResponseEntity which contains an Iterable list of cars made by the maker,
     * and Response Code 200 on success.
     */
    @GetMapping(path = "/search")
    public @ResponseBody
    ResponseEntity<Iterable<Car>> getCarsByMake(@RequestParam final String make) {
        return carService.getCarsByMake(make);
    }

    /**
     * Gets all car from the database.
     * <pre>
     * Method GET
     * URL /api/v1/all
     * </pre>
     * Sample call /api/v1/all
     *
     * @return ResponseEntity which contains an Iterable list of all cars,
     * and Response Code 200 on success.
     */
    @GetMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<Iterable<Car>> getAllCars() {
        return carService.getAllCars();
    }

    /**
     * The method returns all car from the database in a pageable format.
     * <pre>
     * Method GET
     * URL /api/v1/all/pages
     * </pre>
     * Sample call /api/v1/all/pages?page=0
     *
     * @param pageable standard paging parameters in the request
     * @return ResponseEntity which contains an Pageable list of all cars,
     * and Response Code 200 on success.
     */
    @GetMapping(path = "/all/pages")
    public @ResponseBody
    ResponseEntity<Page<Car>> getAllCars(@PageableDefault final Pageable pageable) {
        return carService.getAllCars(pageable);
    }

    /**
     * A simple method which echoes back the car object given in the body of the request as a JSON Object.
     * <pre>
     * Method POST
     * URL /api/v1/echo
     * </pre>
     * Sample call /api/v1/echo
     *
     * @param car car JSON Object to be returned
     * @return ResponseEntity which contains a Car and Response Code 200 on success.
     */
    @PostMapping(path = "/echo", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Car> echoCar(@RequestBody final Car car) {
        return carService.echoCar(car);
    }

    /**
     * The method returns the list of all cars from the database in a pageable format,
     * to those who already have the authorization token.
     * <pre>
     *     Method GET
     *     URL /api/v1/car
     * </pre>
     * Sample call /api/v1/car
     *
     * @param pageable      standard paging parameters in the request
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains an Pageable list of all cars
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match.
     */
    @GetMapping(path = "/car")
    public @ResponseBody
    ResponseEntity<Page<Car>> getAllCarsWithAuthorization(@PageableDefault final Pageable pageable,
                                                          @RequestHeader("Authorization") final String authorization) {
        return carService.getAllCarsWithAuthorization(pageable, authorization);
    }

    /**
     * The method returns for a single car defined by an ID in the parameter of the request if the request sender
     * owns the authorization token.
     * <pre>
     *     Method GET
     *     URL /api/v1/car/{carId}
     * </pre>
     * Sample call /api/v1/car/1
     *
     * @param carId         id of the searched car
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains a single Car
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match.
     */
    @GetMapping(path = "/car/{carId}")
    public @ResponseBody
    ResponseEntity<Optional<Car>> getCarByIdWithAuthorization(@PathVariable final Long carId,
                                                              @RequestHeader("Authorization") final String authorization) {
        return carService.getCarByIdWithAuthorization(carId, authorization);
    }

    /**
     * The method is used for inserting a new Car object into the database, allowed for users with authorization.
     * The car is defined in the body of the request.
     * <pre>
     *     Method PUT
     *     URL /api/v1/car
     * </pre>
     * Sample call /api/v1/car
     *
     * @param car           insertable Car object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains the inserted Car
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match.
     */
    @PutMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Car> insertNewCarWithAuthorization(@RequestBody final Car car,
                                                      @RequestHeader("Authorization") final String authorization) {
        return carService.insertNewCarWithAuthorization(car, authorization);
    }

    /**
     * Updates a single Car object in the database, defined by the ID in the request parameter with the parameters from
     * the request body.
     * <pre>
     *     Method POST
     *     URL /api/v1/car/{carId}
     * </pre>
     * Sample call /api/v1/car/1
     *
     * @param carId         updateable car's ID
     * @param newCarParams  updateable parameters
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains the updated Car
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match,
     * or if there is no car with given ID.
     */
    @PostMapping(path = "/updatecar/{carId}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Car> updateCarByGivenParametersWithAuthorization(@PathVariable final Long carId,
                                                                    @RequestBody final Car newCarParams,
                                                                    @RequestHeader("Authorization") final String authorization) {
        return carService.updateCarByGivenParametersWithAuthorization(carId, newCarParams, authorization);
    }

    /**
     * Returns all available cars, in a pageable format.
     * <pre>
     *     Method GET
     *     URL /api/v1/free
     * </pre>
     * Sample call /api/v1/free?page=0
     *
     * @param pageable standard paging parameters in the request
     * @return ResponseEntity which contains an Pageable list of all free cars
     * and Response Code 200 on success.
     */
    @GetMapping(path = "/free")
    public @ResponseBody
    ResponseEntity<Page<Car>> getAllFreeCars(@PageableDefault final Pageable pageable) {
        return carService.getAllFreeCars(pageable);
    }

    /**
     * Returns all cars from the database in a predefined ViewObject format.
     * <pre>
     *     Method GET
     *     URL /api/v1/car/allCarVo
     * </pre>
     * Sample call /api/v1/car/allCarVo
     *
     * @param pageable standard paging parameters in the request
     * @return ResponseEntity which contains an Pageable list of all cars in a viewObject format
     * and Response Code 200 on success.
     */
    @GetMapping(path = "/car/allCarVo")
    public @ResponseBody
    ResponseEntity<Page<CarViewObject>> getAllCarViewObject(@PageableDefault final Pageable pageable) {
        return carService.getAllCarViewObject(pageable);
    }

    /**
     * The method is used for inserting a new Car object into the database, defined in the body of
     * the request in a carViewObject format.
     * <pre>
     *     Method PUT
     *     URL /api/v1/car/carVO
     * </pre>
     * Sample call /api/v1/car/carVO
     *
     * @param carViewObject insertable car in ViewObject format
     * @return ResponseEntity with Response Code 200 on success.
     */
    @PutMapping(path = "/car/carVO", consumes = "application/json")
    public @ResponseBody
    ResponseEntity insertNewCarFromViewObject(@RequestBody final CarViewObject carViewObject) {
        return carService.insertNewCarFromViewObject(carViewObject);
    }
}
