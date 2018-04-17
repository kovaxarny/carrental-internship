package com.epam.internship.carrental.service.reservation;

import com.epam.internship.carrental.service.reservation.exception.ReservationNotFoundException;
import com.epam.internship.carrental.service.reservation.exception.ReservationRepositoryException;
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


    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bookCarRental(final ReservationVO reservationVO) {
        try {
            reservationRepository.save(ReservationToVOConverter.rentedCarFromRentedCarViewObject(reservationVO));
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ReservationRepositoryException("Something went wrong in the Reservation Repository");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endCarRental(final Long id) {
        Optional<Reservation> optionalRentedCar;
        try {
            optionalRentedCar = reservationRepository.findById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ReservationRepositoryException("Something went wrong in the Reservation Repository");
        }
        if (!optionalRentedCar.isPresent()) {
            throw new ReservationNotFoundException();
        }
        reservationRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationVO modifyCarRental(final Long id,
                                         final ReservationVO reservationVO) {
        Optional<Reservation> optionalRentedCar;
        try {
            optionalRentedCar = reservationRepository.findById(id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new ReservationRepositoryException("Something went wrong in the Reservation Repository");
        }
        if (!optionalRentedCar.isPresent()) {
            throw new ReservationNotFoundException();
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
            e.printStackTrace();
            throw new ReservationRepositoryException("Something went wrong in the Reservation Repository");
        }
    }
}
