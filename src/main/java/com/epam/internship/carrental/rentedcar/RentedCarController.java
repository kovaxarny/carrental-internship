package com.epam.internship.carrental.rentedcar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v2")
public class RentedCarController {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "employee_token";

    @Autowired
    private RentedCarRepository takenCarRepository;

    @PutMapping(path = "/hire", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> bookCarRentalWithAuthorization(@RequestBody RentedCar takenCar, @RequestHeader("Authorization") String authorization){
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        takenCarRepository.save(takenCar);
        return new ResponseEntity<>("Rent successful.", HttpStatus.OK);
    }

    @PostMapping(path = "/endrental/{id}")
    public @ResponseBody
    ResponseEntity<?> endCarRentalWithAuthorization(@PathVariable Long id,@RequestHeader("Authorization") String authorization){
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        }
        Optional<RentedCar> optionalTakenCar = takenCarRepository.findById(id);
        if (!optionalTakenCar.isPresent()) {
            return new ResponseEntity<>("No rent with given Id", HttpStatus.FORBIDDEN);
        } else {
            takenCarRepository.deleteById(id);
            return new ResponseEntity<>("Car rental removed.", HttpStatus.OK);
        }

    }
}
