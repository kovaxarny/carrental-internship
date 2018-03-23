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

    @Override
    public ResponseEntity addNewCar(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox carGearbox) {
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setCarType(carType);
        car.setSeats(seats);
        car.setFuelUsage(fuelUsage);
        car.setGearbox(carGearbox);
        carRepository.save(car);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity addNewCar(Car car) {
        carRepository.save(car);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCars() {
        return new ResponseEntity<Iterable>(carRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCarsByMake(String make) {
        return new ResponseEntity<Iterable>(carRepository.findByMake(make),HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAllCars(Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAll(pageable),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Car> echoCar(Car car) {
        return new ResponseEntity<>(car,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCarsWithAuthorization(Pageable pageable, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findAll(pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCarByIdWithAuthorization(Long carId, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.findById(carId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity insertNewCarWithAuthorization(Car car, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateCarByGivenParametersWithAuthorization(Long carId, Car newCarParams, String authorization) {
        if (!authorization.equals(AUTH_TOKEN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (!optionalCar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Car carToUpdate = optionalCar.get();
            Car updatedCar = Car.updateCar(carToUpdate, newCarParams);
            return new ResponseEntity<>(carRepository.save(updatedCar), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity getAllFreeCars(Pageable pageable) {
        return new ResponseEntity<>(carRepository.findAllFree(pageable),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCarViewObject(Pageable pageable) {
        Page<CarViewObject> carViewObjectPage = carRepository.findAll(pageable)
                .map(CarConverter::carViewObjectFromCar);
        return new ResponseEntity<>(carViewObjectPage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity insertNewCarFromViewObject(CarViewObject carViewObject) {
        Car carToSave = CarConverter.CarFromCarViewObject(carViewObject);
        carRepository.save(carToSave);
        return new ResponseEntity(HttpStatus.OK);
    }
}
