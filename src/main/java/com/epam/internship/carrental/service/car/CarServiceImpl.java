package com.epam.internship.carrental.service.car;

import com.epam.internship.carrental.service.car.exception.CarAlreadyExistsException;
import com.epam.internship.carrental.service.car.exception.CarNotFoundException;
import com.epam.internship.carrental.service.car.exception.CarRepositoryException;
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
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
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
        }catch (DataAccessException e){
            throw new CarRepositoryException("Something went wrong in the Car Repository");
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
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
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
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
        }
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException();
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
        }catch (DataAccessException e){
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
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
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
        }
        if (carRepository.existByCarParameters(insertableCar.getMake(), insertableCar.getModel(),
                insertableCar.getCarType(), insertableCar.getSeats(),
                insertableCar.getFuelUsage(), insertableCar.getGearbox())) {
            throw new CarAlreadyExistsException();
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
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new CarRepositoryException("Something went wrong in the Car Repository");
        }
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException();
        }
        Car carToUpdate = optionalCar.get();
        Car updatedCar = CarUtil.updateCar(carToUpdate,
                CarToVOConverter.carFromCarViewObject(carVO));
        return CarToVOConverter.carViewObjectFromCar(carRepository.save(updatedCar));

    }


}
