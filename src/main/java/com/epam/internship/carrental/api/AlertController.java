package com.epam.internship.carrental.api;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.alert.AlertServiceImpl;
import com.epam.internship.carrental.service.search.Search;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AlertController providing REST API endpoints for subscriptions.
 */
@Controller
@RequestMapping(path = "/api/v1/alert")
public class AlertController {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final AlertServiceImpl alertService;

    /**
     * Autowired constructor for the class.
     *
     * @param alertService service which provides access to the service layer.
     */
    @Autowired
    public AlertController(final AlertServiceImpl alertService) {
        this.alertService = alertService;
    }

    /**
     * The endpoint allows the user to subscribe to search criteria.
     *
     * <pre>
     * Method POST
     * URL /api/v1/alert/subscribe
     * </pre>
     * Sample call /api/v1/alert/subscribe
     *
     * @param search Search object representing the searched values, and the user who searched
     * @return ResponseEntity with Response Code 200 on success, or 400 if something goes wrong
     */
    @PostMapping(path = "/subscribe", consumes = "application/json")
    public @ResponseBody
    ResponseEntity subscribeToSearch(@RequestBody final Search search) {
        try {
            alertService.subscribeUser(search);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}

