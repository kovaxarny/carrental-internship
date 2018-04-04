package com.epam.internship.carrental.rentedcar;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * RentedCarController provides a REST Api endpoint for managing the RentedCar repository.
 */
@Api(tags = "Rental Controls")
@Controller
@RequestMapping(path = "/api/v2")
public class RentedCarController {

    /**
     * Stores and instance of a RentedCarService.
     */
    private RentedCarServiceImpl rentedCarService;

    /**
     * Autowired constructor for the class.
     *
     * @param rentedCarService service which provides access to the service layer.
     */
    @Autowired
    public RentedCarController(final RentedCarServiceImpl rentedCarService) {
        this.rentedCarService = rentedCarService;
    }

    /**
     * Provides endpoint for the authorized users to insert a new carrental record into the database,
     * defined in the request body.
     * <pre>
     *     Method PUT
     *     URL /api/v2/hire
     * </pre>
     * Sample call /api/v2/hire
     *
     * @param rentedCar     RentedCar object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    @PutMapping(path = "/hire", consumes = "application/json")
    public @ResponseBody
    ResponseEntity bookCarRentalWithAuthorization(@RequestBody final RentedCar rentedCar,
                                                  @RequestHeader("Authorization") final String authorization) {
        return rentedCarService.bookCarRentalWithAuthorization(rentedCar, authorization);
    }

    /**
     * Provides endpoint for removal of a record from the database, defined in the path of the request.
     * <pre>
     *     Method POST
     *     URL /api/v2/endrental/{id}
     * </pre>
     * Sample call /api/v2/endrental/1
     *
     * @param id            id of the record
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    @PostMapping(path = "/endrental/{id}")
    public @ResponseBody
    ResponseEntity endCarRentalWithAuthorization(@PathVariable final Long id,
                                                 @RequestHeader("Authorization") final String authorization) {
        return rentedCarService.endCarRentalWithAuthorization(id, authorization);
    }

    /**
     * Endpoint for modifying a rent's parameters defined in the request body with authorization.
     * <pre>
     *     Method POST
     *     URL /api/v1/modify/{id}
     * </pre>
     * Sample call /api/v2/modify/1
     *
     * @param id updateable record's id
     * @param updatedRentedCarParams updatable parameters
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized or the id doesn't exist
     */
    @PostMapping(path = "/modify/{id}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity modifyCarRentalWithAuthorization(@PathVariable final Long id,
                                                    @RequestBody final RentedCar updatedRentedCarParams,
                                                    @RequestHeader("Authorization") final String authorization){
        return rentedCarService.modifyCarRentalWithAuthorization(id,updatedRentedCarParams,authorization);
    }

    /**
     * Endpoint for listing all rented Car, with authorization.
     * <pre>
     *     Method GET
     *     URL /api/v2/rentedcar/all
     * </pre>
     * Sample call /api/v2/rentedcar/all?page=0
     * @param pageable standard paging parameters in the request
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity containing Page of RentedCars with Response Code 200 on success, or 403 if unauthorized
     */
    @GetMapping(path = "/rentedcar/all")
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
    public @ResponseBody
    ResponseEntity<Page<RentedCar>> listAllCarRentalWithAuthorization(@PageableDefault final Pageable pageable,
                                                                      @RequestHeader("Authorization") final String authorization){
        return rentedCarService.listAllCarRentalWithAuthorization(pageable,authorization);
    }


}
