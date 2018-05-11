package com.epam.internship.carrental.service.reservation;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.reservation.exception.ReservationNotFoundException;
import com.epam.internship.carrental.service.reservation.exception.ReservationOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link ReservationService} interface.
 */
@Service
@Qualifier("rentedCarService")
public class ReservationServiceImpl implements ReservationService {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bookCarReservation(final ReservationVO reservationVO) {
        try {
            reservationRepository.save(ReservationToVOConverter.rentedCarFromRentedCarViewObject(reservationVO));
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new ReservationOperationException("Something went wrong in the Reservation Repository while booking");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endCarReservation(final Long id) {
        Optional<Reservation> optionalRentedCar;
        try {
            optionalRentedCar = reservationRepository.findById(id);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new ReservationOperationException("Something went wrong in the Reservation Repository while ending rental");
        }
        if (!optionalRentedCar.isPresent()) {
            throw new ReservationNotFoundException("No reservation found");
        }
        reservationRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationVO modifyCarReservation(final Long id,
                                              final ReservationVO reservationVO) {
        Optional<Reservation> optionalRentedCar;
        try {
            optionalRentedCar = reservationRepository.findById(id);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new ReservationOperationException("Something went wrong in the Reservation Repository while modifying rental");
        }
        if (!optionalRentedCar.isPresent()) {
            throw new ReservationNotFoundException("Given reservation not found");
        }
        Reservation modifiableReservation = optionalRentedCar.get();
        Reservation updatedReservation = ReservationToVOConverter.rentedCarFromRentedCarViewObject(reservationVO);
        Reservation modifiedReservation = ReservationUtil.modifyRentedCar(modifiableReservation, updatedReservation);
        return ReservationToVOConverter.rentedCarViewObjectFromRentedCar(reservationRepository.save(modifiedReservation));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReservationVO> listAllCarRental(final Pageable pageable) {
        try {
            return reservationRepository.findAll(pageable)
                    .map(ReservationToVOConverter::rentedCarViewObjectFromRentedCar);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new ReservationOperationException("Something went wrong in the Reservation Repository while getting rentals");
        }
    }
}
