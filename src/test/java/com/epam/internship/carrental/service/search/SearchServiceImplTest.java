package com.epam.internship.carrental.service.search;

import com.epam.internship.carrental.service.user.User;
import com.epam.internship.carrental.service.car.Car;
import com.epam.internship.carrental.service.car.CarRepository;
import com.epam.internship.carrental.service.car.CarToVOConverter;
import com.epam.internship.carrental.service.car.CarVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SearchServiceImplTest {

    @Configuration
    static class SearchServiceImplTestContextConfiguration {
        @Bean
        public CarRepository carRepository() {
            return mock(CarRepository.class);
        }

        @Bean
        public SearchRepository searchRepository() {
            return mock(SearchRepository.class);
        }

        @Bean
        public SearchServiceImpl searchService() {
            return new SearchServiceImpl(carRepository(),searchRepository());
        }
    }

    @Autowired
    private SearchServiceImpl searchService = mock(SearchServiceImpl.class, Mockito.RETURNS_DEEP_STUBS);

    @Autowired
    private CarRepository carRepository = mock(CarRepository.class, Mockito.RETURNS_DEEP_STUBS);
    @Autowired
    private SearchRepository searchRepository = mock(SearchRepository.class, Mockito.RETURNS_DEEP_STUBS);

    private Car car;
    private Page<Car> page;
    private Page<CarVO> voPage;
    private Search search;
    private User user;
    private List<CarVO> carVOList;
    private List<Car> carList;

    @Before
    public void init() {
        car = Car.builder().make("Dacia")
                .carType(Car.CarType.SEDAN)
                .fuelUsage(5)
                .seats(5)
                .gearbox(Car.CarGearbox.AUTOMATIC)
                .model("Logan")
                .build();
        page = new PageImpl<Car>(Collections.singletonList(car));
        voPage = page.map(CarToVOConverter::carViewObjectFromCar);
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
        carList = Collections.singletonList(car);
        carVOList = Collections.singletonList(CarToVOConverter.carViewObjectFromCar(car));
    }

    @Test
    public void saveSearchInformation() {
        searchService.saveSearchInformation(search);
        verify(searchRepository,times(1)).save(Mockito.any(Search.class));
    }

//    @Test
//    public void searchCars() {
//        Mockito.when(carRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
//        Mockito.when(searchService.searchCarsWithSpec(Mockito.any(Search.class),Mockito.any(Pageable.class))).thenReturn(voPage);
//        Page<CarVO> resultPage = searchService.searchCarsWithSpec(Mockito.any(Search.class),Mockito.any(Pageable.class));
//        verify(carRepository, times(1)).findAll(Mockito.any(Pageable.class));
//        Assert.assertEquals(resultPage, voPage);
//    }
}