package com.epam.internship.carrental.car;

import com.epam.internship.carrental.car.enums.CarGearbox;
import com.epam.internship.carrental.car.enums.CarType;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = CarController.class, secure = false)
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @Test
    public void AddNewCarAsParamsGET() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "SEDAN");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "AUTOMATIC");

        Mockito.when(carService.addNewCar(
                Mockito.anyString(), Mockito.anyString(), Mockito.any(CarType.class),
                Mockito.anyInt(), Mockito.anyDouble(), Mockito.any(CarGearbox.class)))
                .thenReturn(new ResponseEntity(HttpStatus.OK));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/add")
                .param("make", "Volvo")
                .param("model", "V60")
                .param("carType", "SEDAN")
                .param("seats", "5")
                .param("fuelUsage", "5.4")
                .param("carGearbox", "AUTOMATIC");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void AddNewCarPOST() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "SEDAN");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "AUTOMATIC");

        Mockito.when(carService.addNewCar(Mockito.any(Car.class))).thenReturn(new ResponseEntity(HttpStatus.OK));

        RequestBuilder requestBuilder = post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void GetCarsByMakeGET() throws Exception {
        String expectedString = "[{\"id\":null,\"make\":\"Volvo\",\"model\":\"V60\",\"carType\":\"SEDAN\",\"seats\":5,\"fuelUsage\":5.4,\"gearbox\":\"AUTOMATIC\"}]";

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        Iterable<Car> iterable = Collections.singletonList(mockCar);

        ResponseEntity<Iterable<Car>> responseEntity = new ResponseEntity<>(iterable, HttpStatus.OK);

        Mockito.when(carService.getCarsByMake(Mockito.anyString())).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/search")
                .param("make", "Volvo");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(expectedString, response.getContentAsString());
    }

    @Test
    public void GetAllCarsGET() throws Exception {
        String expectedString = "[{\"id\":null,\"make\":\"Volvo\",\"model\":\"V60\",\"carType\":\"SEDAN\",\"seats\":5,\"fuelUsage\":5.4,\"gearbox\":\"AUTOMATIC\"}]";

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        Iterable<Car> iterable = Collections.singletonList(mockCar);
        ResponseEntity<Iterable<Car>> responseEntity = new ResponseEntity<>(iterable, HttpStatus.OK);

        Mockito.when(carService.getAllCars()).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/all");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(expectedString, response.getContentAsString());
    }

    @Test
    public void GetAllCarsPageableGET() throws Exception {
        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        List<Car> list = Collections.singletonList(mockCar);
        Page<Car> page = new PageImpl<>(list);
        ResponseEntity<Page<Car>> responseEntity = new ResponseEntity<>(page, HttpStatus.OK);

        Mockito.when(carService.getAllCars(Mockito.any(Pageable.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/all/pages")
                .param("page", "0");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void EchoCarPOST() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "SEDAN");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "AUTOMATIC");

        String expectedString = "{\n" +
                "\t\"make\": \"Volvo\",\n" +
                "\t\"model\": \"V60\",\n" +
                "\t\"carType\": \"SEDAN\",\n" +
                "\t\"seats\": 5,\n" +
                "\t\"fuelUsage\": 5.4,\n" +
                "\t\"gearbox\": \"AUTOMATIC\"\n" +
                "}";

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();

        Mockito.when(
                carService.echoCar(Mockito.any(Car.class))
        ).thenReturn(new ResponseEntity<>(mockCar, HttpStatus.OK));

        RequestBuilder requestBuilder = post("/api/v1/echo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expectedString, response.getContentAsString(), false);

    }

    @Test
    public void GetAllCarsWithAuthorizationGET() throws Exception {
        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        List<Car> list = Collections.singletonList(mockCar);
        Page<Car> page = new PageImpl<>(list);
        ResponseEntity<Page<Car>> responseEntity = new ResponseEntity<>(page, HttpStatus.OK);

        Mockito.when(carService.getAllCarsWithAuthorization(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car").header("Authorization",
                "Token token");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(responseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void GetCarByIdWithAuthorizationGET() throws Exception {
        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        Optional<Car> optionalCar = Optional.of(mockCar);
        ResponseEntity<Optional<Car>> responseEntity = new ResponseEntity<>(optionalCar, HttpStatus.OK);

        Mockito.when(carService.getCarByIdWithAuthorization(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car/{carId}", 1)
                .header("Authorization", "Token token");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(responseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void InsertNewCarWithAuthorizationPUT() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("id", 1);
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "SEDAN");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "AUTOMATIC");

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        ResponseEntity<Car> responseEntity = new ResponseEntity<>(mockCar, HttpStatus.OK);

        Mockito.when(carService.insertNewCarWithAuthorization(Mockito.any(Car.class), Mockito.anyString()))
                .thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/car")
                .header("Authorization", "Token token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(responseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void UpdateCarByGivenParametersWithAuthorizationPOST() throws Exception {
        JSONObject exampleCarJson = new JSONObject();
        exampleCarJson.put("make", "Volvo");
        exampleCarJson.put("model", "V60");
        exampleCarJson.put("carType", "SEDAN");
        exampleCarJson.put("seats", 5);
        exampleCarJson.put("fuelUsage", 5.4);
        exampleCarJson.put("gearbox", "AUTOMATIC");

        JSONObject parametersToUpdate = new JSONObject();
        parametersToUpdate.put("model", "A");
        parametersToUpdate.put("carType", "SUV");

        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();

        ResponseEntity<Car> responseEntity = new ResponseEntity<>(mockCar, HttpStatus.OK);

        Mockito.when(carService.updateCarByGivenParametersWithAuthorization(
                Mockito.anyLong(), Mockito.any(Car.class), Mockito.anyString()))
                .thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1//updatecar/{carId}", 1)
                .header("Authorization", "Token token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parametersToUpdate.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(responseEntity.getStatusCodeValue(), response.getStatus());
    }

    @Test
    public void GetAllFreeCarsGET() throws Exception {
        Car mockCar = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        List<Car> list = Collections.singletonList(mockCar);
        Page<Car> page = new PageImpl<>(list);
        ResponseEntity<Page<Car>> responseEntity = new ResponseEntity<>(page, HttpStatus.OK);

        Mockito.when(carService.getAllCars(Mockito.any(Pageable.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/free")
                .param("page", "0");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void GetAllCarViewObjectGET() throws Exception {
        CarViewObject mockCarViewObject = CarViewObject.builder()
                .fullName("Volvo V60 SEDAN")
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        List<CarViewObject> list = Collections.singletonList(mockCarViewObject);
        Page<CarViewObject> page = new PageImpl<>(list);
        ResponseEntity<Page<CarViewObject>> responseEntity = new ResponseEntity<>(page, HttpStatus.OK);

        Mockito.when(carService.getAllCarViewObject(Mockito.any(Pageable.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/car/allCarVo")
                .param("page", "0");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void InsertNewCarFromViewObjectPUT() throws Exception {
        JSONObject exampleCarVOJson = new JSONObject();
        exampleCarVOJson.put("fullName", "Volvo V60 SEDAN");
        exampleCarVOJson.put("seats", 5);
        exampleCarVOJson.put("fuelUsage", 5.4);
        exampleCarVOJson.put("gearbox", "AUTOMATIC");

        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);

        Mockito.when(carService.insertNewCarFromViewObject(Mockito.any(CarViewObject.class))).thenReturn(responseEntity);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/car/carVO")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCarVOJson.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void CarCreationWithEmptyConstructor() {
        Car car = Car.builder()
                .make("Volvo")
                .model("V60")
                .carType(CarType.SEDAN)
                .fuelUsage(5.4)
                .seats(5)
                .gearbox(CarGearbox.AUTOMATIC)
                .build();
        String expectedCarToString = "Car(id=null, make=Volvo, model=V60, carType=SEDAN, seats=5, fuelUsage=5.4, gearbox=AUTOMATIC)";

        String carToString = car.toString();

        Assert.assertEquals(expectedCarToString, carToString);
    }
}
