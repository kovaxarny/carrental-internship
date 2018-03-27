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
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param make       the make of the car
     * @param model      model of the car
     * @param carType    carType of the car
     * @param seats      number of seats of the car
     * @param fuelUsage  fuel usage of the car
     * @param carGearbox gearbox of the car
     * @return ResponseEntity with Response Code 200 on success, 403 on failure.
     */
    @Override
    public ResponseEntity addNewCar(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox carGearbox) {
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
     *
     * @param car Car object to be added
     * @return ResponseEntity with Response Code 200 on success.
     */
    @Override
    public ResponseEntity addNewCar(Car car) {
        if (carRepository.existByCar(car.getMake(), car.getModel(), car.getCarType(), car.getSeats(), car.getFuelUsage(), car.getGearbox())) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        carRepository.save(car);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @return ResponseEntity containing all Cars and Response Code 200 on success.
     */
    @Override
    public ResponseEntity<Iterable<Car>> getAllCars() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param make maker of the Cars.
     * @return ResponseEntity containing all cars made by the maker and Response Code 200 on success.
     */
    @Override
    public ResponseEntity<Iterable<Car>> getCarsByMake(String make) {
        return new ResponseEntity<>(carRepository.findByMake(make), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with Page of Cars and Response Code 200 on success.
     */
    @Override
    public ResponseEntity<Page<Car>> getAllCars(Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param car Car object
     * @return ResponseEntity containing the Car and Response Code 200 on success.
     */
    @Override
    public ResponseEntity<Car> echoCar(Car car) {
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable      standard pageable parameter
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with all cars in a pageable format and Response Code 200 on success,
     * or Response Code 403 if there is no token specified in the request.
     */
    @Override
    public ResponseEntity<Page<Car>> getAllCarsWithAuthorization(Pageable pageable, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findAll(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param carId         a single carId
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with a single Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized, or the car with carId doesn't exist
     */
    @Override
    public ResponseEntity<Optional<Car>> getCarByIdWithAuthorization(Long carId, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findById(carId), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param car           Car object to be added to the database
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with the inserted Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized
     */
    @Override
    public ResponseEntity<Car> insertNewCarWithAuthorization(Car car, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param carId         the updateable car's id
     * @param newCarParams  the new parameters of the car
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with the updated Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized or the car doesn't exist
     */
    @Override
    public ResponseEntity<Car> updateCarByGivenParametersWithAuthorization(Long carId, Car newCarParams, String authorization) {
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
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with the free cars in a pageable format and Response Code 200 on success
     */
    @Override
    public ResponseEntity<Page<Car>> getAllFreeCars(Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAllFree(pageable), HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with all cars in CarViewObject format and Response Code 200 on success
     */
    @Override
    public ResponseEntity<Page<CarViewObject>> getAllCarViewObject(Pageable pageable) {
        Page<CarViewObject> carViewObjectPage = carRepository.findAll(pageable)
                .map(CarConverter::carViewObjectFromCar);
        return new ResponseEntity<>(carViewObjectPage, HttpStatus.OK);
    }

    /**
     * {@inheritDoc}
     *
     * @param carViewObject carViewObject to be inserted
     * @return ResponseEntity with Response Code 200 on success
     */
    @Override
    public ResponseEntity insertNewCarFromViewObject(CarViewObject carViewObject) {
        Car carToSave = CarConverter.carFromCarViewObject(carViewObject);
        carRepository.save(carToSave);
        return new ResponseEntity(HttpStatus.OK);
    }
}
