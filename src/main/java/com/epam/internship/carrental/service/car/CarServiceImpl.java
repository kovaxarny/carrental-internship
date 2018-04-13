package com.epam.internship.carrental.service.car;

import com.epam.internship.carrental.exceptions.CarAlreadyExistsException;
import com.epam.internship.carrental.exceptions.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        carRepository.findAll().forEach(carCollection::add);
        for (Car car : carCollection) {
            carVOCollection.add(CarConverter.carViewObjectFromCar(car));
        }
        return carVOCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarVO> getAllCars(final Pageable pageable) {
        return carRepository.findAll(pageable)
                .map(CarConverter::carViewObjectFromCar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarVO> getAllFreeCars(final Pageable pageable) {
        return carRepository.findAllFree(pageable)
                .map(CarConverter::carViewObjectFromCar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarVO getCarById(final Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException();
        } else {
            return CarConverter.carViewObjectFromCar(optionalCar.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<CarVO> getCarsByMake(final String make) {
        Collection<Car> carCollection = new ArrayList<>();
        Collection<CarVO> carVOCollection = new ArrayList<>();
        carRepository.findByMake(make).forEach(carCollection::add);
        for (Car car : carCollection) {
            carVOCollection.add(CarConverter.carViewObjectFromCar(car));
        }
        return carVOCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertNewCar(final CarVO carVO) {
        Car insertableCar = CarConverter.carFromCarViewObject(carVO);
        if (!carRepository.existByCarParameters(insertableCar.getMake(),insertableCar.getModel(),
                insertableCar.getCarType(),insertableCar.getSeats(),
                insertableCar.getFuelUsage(),insertableCar.getGearbox())){
            carRepository.save(insertableCar);
        }else {
            throw new CarAlreadyExistsException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarVO updateCarWithParameters(final Long carId, final CarVO carVO) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException();
        } else {
            Car carToUpdate = optionalCar.get();
            Car updatedCar = CarUtil.updateCar(carToUpdate,
                    CarConverter.carFromCarViewObject(carVO));
            return CarConverter.carViewObjectFromCar(carRepository.save(updatedCar));
        }
    }


}
