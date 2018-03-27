package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Defines the methods, which has to be used when accessing the CarRepository.
 */
public interface CarService {

    /**
     * Adds a new Car to the database, which is created from the parameters, if it isn't already in the database.
     *
     * @param make       the make of the car
     * @param model      model of the car
     * @param carType    carType of the car
     * @param seats      number of seats of the car
     * @param fuelUsage  fuel usage of the car
     * @param carGearbox gearbox of the car
     * @return ResponseEntity with Response Code 200 on success, 403 on failure.
     */
    ResponseEntity addNewCar(String make, String model, CarType carType, int seats, double fuelUsage, CarGearbox carGearbox);

    /**
     * Adds a new Car to the database, given in the parameters.
     *
     * @param car Car object to be added
     * @return ResponseEntity with Response Code 200 on success.
     */
    ResponseEntity addNewCar(Car car);

    /**
     * Retrieves all Car objects from the database, and returns them in a ResponseEntity.
     *
     * @return ResponseEntity containing all Cars and Response Code 200 on success.
     */
    ResponseEntity<Iterable<Car>> getAllCars();

    /**
     * Retrieves all Cars by a given maker, and returns them in a ResponseEntity.
     *
     * @param make maker of the Cars.
     * @return ResponseEntity containing all cars made by the maker and Response Code 200 on success.
     */
    ResponseEntity<Iterable<Car>> getCarsByMake(String make);

    /**
     * Returns all cars from the database in a pageable format.
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with Page of Cars and Response Code 200 on success.
     */
    ResponseEntity<Page<Car>> getAllCars(Pageable pageable);

    /**
     * Simple echo Service which puts the Car object specified in the body, into a ResponseEntity and returns
     * it back to the request sender.
     *
     * @param car Car object
     * @return ResponseEntity containing the Car and Response Code 200 on success.
     */
    ResponseEntity<Car> echoCar(Car car);

    /**
     * Returns all cars from the database if the request sender has authorization.
     *
     * @param pageable      standard pageable parameter
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with all cars in a pageable format and Response Code 200 on success,
     * or Response Code 403 if there is no token specified in the request.
     */
    ResponseEntity<Page<Car>> getAllCarsWithAuthorization(Pageable pageable, String authorization);

    /**
     * Retrieves all data about a single car specified by the id.
     *
     * @param carId         a single carId
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with a single Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized, or the car with carId doesn't exist
     */
    ResponseEntity<Optional<Car>> getCarByIdWithAuthorization(Long carId, String authorization);

    /**
     * Inserts a new Car into the database, if the user has authorization.
     *
     * @param car           Car object to be added to the database
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with the inserted Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized
     */
    ResponseEntity<Car> insertNewCarWithAuthorization(Car car, String authorization);

    /**
     * Updates the Car object in the database, with the parameters specified in the request body.
     *
     * @param carId         the updateable car's id
     * @param newCarParams  the new parameters of the car
     * @param authorization authorization token contained in the header of the request
     * @return ResponseEntity with the updated Car object in it and Response Code 200 on success,
     * or Response Code 403 if unauthorized or the car doesn't exist
     */
    ResponseEntity<Car> updateCarByGivenParametersWithAuthorization(Long carId, Car newCarParams, String authorization);

    /**
     * Retrieves all free cars form the database.
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with the free cars in a pageable format and Response Code 200 on success
     */
    ResponseEntity<Page<Car>> getAllFreeCars(Pageable pageable);

    /**
     * Retrieves all cars from the database and converts them into CarViewObjects.
     *
     * @param pageable standard pageable parameter
     * @return ResponseEntity with all cars in CarViewObject format and Response Code 200 on success
     */
    ResponseEntity<Page<CarViewObject>> getAllCarViewObject(Pageable pageable);

    /**
     * Inserts a new Car into the database after converting a CarViewObject into a Car object.
     *
     * @param carViewObject carViewObject to be inserted
     * @return ResponseEntity with Response Code 200 on success
     */
    ResponseEntity insertNewCarFromViewObject(CarViewObject carViewObject);
}
