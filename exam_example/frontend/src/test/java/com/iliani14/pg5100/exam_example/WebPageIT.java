package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.CreateUserPageObject;
import com.iliani14.pg5100.exam_example.po.HomePageObject;
import com.iliani14.pg5100.exam_example.po.LoginPageObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anitailieva on 11/10/2016.
 */

public class WebPageIT extends WebTestBase {

    @Before
    public void toStartingPage() {
        hpo = new HomePageObject(getDriver());
        hpo.toStartingPage();
        hpo.logout();
        assertTrue(hpo.isOnPage());
        assertFalse(hpo.isLoggedIn());

    }

    @Test
    public void testHomePage() {
        hpo.toStartingPage();
        assertTrue(hpo.isOnPage());

    }

    @Test
    public void testLoginLink() {
        LoginPageObject loginPageObject = hpo.goToLogin();
        assertTrue(loginPageObject.isOnPage());
    }

    @Test
    public void testLoginWrongUser(){
        LoginPageObject lo = hpo.goToLogin();
        HomePageObject ho =  lo.doLogin("name", "Pass");
        assertNull(ho);
        assertTrue(lo.isOnPage());
    }

    @Test
    public void testCreateUserFailDueToPasswordMismatch(){
        LoginPageObject login = hpo.goToLogin();
        CreateUserPageObject create = login.createNewUser();

        HomePageObject home = create.createUser("user", "pass", "password", "name", "surname", "Bulgaria");
        assertNull(home);
        assertTrue(create.isOnPage());

    }

    @Test
    public void testCreateValidUser(){
        LoginPageObject loginPageObject = hpo.goToLogin();
        CreateUserPageObject user = loginPageObject.createNewUser();
        assertTrue(user.isOnPage());

        String userId = "user";
        HomePageObject home = user.createUser(userId, "password", "password", "name", "surname", "United States");
        assertNotNull(home);
        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn(userId));

        home.logout();
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testLogin(){
        createAndLogNewUser("userId", "name", "surname", "Norway");
        hpo.logout();
        assertFalse(hpo.isLoggedIn());

        LoginPageObject login = hpo.goToLogin();
        HomePageObject home = login.doLogin("userId", "pass");
        assertNotNull(home);
        assertTrue(home.isOnPage());
        assertTrue(home.isLoggedIn());
    }
}
