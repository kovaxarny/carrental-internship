package com.epam.internship.carrental.rentedcar;

import javax.persistence.*;
import java.util.Date;

/**
 * The entity class for the RentedCar table which contains currently rented cars
 */
@Entity
public class RentedCar {

    /**
     * The id is used for identifying the records in the database.
     *
     * It's value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The rented car's ID
     */
    @Column(nullable = false)
    private Long carId;

    /**
     * The date when the rent of the car begins
     */
    @Column(nullable = false)
    private Date startOfRental;

    /**
     * The date when the rent ends.
     */
    @Column(nullable = false)
    private Date endOfRental;

    public RentedCar() {
    }

    public RentedCar(Long id, Long carId, Date startOfRental, Date endOfRental) {
        this.id = id;
        this.carId = carId;
        this.startOfRental = startOfRental;
        this.endOfRental = endOfRental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartOfRental() {
        return startOfRental;
    }

    public void setStartOfRental(Date startOfRental) {
        this.startOfRental = startOfRental;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Date getEndOfRental() {
        return endOfRental;
    }

    public void setEndOfRental(Date endOfRental) {
        this.endOfRental = endOfRental;
    }
}
