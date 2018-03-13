package com.epam.internship.carrental;

import com.epam.internship.carrental.controller.MainController;
import com.epam.internship.carrental.enums.CarType;
import com.epam.internship.carrental.enums.Gearbox;
import com.epam.internship.carrental.model.Car;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
//@SpringBootTest
@EnableWebMvc
@WebMvcTest(value = MainController.class, secure = false)
public class CarrentalApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainController mainController;

    @Test
    public void retrieveCarData() throws Exception{
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id",1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "Sedan");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put(	"gearbox", "Automatic");

        String expectedString = "{\n" +
                "\t\"make\": \"Volvo\",\n" +
                "\t\"model\": \"V60\",\n" +
                "\t\"carType\": \"Sedan\",\n" +
                "\t\"seats\": 5,\n" +
                "\t\"fuelUsage\": 5.4,\n" +
                "\t\"gearbox\": \"Automatic\"\n" +
                "}";

        Car mockCar = new Car("Volvo","V60",CarType.Sedan,5,5.4,Gearbox.Automatic);

        Mockito.when(
                mainController.echoCar(Mockito.any(Car.class))
        ).thenReturn(mockCar);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/echo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals("Yay",expectedString,response.getContentAsString(),false);

    }

	@Test
	public void contextLoads() {
	}

	@Test
	public void CarCreation() {
		Car car = new Car();
		car.setId(1L);
		car.setMake("Volvo");
		car.setModel("V60");
		car.setCarType(CarType.Sedan);
		car.setSeats(5);
		car.setFuelUsage(5.4);
		car.setGearbox(Gearbox.Automatic);

		Assert.assertEquals("It's fine ", car.toString(),"Car{make='Volvo', model='V60', carType=Sedan, seats=5, fuelUsage=5.4, gearbox=Automatic}" );
	}

}
