package com.epam.internship.carrental.api;


import com.epam.internship.carrental.service.search.SearchServiceImpl;
import org.json.JSONObject;
import org.junit.Before;
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
@WebMvcTest(value = SearchController.class, secure = false)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchServiceImpl searchService;

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

//    @Test
//    public void searchForCar() throws Exception {
//        this.mockMvc.perform(get("/api/v1/search/car")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(searchJSONObject.toString()))
//                .andExpect(status().isOk());
//        verify(searchService, times(1))
//                .searchCars(Mockito.any(Pageable.class),Mockito.any(Search.class));
//    }
}
