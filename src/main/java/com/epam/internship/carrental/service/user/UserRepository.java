package com.epam.internship.carrental.service.user;

import com.epam.internship.carrental.service.user.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for accessing the User repository.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>, JpaSpecificationExecutor<User> {
}
