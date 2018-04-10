package com.epam.internship.carrental.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Defines the methods, which has to be used when accessing the CarRepository.
 */
public interface CarService {

    /**
     * Retrieves all Car objects from the database.
     *
     * @return An Iterable containing all Cars as CarViewObjects
     */
    Iterable<CarViewObject> getAllCars();

    /**
     * Returns all cars from the database in a pageable format.
     *
     * @param pageable standard pageable parameter
     * @return All Cars as CarViewObjects in a pageable format
     */
    Page<CarViewObject> getAllCars(Pageable pageable);

    /**
     * Retrieves all free cars form the database.
     *
     * @param pageable standard pageable parameter
     * @return Free cars in a pageable format
     */
    Page<CarViewObject> getAllFreeCars(Pageable pageable);

    /**
     * Retrieves all data about a single car specified by the id.
     *
     * @param carId         a single carId
     * @return A single CarViewObject
     */
    CarViewObject getCarById(Long carId);

    /**
     * Retrieves all Cars by a given maker.
     *
     * @param make maker of the Cars.
     * @return An Iterable containing all Cars as CarViewObjects made by the maker
     */
    Iterable<CarViewObject> getCarsByMake(String make);


    /**
     * Inserts a new Car into the database
     *
     * @param carViewObject           CarViewObject object to be added to the database
     * @return The inserted CarViewObject
     */
    void insertNewCar(CarViewObject carViewObject);

    /**
     * Updates the Car object in the database, with the parameters specified in the request body.
     *
     * @param carId         the updateable car's id
     * @param carViewObject  the new parameters of the car
     * @return The updated CarViewObject
     */
    CarViewObject updateCarWithParameters(Long carId, CarViewObject carViewObject);


}
