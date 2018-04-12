package com.epam.internship.carrental.rentedcar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public @Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RentedCarViewObject {
    /**
     * The rented car's ID.
     */
    private Long carId;

    /**
     * The date when the rent of the car begins.
     */
    private Date startOfRental;

    /**
     * The date when the rent ends.
     */
    private Date endOfRental;
}
