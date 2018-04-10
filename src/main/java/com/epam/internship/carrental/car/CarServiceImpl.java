package com.epam.internship.carrental.car;

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
 * Implementation of the {@link com.epam.internship.carrental.car.CarService} interface.
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
    public Iterable<CarViewObject> getAllCars() {
        Collection<Car> carCollection = new ArrayList<>();
        Collection<CarViewObject> carViewObjectCollection = new ArrayList<>();
        carRepository.findAll().forEach(carCollection::add);
        for (Car car : carCollection) {
            carViewObjectCollection.add(CarConverter.carViewObjectFromCar(car));
        }
        return carViewObjectCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarViewObject> getAllCars(final Pageable pageable) {
        return carRepository.findAll(pageable)
                .map(CarConverter::carViewObjectFromCar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CarViewObject> getAllFreeCars(final Pageable pageable) {
        return carRepository.findAllFree(pageable)
                .map(CarConverter::carViewObjectFromCar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarViewObject getCarById(final Long carId) {
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
    public Iterable<CarViewObject> getCarsByMake(final String make) {
        Collection<Car> carCollection = new ArrayList<>();
        Collection<CarViewObject> carViewObjectCollection = new ArrayList<>();
        carRepository.findByMake(make).forEach(carCollection::add);
        for (Car car : carCollection) {
            carViewObjectCollection.add(CarConverter.carViewObjectFromCar(car));
        }
        return carViewObjectCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertNewCar(final CarViewObject carViewObject) {
        Car insertableCar = CarConverter.carFromCarViewObject(carViewObject);
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
    public CarViewObject updateCarWithParameters(final Long carId, final CarViewObject carViewObject) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            throw new CarNotFoundException();
        } else {
            Car carToUpdate = optionalCar.get();
            Car updatedCar = CarUtilities.updateCar(carToUpdate,
                    CarConverter.carFromCarViewObject(carViewObject));
            return CarConverter.carViewObjectFromCar(carRepository.save(updatedCar));
        }
    }


}
