package com.epam.internship.carrental.service.alert;

import com.epam.internship.carrental.service.car.Car;
import com.epam.internship.carrental.service.search.Search;
import com.epam.internship.carrental.service.search.SearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AlertServiceImplTest {

    @Configuration
    static class AlertServiceImplTestContextConfiguration {
        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public SearchRepository searchRepository() {
            return mock(SearchRepository.class);
        }

        @Bean
        public AlertServiceImpl alertService() {
            return new AlertServiceImpl(userRepository(), searchRepository());
        }
    }

    @Autowired
    private AlertServiceImpl alertService = mock(AlertServiceImpl.class, Mockito.RETURNS_DEEP_STUBS);

    @Autowired
    private UserRepository userRepository = mock(UserRepository.class, Mockito.RETURNS_DEEP_STUBS);
    @Autowired
    private SearchRepository searchRepository = mock(SearchRepository.class, Mockito.RETURNS_DEEP_STUBS);

    private Search search;
    private User user;

    @Before
    public void init() {
        user = User.builder()
                .userEmail("ecet@gmail.com")
                .build();
        search = Search.builder()
                .user(user)
                .searchedMake("Dacia")
                .searchedCarType(Car.CarType.SEDAN)
                .searchedFuelUsage(5)
                .searchedSeats(5)
                .searchedGearbox(Car.CarGearbox.AUTOMATIC)
                .searchedModel("Logan")
                .build();
    }

    @Test
    public void subscribeUser() {
        alertService.subscribeUser(search);
        verify(userRepository,times(1)).save(Mockito.any(User.class));
        verify(searchRepository,times(1)).save(Mockito.any(Search.class));
    }
}