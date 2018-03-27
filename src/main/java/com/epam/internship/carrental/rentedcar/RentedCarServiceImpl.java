package com.epam.internship.carrental.rentedcar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link com.epam.internship.carrental.rentedcar.RentedCarService} interface.
 */
@Service
@Qualifier("rentedCarService")
public class RentedCarServiceImpl implements RentedCarService {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "employee_token";

    private final RentedCarRepository rentedCarRepository;

    @Autowired
    public RentedCarServiceImpl(final RentedCarRepository rentedCarRepository) {
        this.rentedCarRepository = rentedCarRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param rentedCar     the insertable rentedCar object
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success, or 403 if unauthorized
     */
    @Override
    public ResponseEntity bookCarRentalWithAuthorization(final RentedCar rentedCar,
                                                         final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        rentedCarRepository.save(rentedCar);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param id            id of the removable record
     * @param authorization authorization token from the header of the request
     * @return ResponseEntity with Response Code 200 on success,
     *          or 403 if unauthorized or the id doesn't exist
     */
    @Override
    public ResponseEntity endCarRentalWithAuthorization(final Long id,
                                                        final String authorization) {
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
