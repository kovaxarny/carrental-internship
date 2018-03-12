package com.epam.internship.carrental;

import com.epam.internship.carrental.model.Car;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrentalApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void CarCreation() {
		Car car = new Car();
		car.setId(1);
		car.setType("Ford");

		Assert.assertEquals("It's fine ", car.toString(),"Car{type='Ford'}" );
	}

}
