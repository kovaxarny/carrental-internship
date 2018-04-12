package com.epam.internship.carrental.rentedcar;

import com.epam.internship.carrental.exceptions.RentedCarNotFoundException;
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


    private final RentedCarRepository rentedCarRepository;

    @Autowired
    public RentedCarServiceImpl(final RentedCarRepository rentedCarRepository) {
        this.rentedCarRepository = rentedCarRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bookCarRental(final RentedCarViewObject rentedCarViewObject) {
        rentedCarRepository.save(RentedCarConverter.rentedCarFromRentedCarViewObject(rentedCarViewObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endCarRental(final Long id) {
        Optional<RentedCar> optionalRentedCar = rentedCarRepository.findById(id);
        if (!optionalRentedCar.isPresent()) {
            throw new RentedCarNotFoundException();
        }
        rentedCarRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RentedCarViewObject modifyCarRental(final Long id,
                                               final RentedCarViewObject rentedCarViewObject) {
        Optional<RentedCar> optionalRentedCar = rentedCarRepository.findById(id);
        if (!optionalRentedCar.isPresent()) {
            throw new RentedCarNotFoundException();
        } else {
            RentedCar modifiableRentedCar = optionalRentedCar.get();
            RentedCar updatedRentedCar = RentedCarConverter.rentedCarFromRentedCarViewObject(rentedCarViewObject);
            RentedCar modifiedRentedCar = RentedCarUtilities.modifyRentedCar(modifiableRentedCar, updatedRentedCar);
            return RentedCarConverter.rentedCarViewObjectFromRentedCar(rentedCarRepository.save(modifiedRentedCar));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<RentedCarViewObject> listAllCarRental(final Pageable pageable) {
        return rentedCarRepository.findAll(pageable)
                .map(RentedCarConverter::rentedCarViewObjectFromRentedCar);
    }
}
