package com.epam.internship.carrental.service.alert;

import com.epam.internship.carrental.CarrentalApplication;
import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchServiceImpl;
import com.epam.internship.carrental.service.user.User;
import com.epam.internship.carrental.service.user.UserAlreadyExistsException;
import com.epam.internship.carrental.service.user.UserCriteriaBuilder;
import com.epam.internship.carrental.service.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("alertService")
public class AlertServiceImpl implements AlertService {
    private static final Logger LOGGER = LogManager.getLogger(CarrentalApplication.class);

    private final UserRepository userRepository;

    private final SearchServiceImpl searchService;

    @Autowired
    public AlertServiceImpl(final UserRepository userRepository,
                            final SearchServiceImpl searchService) {
        this.userRepository = userRepository;
        this.searchService = searchService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribeUser(Search search) {
        try{
            Specification<User> emailSpec = UserCriteriaBuilder.filterByUserEmail(search.getUser().getUserEmail());
            List<User> userList = userRepository.findAll(Specification.where(emailSpec));
            if (userList.isEmpty()){
                searchService.saveSearchInformation(search);
            }else{
                search.setUser(userList.get(0));
                searchService.saveSearchInformation(search);
            }

        }catch (DataAccessException e){
            LOGGER.error(e);
            throw new UserAlreadyExistsException("Something went wrong in the User Repository, while subscribing");
        }
    }
}
