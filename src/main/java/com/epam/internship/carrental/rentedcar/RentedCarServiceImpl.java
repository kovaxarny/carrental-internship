package com.epam.internship.carrental.rentedcar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     */
    @Override
    public void bookCarRentalWithAuthorization(final RentedCar rentedCar,
                                                         final String authorization) {
//        if (!authorization.equals(AUTH_TOKEN)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        rentedCarRepository.save(rentedCar);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endCarRentalWithAuthorization(final Long id,
                                                        final String authorization) {
//        if (!authorization.equals(AUTH_TOKEN)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        Optional<RentedCar> optionalRentedCar = rentedCarRepository.findById(id);
//        if (!optionalRentedCar.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        } else {
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
        rentedCarRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyCarRentalWithAuthorization(final Long id,
                                                           final RentedCar rentedCar,
                                                           final String authorization) {
//        if (!authorization.equals(AUTH_TOKEN)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        Optional<RentedCar> optionalRentedCar = rentedCarRepository.findById(id);
        if (!optionalRentedCar.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else {
            RentedCar modifiableRentedCar = optionalRentedCar.get();
            RentedCar modifiedRentedCar = RentedCarUtilities.modifyRentedCar(modifiableRentedCar,rentedCar);
            rentedCarRepository.save(modifiedRentedCar);
//            return new ResponseEntity<>(rentedCarRepository.save(modifiedRentedCar),HttpStatus.OK);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<RentedCar> listAllCarRentalWithAuthorization(final Pageable pageable,
                                                                             final String authorization) {
//        if (!authorization.equals(AUTH_TOKEN)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        return rentedCarRepository.findAll(pageable);
    }
}
