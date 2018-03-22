package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Qualifier("carService")
public class CarServiceImpl implements CarService {
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
}
