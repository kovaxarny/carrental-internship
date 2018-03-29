package com.epam.internship.carrental.rentedcar;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * RentedCarController provides a REST Api endpoint for managing the RentedCar repository.
 */
@Api(tags="Rented Cars")
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
}
