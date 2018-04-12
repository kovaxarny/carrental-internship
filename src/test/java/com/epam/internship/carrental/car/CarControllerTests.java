package com.epam.internship.carrental.car;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = CarController.class, secure = false)
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    private JSONObject carViewJSONObject;

    @Before
    public void initialize() throws Exception {
        carViewJSONObject = new JSONObject();
        carViewJSONObject.put("fullName", "Dacia Logan SEDAN");
        carViewJSONObject.put("seats", 5);
        carViewJSONObject.put("fuelUsage", 12.2);
        carViewJSONObject.put("gearbox", "MANUAL");
    }

    @Test
    public void addNewCar() throws Exception {
        this.mockMvc.perform(post("/api/v1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarsByMake() throws Exception {
        this.mockMvc.perform(get("/api/v1/search?make={make}", "Dacia"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCars() throws Exception {
        this.mockMvc.perform(get("/api/v1/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCarsWithPaging() throws Exception {
        this.mockMvc.perform(get("/api/v1/all/pages?page={page}", 0))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCarsWithAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/car?page={page}", 0)
                .header("Authorization",
                        "Token token"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCarsWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/car?page={page}", 0)
                .header("Authorization",
                        "Wrong Token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCarByIdWithAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/car/{carId}", 1)
                .header("Authorization",
                        "Token token"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCarByIdWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/car/{carId}", 1)
                .header("Authorization",
                        "Wrong token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void insertNewCarWithAuthorization() throws Exception {
        this.mockMvc.perform(put("/api/v1/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString())
                .header("Authorization",
                        "Token token"))
                .andExpect(status().isOk());
    }

    @Test
    public void insertNewCarWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(put("/api/v1/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString())
                .header("Authorization",
                        "Wrong token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateCarByGivenParametersWithAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/updatecar/{carId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString())
                .header("Authorization",
                        "Token token"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCarByGivenParametersWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/updatecar/{carId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString())
                .header("Authorization",
                        "Wrong token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllFreeCars() throws Exception {
        this.mockMvc.perform(get("/api/v1/free"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCarViewObject() throws Exception {
        this.mockMvc.perform(get("/api/v1/car/allCarVo?page={page}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void insertNewCarFromViewObject() throws Exception {

        this.mockMvc.perform(put("/api/v1/car/carVO")
                .contentType(MediaType.APPLICATION_JSON)
                .content(carViewJSONObject.toString()))
                .andExpect(status().isOk());
    }
}
