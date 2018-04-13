package com.epam.internship.carrental.service.car;

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
    Iterable<CarVO> getAllCars();

    /**
     * Returns all cars from the database in a pageable format.
     *
     * @param pageable standard pageable parameter
     * @return All Cars as CarViewObjects in a pageable format
     */
    Page<CarVO> getAllCars(Pageable pageable);

    /**
     * Retrieves all free cars form the database.
     *
     * @param pageable standard pageable parameter
     * @return Free cars in a pageable format
     */
    Page<CarVO> getAllFreeCars(Pageable pageable);

    /**
     * Retrieves all data about a single car specified by the id.
     *
     * @param carId         a single carId
     * @return A single CarVO
     */
    CarVO getCarById(Long carId);

    /**
     * Retrieves all Cars by a given maker.
     *
     * @param make maker of the Cars.
     * @return An Iterable containing all Cars as CarViewObjects made by the maker
     */
    Iterable<CarVO> getCarsByMake(String make);


    /**
     * Inserts a new Car into the database
     *
     * @param carVO           CarVO object to be added to the database
     * @return The inserted CarVO
     */
    void insertNewCar(CarVO carVO);

    /**
     * Updates the Car object in the database, with the parameters specified in the request body.
     *
     * @param carId         the updateable car's id
     * @param carVO  the new parameters of the car
     * @return The updated CarVO
     */
    CarVO updateCarWithParameters(Long carId, CarVO carVO);


}
