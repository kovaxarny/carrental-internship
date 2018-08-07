package com.epam.internship.carrental.service.car;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.car.exception.CarAlreadyExistsException;
import com.epam.internship.carrental.service.car.exception.CarNotFoundException;
import com.epam.internship.carrental.service.car.exception.CarOperationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Implementation of the {@link CarService} interface.
 */
@Service
@Qualifier("carService")
public class CarServiceImpl implements CarService {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<CarVO> getAllCars() {
        Collection<Car> carCollection = new ArrayList<>();
        Collection<CarVO> carVOCollection = new ArrayList<>();
        try {
            carRepository.findAll().forEach(carCollection::add);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while querying all Cars");
        }
        for (Car car : carCollection) {
            carVOCollection.add(CarToVOConverter.carViewObjectFromCar(car));
        }
        return carVOCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarVO> getAllCars(final Pageable pageable) {
        try {
            return carRepository.findAll(pageable)
                    .map(CarToVOConverter::carViewObjectFromCar);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while querying all Cars with paging");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarVO> getAllFreeCars(final Pageable pageable) {
        try {
            return carRepository.findAllFree(pageable)
                    .map(CarToVOConverter::carViewObjectFromCar);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while querying all free Cars");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarVO getCarById(final Long carId) {
        Optional<Car> optionalCar;
        try {
            optionalCar = carRepository.findById(carId);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while querying Car by ID");
        }
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException("Car not found");
        }
        return CarToVOConverter.carViewObjectFromCar(optionalCar.get());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<CarVO> getCarsByMake(final String make) {
        Collection<Car> carCollection = new ArrayList<>();
        Collection<CarVO> carVOCollection = new ArrayList<>();
        try {
            carRepository.findByMake(make).forEach(carCollection::add);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while querying Cars by maker");
        }
        for (Car car : carCollection) {
            carVOCollection.add(CarToVOConverter.carViewObjectFromCar(car));
        }
        return carVOCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertNewCar(final CarVO carVO) {
        Car insertableCar;
        try {
            insertableCar = CarToVOConverter.carFromCarViewObject(carVO);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while inserting new Car");
        }
        if (carRepository.existByCarParameters(insertableCar.getMake(), insertableCar.getModel(),
                insertableCar.getCarType(), insertableCar.getSeats(),
                insertableCar.getFuelUsage(), insertableCar.getGearbox())) {
            throw new CarAlreadyExistsException("Car already exists");
        }
        carRepository.save(insertableCar);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarVO updateCarWithParameters(final Long carId, final CarVO carVO) {
        Optional<Car> optionalCar;
        try {
            optionalCar = carRepository.findById(carId);
        } catch (DataAccessException e) {
            LOGGER.error(e);
            throw new CarOperationException("Something went wrong in the Car Repository while updating Car");
        }
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException("Car to be updated not found");
        }
        Car carToUpdate = optionalCar.get();
        Car updatedCar = CarUtil.updateCar(carToUpdate,
                CarToVOConverter.carFromCarViewObject(carVO));
        return CarToVOConverter.carViewObjectFromCar(carRepository.save(updatedCar));

    }


}
