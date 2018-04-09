package com.epam.internship.carrental.rentedcar;

import com.epam.internship.carrental.car.CarServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = RentedCarController.class, secure = false)
public class RentedCarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @Test
    public void test(){
        Assert.assertEquals(1L,1L);
    }
}
