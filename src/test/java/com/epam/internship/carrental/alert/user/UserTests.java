package com.epam.internship.carrental.alert.user;

import org.junit.Assert;
import org.junit.Test;

public class UserTests {

    @Test
    public void userCreationTest(){
        User user = new User();
        Assert.assertNotNull(user);
    }

    @Test
    public void userBuilderTest(){
        User user = User.builder().userEmail("ecet@sav.com").build();
        Assert.assertEquals("ecet@sav.com",user.getUserEmail());
    }


}
