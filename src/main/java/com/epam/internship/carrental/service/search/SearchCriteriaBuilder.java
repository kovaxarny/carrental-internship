package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.Car;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;

/**
 * Criteria builder for Search database queries.
 */
public class SearchCriteriaBuilder {

    private SearchCriteriaBuilder() {
    }

    /**
     * Creates a query criteria which allows filtering by car maker.
     * @param make email to filter by
     * @return equal criteria
     */
    public static Specification<Car> filterByMaker(@NotNull String make) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (make != null) {
                return criteriaBuilder.like(root.get("make"), "%" + make + "%");
            }
            return null;
        };
    }

    /**
     * Creates a query criteria which allows filtering by car model.
     * @param model email to filter by
     * @return like criteria
     */
    public static Specification<Car> filterByModel(@NotNull String model) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (model != null) {
                return criteriaBuilder.like(root.get("model"), "%" + model + "%");
            }
            return null;
        };
    }

    /**
     * Creates a query criteria which allows filtering by carType.
     * @param carType email to filter by
     * @return equal criteria
     */
    public static Specification<Car> filterByType(@NotNull Car.CarType carType) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (carType != null) {
                return criteriaBuilder.equal(root.get("carType"), carType);
            }
            return null;
        };
    }

    /**
     * Creates a query criteria which allows filtering by number of seats.
     * @param seats email to filter by
     * @return equal criteria
     */
    public static Specification<Car> filterBySeats(@NotNull int seats) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (seats != 0) {
                return criteriaBuilder.equal(root.get("seats"), seats);
            }
            return null;
        };
    }

    /**
     * Creates a query criteria which allows filtering by car's fuelUsage.
     * @param fuelUsage email to filter by
     * @return lower than criteria
     */
    public static Specification<Car> filterByFuelUsage(@NotNull double fuelUsage) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (fuelUsage != 0.0) {
                return criteriaBuilder.lt(root.get("fuelUsage"), fuelUsage);
            }
            return null;
        };
    }

    /**
     * Creates a query criteria which allows filtering by gearbox.
     * @param gearbox gearbox to filter by
     * @return equal criteria
     */
    public static Specification<Car> filterByGearbox(@NotNull Car.CarGearbox gearbox) {
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            if (gearbox != null) {
                return criteriaBuilder.equal(root.get("gearbox"), gearbox);
            }
            return null;
        };
    }
}
