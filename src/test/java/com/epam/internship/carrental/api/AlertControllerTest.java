package com.epam.internship.carrental.api;

import com.epam.internship.carrental.service.alert.AlertServiceImpl;
import com.epam.internship.carrental.service.search.Search;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@EnableWebMvc
@EnableSpringDataWebSupport
@WebMvcTest(value = AlertController.class, secure = false)
public class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertServiceImpl alertService;

    private JSONObject searchJSONObject;
    private JSONObject userJSONObject;

    @Before
    public void initialize() throws Exception {
        userJSONObject = new JSONObject();
        userJSONObject.put("userEmail","email@email.com");

        searchJSONObject = new JSONObject();
        searchJSONObject.put("user", userJSONObject);
        searchJSONObject.put("searchedMake", "Dacia");
        searchJSONObject.put("searchedModel", "Logan");
        searchJSONObject.put("searchedCarType", "SEDAN");
        searchJSONObject.put("searchedSeats", 5);
        searchJSONObject.put("searchedFuelUsage", 12.2);
        searchJSONObject.put("searchedGearbox", "MANUAL");
    }

    @Test
    public void subscribeToSearch() throws Exception {
        this.mockMvc.perform(post("/api/v1/alert/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchJSONObject.toString()))
                .andExpect(status().isOk());
        verify(alertService, times(1))
                .subscribeUser(Mockito.any(Search.class));
    }
}