package com.epam.internship.carrental.api;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.reservation.ReservationServiceImpl;
import com.epam.internship.carrental.service.reservation.ReservationVO;
import com.epam.internship.carrental.service.reservation.exception.ReservationNotFoundException;
import com.epam.internship.carrental.service.reservation.exception.ReservationOperationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ReservationController provides a REST API endpoint for managing reservations.
 */
@Api(tags = "Reservation Controls")
@Controller
@RequestMapping(path = "/api/v1/reservation")
public class ReservationController {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);


    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "employee_token";

    /**
     * Stores and instance of a ReservationService.
     */
    private ReservationServiceImpl reservationService;

    /**
     * Autowired constructor for the class.
     *
     * @param reservationService service which provides access to the service layer.
     */
    @Autowired
    public ReservationController(final ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * Provides endpoint for reserving a Car.
     * Available to authorized users only
     * <pre>
     *     Method PUT
     *     URL /api/v1/reservation/reserve
     * </pre>
     * Sample call /api/v1/reservation/reserve
     *
     * @param reservationVO ReservationVO object
     * @return ResponseEntity with Response Code 200 on success
     */
    @PutMapping(path = "/reserve", consumes = "application/json")
    public @ResponseBody
    ResponseEntity bookReservation(@RequestBody final ReservationVO reservationVO) {

        HttpStatus httpStatus;
        try {
            reservationService.bookCarReservation(reservationVO);
            httpStatus = HttpStatus.OK;
        } catch (ReservationOperationException e) {
            LOGGER.error(e);
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Provides endpoint for removing of a record from the database, defined in the path of the request.
     * Available to authorized users only
     * <pre>
     *     Method POST
     *     URL /api/v1/reservation/{id}
     * </pre>
     * Sample call /api/v1/reservation/1
     *
     * @param id            id of the record
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    @PostMapping(path = "/end/{id}")
    public @ResponseBody
    ResponseEntity endReservationWithAuthorization(@PathVariable final Long id,
                                                   @RequestHeader("Authorization") final String authorization) {
        HttpStatus httpStatus;
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        try {
            reservationService.endCarReservation(id);
            httpStatus = HttpStatus.OK;
        } catch (ReservationNotFoundException | ReservationOperationException e) {
            LOGGER.error(e);
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity(httpStatus);
    }

    /**
     * Provides endpoint for modifying a reservations parameters defined in the request body.
     * Available to authorized users only
     * <pre>
     *     Method POST
     *     URL /api/v1/reservation/modify/{id}
     * </pre>
     * Sample call /api/v1/reservation/modify/1
     *
     * @param id            updateable record's id
     * @param reservationVO updatable parameters
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized or the id doesn't exist
     */
    @PostMapping(path = "/modify/{id}", consumes = "application/json")
    public @ResponseBody
    ResponseEntity modifyReservationWithAuthorization(@PathVariable final Long id,
                                                      @RequestBody final ReservationVO reservationVO,
                                                      @RequestHeader("Authorization") final String authorization) {
        HttpStatus httpStatus;
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        try {
            reservationService.modifyCarReservation(id, reservationVO);
            httpStatus = HttpStatus.OK;
        } catch (ReservationNotFoundException | ReservationOperationException e) {
            LOGGER.error(e);
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity(httpStatus);
    }

    /**
     * Provides endpoint for listing all Reservations.
     * Available to authorized users only
     * <pre>
     *     Method GET
     *     URL /api/v1/reservation/reservations
     * </pre>
     * Sample call /api/v1/reservation/reservations?page=0
     *
     * @param pageable      standard paging parameters in the request
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity containing Page of Reservations with Response Code 200 on success, or 403 if unauthorized
     */
    @GetMapping(path = "/reservations")
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
    ResponseEntity<Page<ReservationVO>> listAllReservationWithAuthorization(@PageableDefault final Pageable pageable,
                                                                            @RequestHeader("Authorization") final String authorization) {
        HttpStatus httpStatus;
        Page<ReservationVO> reservations = null;
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            reservations = reservationService.listAllReservation(pageable);
            httpStatus = HttpStatus.OK;
        } catch (ReservationOperationException e) {
            LOGGER.error(e);
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(reservations, httpStatus);
    }
}
