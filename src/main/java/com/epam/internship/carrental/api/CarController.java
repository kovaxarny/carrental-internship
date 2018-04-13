package com.epam.internship.carrental.api;

import com.epam.internship.carrental.exceptions.CarAlreadyExistsException;
import com.epam.internship.carrental.exceptions.CarNotFoundException;
import com.epam.internship.carrental.service.car.CarServiceImpl;
import com.epam.internship.carrental.service.car.CarVO;
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
@RequestMapping(path = "/api/v1/car")
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
    @GetMapping(path = "/cars/pages")
    public @ResponseBody
    ResponseEntity<Page<CarVO>> getAllCars(@PageableDefault final Pageable pageable) {
        return new ResponseEntity<>(carService.getAllCars(pageable), HttpStatus.OK);
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
    @GetMapping(path = "/cars/make")
    public @ResponseBody
    ResponseEntity<Iterable<CarVO>> getCarsByMake(@RequestParam final String make) {
        return new ResponseEntity<>(carService.getCarsByMake(make), HttpStatus.OK);
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
    ResponseEntity<Page<CarVO>> getAllFreeCars(@PageableDefault final Pageable pageable) {
        return new ResponseEntity<>(carService.getAllFreeCars(pageable), HttpStatus.OK);
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
     * @param carVO         insertable Car object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity which contains the inserted Car
     * and Response Code 200 on success, or Response Code 403 if the token doesn't match.
     */
    @ApiOperation(value = "", tags = "New Car")
    @PutMapping(path = "/new", consumes = "application/json")
    public @ResponseBody
    ResponseEntity insertNewCarWithAuthorization(@RequestBody final CarVO carVO,
                                                 @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            try {
                carService.insertNewCar(carVO);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (CarAlreadyExistsException e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        }
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
    @GetMapping(path = "/cars")
    public @ResponseBody
    ResponseEntity<Page<CarVO>> getAllCarsWithAuthorization(@PageableDefault final Pageable pageable,
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
    ResponseEntity<CarVO> getCarByIdWithAuthorization(@PathVariable final Long carId,
                                                      @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            try {
                return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
            } catch (CarNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
    @PostMapping(path = "/update/{carId}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<CarVO> updateCarByGivenParametersWithAuthorization(@PathVariable final Long carId,
                                                                      @RequestBody final CarVO newCarParams,
                                                                      @RequestHeader("Authorization") final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            try {
                return new ResponseEntity<>(carService.updateCarWithParameters(carId, newCarParams), HttpStatus.OK);
            } catch (CarNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
    }
}
