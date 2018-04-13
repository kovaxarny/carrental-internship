package com.epam.internship.carrental.service.reservation;

import com.epam.internship.carrental.exceptions.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        reservationRepository.save(ReservationConverter.rentedCarFromRentedCarViewObject(reservationVO));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endCarRental(final Long id) {
        Optional<Reservation> optionalRentedCar = reservationRepository.findById(id);
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
        Optional<Reservation> optionalRentedCar = reservationRepository.findById(id);
        if (!optionalRentedCar.isPresent()) {
            throw new ReservationNotFoundException();
        } else {
            Reservation modifiableReservation = optionalRentedCar.get();
            Reservation updatedReservation = ReservationConverter.rentedCarFromRentedCarViewObject(reservationVO);
            Reservation modifiedReservation = ReservationUtil.modifyRentedCar(modifiableReservation, updatedReservation);
            return ReservationConverter.rentedCarViewObjectFromRentedCar(reservationRepository.save(modifiedReservation));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReservationVO> listAllCarRental(final Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(ReservationConverter::rentedCarViewObjectFromRentedCar);
    }
}
