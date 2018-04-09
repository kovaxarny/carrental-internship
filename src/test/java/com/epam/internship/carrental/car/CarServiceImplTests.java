package com.epam.internship.carrental.car;

import com.epam.internship.carrental.alert.search.Search;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarServiceImplTests {
    @MockBean
    private CarServiceImpl carService;

    private Search search;

    @Before
    public void setUp() {
        search = Search.builder().build();
    }

    @Test
    public void searchCarsByParameterTest() {
        Car car = Car.builder().build();
        List<Car> carList = Collections.singletonList(car);
        ResponseEntity<List<Car>> expectedResponseEntity = new ResponseEntity<>(carList, HttpStatus.OK);
        Mockito.when(carService.searchCarsByParameters(search))
                .thenReturn(expectedResponseEntity);

        ResponseEntity responseEntity = carService.searchCarsByParameters(search);

        Assert.assertEquals(responseEntity, expectedResponseEntity);
    }
}
