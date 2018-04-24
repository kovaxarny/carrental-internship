package com.epam.internship.carrental.service.alert;

import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("alertService")
public class AlertServiceImpl implements AlertService {

    private final UserRepository userRepository;

    private final SearchServiceImpl searchService;

    @Autowired
    public AlertServiceImpl(final UserRepository userRepository,
                            final SearchServiceImpl searchService) {
        this.userRepository = userRepository;
        this.searchService = searchService;
    }

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
            e.printStackTrace();
            throw new UserAlreadyExistsException("Something went wrong in the User Repository, while subscribing");
        }
    }
}
