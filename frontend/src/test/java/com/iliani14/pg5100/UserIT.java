package com.iliani14.pg5100;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 03/10/2016.
 */
public class UserIT extends SeleniumTestBase{

    private HomePageObject pageObject;


    @Before
    public void toStartingPage(){
        pageObject = new HomePageObject(getDriver());
        pageObject.toStartingPage();
    }

    @Test
    public void testHomePage(){
        assertTrue(pageObject.isOnPage());
        assertTrue(pageObject.isLoggedOut());
    }

    @Test
    public void testCreateUser(){
        LoginPageObject loginPageObject = pageObject.doLogin();
        assertTrue(loginPageObject.isOnPage());
        assertFalse(pageObject.isOnPage());

        String user = "name";
        String pass = "password";

        pageObject = loginPageObject.logInUser(user, pass);
        assertFalse(loginPageObject.isOnPage());
        assertTrue(pageObject.isOnPage());


        assertTrue(getDriver().getPageSource().contains(user));
        assertTrue(pageObject.isLoggedIn());
    }


    }

