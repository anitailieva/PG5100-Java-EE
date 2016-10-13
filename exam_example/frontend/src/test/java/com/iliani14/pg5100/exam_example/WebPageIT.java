package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.HomePageObject;
import com.iliani14.pg5100.exam_example.po.LoginPageObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 11/10/2016.
 */

public class WebPageIT extends WebTestBase {
    private HomePageObject hp;

    @Before
    public void toStartingPage() {
        hp = new HomePageObject(getDriver());
        hp.toStartingPage();
        hp.logout();
        assertTrue(hp.isOnPage());
        assertFalse(hp.isLoggedIn());

    }

    @Test
    public void testHomePage() {
        hp.toStartingPage();
        assertTrue(hp.isOnPage());

    }

    @Test
    public void testLoginLink() {
        LoginPageObject loginPageObject = hp.goToLogin();
        assertTrue(loginPageObject.isOnPage());
    }

    @Test
    public void testLoginWrongUser(){
        LoginPageObject lo = hp.goToLogin();
        HomePageObject ho =  lo.doLogin("name", "Pass");
        assertNull(ho);
        assertTrue(lo.isOnPage());
    }

}
