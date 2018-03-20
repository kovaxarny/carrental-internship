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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = MainController.class, secure = false)
public class CarrentalApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainController mainController;


    @Test
    public void AllCarsWithAuthorizationServiceTest() throws Exception {
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.OK);

        Mockito.when(mainController.getAllCarsWithAuthorization(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(expectedResponseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car").header("Authorization",
                "Token token");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(expectedResponseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void FailingAllCarsWithAuthorizationServiceTest() throws Exception {
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.FORBIDDEN);

        Mockito.when(mainController.getAllCarsWithAuthorization(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(expectedResponseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car").header("Authorization",
                "No token");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(expectedResponseEntity.getStatusCodeValue(), response.getStatus());
    }


    @Test
    public void AddNewCarAsJSONObjectPostServiceTest() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "Sedan");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "Automatic");
        String expectedString = "Saved";

        Mockito.when(mainController.addNewCar(Mockito.any(Car.class))).thenReturn(expectedString);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(expectedString, response.getContentAsString());
    }

    @Test
    public void EchoCarServiceTest() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "Sedan");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "Automatic");

        String expectedString = "{\n" +
                "\t\"make\": \"Volvo\",\n" +
                "\t\"model\": \"V60\",\n" +
                "\t\"carType\": \"Sedan\",\n" +
                "\t\"seats\": 5,\n" +
                "\t\"fuelUsage\": 5.4,\n" +
                "\t\"gearbox\": \"Automatic\"\n" +
                "}";

        Car mockCar = new Car("Volvo", "V60", CarType.Sedan, 5, 5.4, Gearbox.Automatic);

        Mockito.when(
                mainController.echoCar(Mockito.any(Car.class))
        ).thenReturn(mockCar);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/echo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedString, response.getContentAsString(), false);

    }

    @Test
    public void CarCreationWithEmptyConstructor() {
        Car car = new Car();
        car.setId(1L);
        car.setMake("Volvo");
        car.setModel("V60");
        car.setCarType(CarType.Sedan);
        car.setSeats(5);
        car.setFuelUsage(5.4);
        car.setGearbox(Gearbox.Automatic);

        String expectedCarToString = "Car{make='Volvo', model='V60', carType=Sedan, seats=5, fuelUsage=5.4, gearbox=Automatic}";

        String carToString = car.toString();

        Assert.assertEquals(carToString, expectedCarToString);
    }

    @Test
    public void CarCreationWithParameterConstructor() {
        Car car = new Car("Volvo", "V60", CarType.Sedan, 5, 5.4, Gearbox.Automatic);
        String expectedCarToString = "Car{make='Volvo', model='V60', carType=Sedan, seats=5, fuelUsage=5.4, gearbox=Automatic}";

        String carToString = car.toString();

        Assert.assertEquals(carToString, expectedCarToString);
    }

    @Test
    public void contextLoads() {
    }


}
