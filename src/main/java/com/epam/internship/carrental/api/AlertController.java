package com.epam.internship.carrental.api;

import com.epam.internship.carrental.service.alert.AlertServiceImpl;
import com.epam.internship.carrental.service.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/v1/alert")
public class AlertController {

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

    @PostMapping(path = "/subscribe", consumes = "application/json")
    public @ResponseBody
    ResponseEntity subscribeToSearch(@RequestBody final Search search) {
        try {
            alertService.subscribeUser(search);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

}

