package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.car.Car;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

public class SearchCriteriaBuilder {

    private SearchCriteriaBuilder() {
    }

    public static Specification<Car> filterByMaker(@NotNull String make) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (make != null) {
                    return criteriaBuilder.like(root.get("make"), "%" + make + "%");
                }
                return null;
            }
        };
    }

    public static Specification<Car> filterByModel(@NotNull String model) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (model != null) {
                    return criteriaBuilder.like(root.get("model"), "%" + model + "%");
                }
                return null;
            }
        };
    }

    public static Specification<Car> filterByType(@NotNull Car.CarType carType) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (carType != null) {
                    return criteriaBuilder.equal(root.get("carType"), carType);
                }
                return null;
            }
        };
    }

    public static Specification<Car> filterBySeats(@NotNull int seats) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (seats != 0) {
                    return criteriaBuilder.equal(root.get("seats"), seats);
                }
                return null;
            }
        };
    }

    public static Specification<Car> filterByFuelUsage(@NotNull double fuelUsage) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (fuelUsage != 0.0) {
                    return criteriaBuilder.lt(root.get("fuelUsage"), fuelUsage);
                }
                return null;
            }
        };
    }

    public static Specification<Car> filterByGearbox(@NotNull Car.CarGearbox gearbox) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (gearbox != null) {
                    return criteriaBuilder.equal(root.get("gearbox"), gearbox);
                }
                return null;
            }
        };
    }
}
