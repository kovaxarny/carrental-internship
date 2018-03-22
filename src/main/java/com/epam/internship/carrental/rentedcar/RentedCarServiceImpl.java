package com.epam.internship.carrental.rentedcar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("rentedCarService")
public class RentedCarServiceImpl implements RentedCarService{

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "employee_token";

    private final RentedCarRepository rentedCarRepository;

    @Autowired
    public RentedCarServiceImpl(RentedCarRepository rentedCarRepository) {
        this.rentedCarRepository = rentedCarRepository;
    }

    @Override
    public ResponseEntity bookCarRentalWithAuthorization(RentedCar rentedCar, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        rentedCarRepository.save(rentedCar);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity endCarRentalWithAuthorization(Long id, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<RentedCar> optionalTakenCar = rentedCarRepository.findById(id);
        if (!optionalTakenCar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            rentedCarRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
