package com.epam.internship.carrental.service.alert;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

/**
 * CriteriaBuilder class for querying the users.
 */
public class UserCriteriaBuilder {

    private UserCriteriaBuilder() {
    }

    /**
     * Creates a query criteria which allows filtering by user's email address.
     * @param email email to filter by
     * @return equal criteria
     */
    public static Specification<User> filterByUserEmail(@NotNull String email) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (email != null) {
                    return criteriaBuilder.equal(root.get("userEmail"), email);
                }
                return null;
            }
        };
    }
}
