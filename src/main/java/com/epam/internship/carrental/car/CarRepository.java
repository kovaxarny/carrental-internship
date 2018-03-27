package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface to access the database of cars
 */
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {

    /**
     * The query is used to retrieve all cars made by the maker specified in the parameter.
     *
     * @param make make of cars to be searched to
     * @return Iterable which contains the cars by the maker
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "WHERE car.make = :make")
    Iterable<Car> findByMake(@Param("make") String make);

    /**
     * Retrieves all free cars from the database.
     *
     * @param pageable standard pageable parameter
     * @return Page of cars
     */
    @Query(value = "SELECT car FROM Car AS car " +
            "LEFT JOIN RentedCar AS rented ON car.id=rented.carId WHERE rented.carId IS NULL")
    Page<Car> findAllFree(Pageable pageable);

    /**
     * Used to search for a single car in the database, defined by the parameters
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
    boolean existByCar(@Param("make") String make, @Param("model") String model, @Param("carType") CarType carType,
                       @Param("seats") int seats, @Param("fuelUsage") double fuelUsage, @Param("gearbox") CarGearbox gearbox);
}
