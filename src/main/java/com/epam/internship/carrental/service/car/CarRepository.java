package com.epam.internship.carrental.service.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface to access the database of cars.
 */
@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    /**
     * The query retrieves all cars made by the maker specified in the parameter.
     *
     * @param make make of cars to be searched to
     * @return Iterable which contains the cars by the maker
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "WHERE car.make = :make")
    Iterable<Car> findByMake(@Param("make") String make);

    /**
     *  The query retrieves all free cars from the database.
     *
     * @param pageable standard pageable parameter
     * @return Page of cars
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "LEFT JOIN Reservation AS rented ON car.id=rented.carId WHERE rented.carId IS NULL")
    Page<Car> findAllFree(Pageable pageable);

    /**
     * The query searches for a single car in the database, defined by the parameters.
     *
     * @param make      maker of the searched car
     * @param model     model of the searched car
     * @param carType   type of the searched car
     * @param seats     number of seats of the searched car
     * @param fuelUsage fuel usage of the searched car
     * @param gearbox   gearbox of the searched car
     * @return true if exists, else false
     */
    @Query(value = "SELECT " +
            "CASE WHEN COUNT(car) > 0 THEN true ELSE false END " +
            "FROM Car AS car " +
            "WHERE car.make = :make " +
            "AND car.model = :model " +
            "AND car.carType = :carType " +
            "AND car.seats = :seats " +
            "AND car.fuelUsage = :fuelUsage " +
            "AND car.gearbox = :gearbox")
    boolean existByCarParameters(@Param("make") String make, @Param("model") String model, @Param("carType") Car.CarType carType,
                                 @Param("seats") int seats, @Param("fuelUsage") double fuelUsage, @Param("gearbox") Car.CarGearbox gearbox);
}
