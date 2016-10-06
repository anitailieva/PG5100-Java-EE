package com.iliani14.pg5100;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 03/10/2016.
 */
public class UserIT extends SeleniumTestBase{

    private HomePageObject po;

    @Before
    public void toStartingPage() {
        po = new HomePageObject(getDriver());
        po.toStartingPage();
    }


    @Test
    public void testHomePage() {
        assertTrue(po.isOnPage());
        assertTrue(po.isLoggedOut());
    }



}

