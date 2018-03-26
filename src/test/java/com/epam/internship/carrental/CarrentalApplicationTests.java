package com.epam.internship.carrental;

import com.epam.internship.carrental.car.CarController;
import com.epam.internship.carrental.car.CarServiceImpl;
import com.epam.internship.carrental.car.enums.CarType;
import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.Car;
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
@WebMvcTest(value = CarController.class, secure = false)
public class CarrentalApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;


    @Test
    public void AllCarsWithAuthorizationServiceTest() throws Exception {
        ResponseEntity expectedResponseEntity = new ResponseEntity(HttpStatus.OK);

        Mockito.when(carService.getAllCarsWithAuthorization(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(expectedResponseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car").header("Authorization",
                "Token token");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(expectedResponseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void FailingAllCarsWithAuthorizationServiceTest() throws Exception {
        ResponseEntity expectedResponseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Mockito.when(carService.getAllCarsWithAuthorization(Mockito.any(Pageable.class), Mockito.anyString()))
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

        Mockito.when(carService.addNewCar(Mockito.any(Car.class))).thenReturn(new ResponseEntity(HttpStatus.OK));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
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

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.Sedan)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.Automatic)
                .build();

        Mockito.when(
                carService.echoCar(Mockito.any(Car.class))
        ).thenReturn(new ResponseEntity<>(mockCar,HttpStatus.OK));

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
        Car car = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.Sedan)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.Automatic)
                .build();
        String expectedCarToString = "Car(id=null, make=Volvo, model=V60, carType=Sedan, seats=5, fuelUsage=5.4, gearbox=Automatic)";

        String carToString = car.toString();

        Assert.assertEquals(expectedCarToString, carToString);
    }

    @Test
    public void CarCreationWithParameterConstructor() {
        Car car = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.Sedan)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.Automatic)
                .build();
        String expectedCarToString = "Car(id=null, make=Volvo, model=V60, carType=Sedan, seats=5, fuelUsage=5.4, gearbox=Automatic)";

        String carToString = car.toString();

        Assert.assertEquals(expectedCarToString,carToString);
    }

    @Test
    public void contextLoads() {
    }


}
