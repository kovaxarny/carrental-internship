package com.epam.internship.carrental.service.alert;

import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("alertService")
public class AlertServiceImpl implements AlertService {

    private final UserRepository userRepository;

    private final SearchRepository searchRepository;

    @Autowired
    public AlertServiceImpl(final UserRepository userRepository,
                            final SearchRepository searchRepository) {
        this.userRepository = userRepository;
        this.searchRepository = searchRepository;
    }

    @Override
    public void subscribeUser(Search search) {
        userRepository.save(search.getUser());
        searchRepository.save(search);
    }
}
