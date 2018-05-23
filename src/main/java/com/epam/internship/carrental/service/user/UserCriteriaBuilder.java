package com.epam.internship.carrental.service.user;

import com.epam.internship.carrental.service.user.User;
import org.springframework.data.jpa.domain.Specification;

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
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            if (email != null) {
                return criteriaBuilder.equal(root.get("userEmail"), email);
            }
            return null;
        };
    }
}
