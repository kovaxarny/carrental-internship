package com.epam.internship.carrental.alert.search;

import com.epam.internship.carrental.alert.user.User;
import com.epam.internship.carrental.car.enums.CarType;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests {

    @Test
    public void searchCreationTest(){
        Search search = new Search();
        Assert.assertNotNull(search);
    }

    @Test
    public void searchBuilderTest(){
        Search search = Search.builder()
                .user(User.builder()
                        .userEmail("ecet@ecet.com")
                        .build())
                .searchedCarType(CarType.SPORT)
                .build();
        Assert.assertEquals("ecet@ecet.com",search.getUser().getUserEmail());
        Assert.assertEquals(CarType.SPORT,search.getSearchedCarType());
    }
}
