package com.epam.internship.carrental.rentedcar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v2")
public class RentedCarController {

    private RentedCarServiceImpl rentedCarService;

    public RentedCarController() {
    }

    @Autowired
    public RentedCarController(RentedCarServiceImpl rentedCarService) {
        this.rentedCarService = rentedCarService;
    }

    @PutMapping(path = "/hire", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<?> bookCarRentalWithAuthorization(@RequestBody RentedCar rentedCar, @RequestHeader("Authorization") String authorization){
        return rentedCarService.bookCarRentalWithAuthorization(rentedCar,authorization);
    }

    @PostMapping(path = "/endrental/{id}")
    public @ResponseBody
    ResponseEntity<?> endCarRentalWithAuthorization(@PathVariable Long id,@RequestHeader("Authorization") String authorization){
        return rentedCarService.endCarRentalWithAuthorization(id,authorization);
    }
}
