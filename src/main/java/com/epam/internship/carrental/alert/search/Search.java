package com.epam.internship.carrental.alert.search;

import com.epam.internship.carrental.alert.user.User;
import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import lombok.*;

import javax.persistence.*;

/**
 * Entity named Search for storing searches.
 */
@Entity
public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
class Search {

    /**
     * The id is used for identifying the records in the database.
     * <p>
     * It's value is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who made this search.
     */
    @ManyToOne(targetEntity = User.class)
    private User user;
    /**
     * The searchedMake field is used for defining the car maker we want to search to.
     */
    @Column(nullable = false)
    private String searchedMake;

    /**
     * The searchedModel field is used for defining the car model we want to search to.
     */
    @Column(nullable = false)
    private String searchedModel;

    /**
     * The searchedCarType can have only predefined values from.
     * {@link com.epam.internship.carrental.car.enums.CarType}
     */
    @Enumerated(EnumType.STRING)
    private CarType searchedCarType;

    /**
     * The searchedSeats field holds the number of seats in a car.
     */
    @Column(nullable = false)
    private int searchedSeats;

    /**
     * The field searchedFuelUsage has the cars fuel usage per 100 km.
     */
    @Column(nullable = false)
    private double searchedFuelUsage;

    /**
     * The searchedGearbox field has its values declared in.
     * {@link com.epam.internship.carrental.car.enums.CarGearbox}
     */
    @Enumerated(EnumType.STRING)
    private CarGearbox searchedGearbox;
}
