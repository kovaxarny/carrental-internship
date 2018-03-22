package com.epam.internship.carrental.rentedcar;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long carId;

    @Column(nullable = false)
    private Date startOfRental;

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
