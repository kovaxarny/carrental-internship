package com.epam.internship.carrental.car;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * CarController providing a REST API endpoints.
 */
@Controller
@RequestMapping(path = "/api/v1")
public class CarController {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "token";

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
     * Provides endpoint for adding a new Car to the database, where the car is defined in the request body in JSON format.
     * <pre>
     * Method POST
     * URL /api/v1/add
     * </pre>
     * Sample call /api/v1/add
     *
     * @param carViewObject car to be added
     * @return ResponseEntity with Response Code 200 on success.
     */
    @ApiOperation(value = "", tags = "New Car")
    @PostMapping(path = "/add", consumes = "application/json")
    public @ResponseBody
    ResponseEntity addNewCar(@RequestBody final CarViewObject carViewObject) {
        carService.insertNewCar(carViewObject);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * The method returns all cars by a single maker, where the maker is specified in the request parameters.
     * <pre>
     * Method GET
     * URL /api/v1/search
     * </pre>
     * Sample call /api/v1/search?make=Dacia
     *
     * @param make maker of cars to be retrieved
     * @return ResponseEntity which contains an Iterable list of cars made by the maker,
     * and Response Code 200 on success.
     */
    @ApiOperation(value = "", tags = "Searching")
    @GetMapping(path = "/search")
    public @ResponseBody
    ResponseEntity<Iterable<CarViewObject>> getCarsByMake(@RequestParam final String make) {
        return new ResponseEntity<>(carService.getCarsByMake(make), HttpStatus.OK);
    }

    /**
     * Provides endpoint for retrieving all cars from the database.
     * <pre>
     * Method GET
     * URL /api/v1/all
     * </pre>
     * Sample call /api/v1/all
     *
     * @return ResponseEntity which contains an Iterable list of all cars,
     * and Response Code 200 on success.
     */

    @ApiOperation(value = "", tags = "All Car Listing")
    @GetMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<Iterable<CarViewObject>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    /**
     * Provides endpoint for retrieving all cars from the database in a pageable format.
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
    @ApiOperation(value = "", tags = "All Car Listing")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property (asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping(path = "/all/pages")
    public @ResponseBody
    ResponseEntity<Page<CarViewObject>> getAllCars(@PageableDefault final Pageable pageable) {
        return new ResponseEntity<>(carService.getAllCars(pageable), HttpStatus.OK);
    }

    /**
     * Provides endpoint for retrieving the list of all cars from the database in a pageable format.
     * Available only to authorized users
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
    @ApiOperation(value = "", tags = "All Car Listing")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property (asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping(path = "/car")
    public @ResponseBody
    ResponseEntity<Page<CarViewObject>> getAllCarsWithAuthorization(@PageableDefault final Pageable pageable,
                                                                    @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carService.getAllCars(pageable), HttpStatus.OK);
    }

    /**
     * Provides endpoint for retrieving a single car defined by an ID in the parameter of the request.
     * Available only to authorized users
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
    @ApiOperation(value = "", tags = "Searching")
    @GetMapping(path = "/car/{carId}")
    public @ResponseBody
    ResponseEntity<CarViewObject> getCarByIdWithAuthorization(@PathVariable final Long carId,
                                                              @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
        }

    }

    /**
     * Provides endpoint for inserting a new Car object into the database.
     * The car is defined in the body of the request
     * Available only to authorized users
     * <pre>
     *     Method PUT
     *     URL /api/v1/car
     * </pre>
     * Sample call /api/v1/car
     *
     * @param carViewObject insertable Car object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains the inserted Car
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match.
     */
    @ApiOperation(value = "", tags = "New Car")
    @PutMapping(path = "/car", consumes = "application/json")
    public @ResponseBody
    ResponseEntity insertNewCarWithAuthorization(@RequestBody final CarViewObject carViewObject,
                                                 @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            carService.insertNewCar(carViewObject);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Provides endpoint for updating a single Car object in the database,which is defined by the ID in the request parameter
     * with the parameters from the request body.
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
    @ApiOperation(value = "", tags = "Car Modification")
    @PostMapping(path = "/updatecar/{carId}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<CarViewObject> updateCarByGivenParametersWithAuthorization(@PathVariable final Long carId,
                                                                              @RequestBody final CarViewObject newCarParams,
                                                                              @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(carService.updateCarWithParameters(carId, newCarParams), HttpStatus.OK);
        }
    }

    /**
     * Provides endpoint for retrieving all available cars, in a pageable format.
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
    @ApiOperation(value = "", tags = "Free Car Listing")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property (asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping(path = "/free")
    public @ResponseBody
    ResponseEntity<Page<CarViewObject>> getAllFreeCars(@PageableDefault final Pageable pageable) {
        return new ResponseEntity<>(carService.getAllFreeCars(pageable), HttpStatus.OK);
    }

    /**
     * Provides endpoint for retrieving all cars from the database in a predefined ViewObject format.
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
    @ApiOperation(value = "", tags = "View Object Operations")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property (asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping(path = "/car/allCarVo")
    public @ResponseBody
    ResponseEntity<Page<CarViewObject>> getAllCarViewObject(@PageableDefault final Pageable pageable) {
        return new ResponseEntity<>(carService.getAllCars(pageable), HttpStatus.OK);
    }

    /**
     * Provides endpoint for inserting a new Car object into the database, defined in the body of
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
    @ApiOperation(value = "", tags = {"View Object Operations", "New Car"})
    @PutMapping(path = "/car/carVO", consumes = "application/json")
    public @ResponseBody
    ResponseEntity insertNewCarFromViewObject(@RequestBody final CarViewObject carViewObject) {
        carService.insertNewCar(carViewObject);
        return new ResponseEntity(HttpStatus.OK);
    }
}
