package com.epam.internship.carrental.api;

import com.epam.internship.carrental.service.reservation.ReservationServiceImpl;
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
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = ReservationController.class, secure = false)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationServiceImpl reservationService;

    private JSONObject reservationVOJSONObject;

    @Before
    public void initialize() throws Exception {
        Date date = new Date();

        reservationVOJSONObject = new JSONObject();
        reservationVOJSONObject.put("carId", 1);
        reservationVOJSONObject.put("startOfReservation", date.getTime());
        reservationVOJSONObject.put("endOfReservation", date.getTime());
    }

    @Test
    public void bookCarRental() throws Exception {
        this.mockMvc.perform(put("/api/v1/reservation/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationVOJSONObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void endCarRentalWithAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/reservation/end/{id}", 1)
                .header("Authorization",
                        "Token employee_token"))
                .andExpect(status().isOk());
    }

    @Test
    public void endCarRentalWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/reservation/end/{id}", 1)
                .header("Authorization",
                        "Wrong employee_token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void modifyCarRentalWithAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/reservation/modify/{id}", 1)
                .header("Authorization",
                        "Token employee_token")

                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationVOJSONObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void modifyCarRentalWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(post("/api/v1/reservation/modify/{id}", 1)
                .header("Authorization",
                        "Wrong employee_token")

                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationVOJSONObject.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void listAllCarRentalWithAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/reservation/reservations")
                .header("Authorization",
                        "Token employee_token"))
                .andExpect(status().isOk());
    }

    @Test
    public void listAllCarRentalWithWrongAuthorization() throws Exception {
        this.mockMvc.perform(get("/api/v1/reservation/reservations")
                .header("Authorization",
                        "Wrong employee_token"))
                .andExpect(status().isForbidden());
    }
}