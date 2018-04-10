package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link com.epam.internship.carrental.car.CarService} interface.
 */
@Service
@Qualifier("carService")
public class CarServiceImpl implements CarService {

    private static final String AUTH_HEADER = "Token ";
    private static final String AUTH_TOKEN = AUTH_HEADER + "token";

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity addNewCar(final String make, final String model,
                                    final CarType carType, final int seats,
                                    final double fuelUsage, final CarGearbox carGearbox) {
        if (carRepository.existByCar(make, model, carType, seats, fuelUsage, carGearbox)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Car car = Car.builder()
                .make(make)
                .model(model)
                .carType(carType)
                .seats(seats)
                .fuelUsage(fuelUsage)
                .gearbox(carGearbox)
                .build();
        carRepository.save(car);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity addNewCar(final Car car) {
        if (carRepository.existByCar(car.getMake(), car.getModel(), car.getCarType(),
                car.getSeats(), car.getFuelUsage(), car.getGearbox())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        carRepository.save(car);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Iterable<Car>> getAllCars() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Iterable<Car>> getCarsByMake(final String make) {
        return new ResponseEntity<>(carRepository.findByMake(make), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Page<Car>> getAllCars(final Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Car> echoCar(final Car car) {
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Page<Car>> getAllCarsWithAuthorization(final Pageable pageable,
                                                                 final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Optional<Car>> getCarByIdWithAuthorization(final Long carId,
                                                                     final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findById(carId), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Car> insertNewCarWithAuthorization(final Car car,
                                                             final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Car> updateCarByGivenParametersWithAuthorization(final Long carId,
                                                                           final Car newCarParams,
                                                                           final String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Car carToUpdate = optionalCar.get();
            Car updatedCar = CarUtilities.updateCar(carToUpdate, newCarParams);
            return new ResponseEntity<>(carRepository.save(updatedCar), HttpStatus.OK);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Page<Car>> getAllFreeCars(final Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAllFree(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Page<CarViewObject>> getAllCarViewObject(final Pageable pageable) {
        Page<CarViewObject> carViewObjectPage = carRepository.findAll(pageable)
                .map(CarConverter::carViewObjectFromCar);
        return new ResponseEntity<>(carViewObjectPage, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity insertNewCarFromViewObject(final CarViewObject carViewObject) {
        Car carToSave = CarConverter.carFromCarViewObject(carViewObject);
        carRepository.save(carToSave);
        return new ResponseEntity(HttpStatus.OK);
    }
}
