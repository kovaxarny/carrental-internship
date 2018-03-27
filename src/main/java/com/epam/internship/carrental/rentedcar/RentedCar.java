package com.epam.internship.carrental.rentedcar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * The entity class for the RentedCar table which contains currently rented cars.
 */
@Entity
public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RentedCar {

    /**
     * The id is used for identifying the records in the database.
     * It's value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The rented car's ID.
     */
    @Column(nullable = false)
    private Long carId;

    /**
     * The date when the rent of the car begins.
     */
    @Column(nullable = false)
    private Date startOfRental;

    /**
     * The date when the rent ends.
     */
    @Column(nullable = false)
    private Date endOfRental;
}
