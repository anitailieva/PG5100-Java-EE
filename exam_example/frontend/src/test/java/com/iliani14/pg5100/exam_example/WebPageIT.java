package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.CreateEventPageObject;
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

    @Test
    public void testCreateOneEvent() {
        createAndLogNewUser("username", "name", "surname", "Norway");
        hpo.setShowOnlyOwnCountry(true);

        int n = hpo.getNumberOfDisplayedEvents();
        String title = "title";

        CreateEventPageObject create = hpo.goToCreateEvent();
        hpo = create.createEvent(title, "Text", "Norway", "Oslo");
        assertNotNull(hpo);
        assertEquals(n+1, hpo.getNumberOfDisplayedEvents());
        assertTrue(getPageSource().contains(title));
    }

    @Test
    public void testCreateEventInDifferentCountries(){
        String country1 = "Norway";
        String country2 = "United States";

        createAndLogNewUser("user1", "name1", "lastname1", country1);

        CreateEventPageObject create = hpo.goToCreateEvent();
        hpo = create.createEvent("Concert", "On Saturday", country1, "Oslo");
        create = hpo.goToCreateEvent();
        hpo = create.createEvent("Party", "Bring alcohol", country2, "New York");

        hpo.setShowOnlyOwnCountry(false);
        int onlyOff = hpo.getNumberOfDisplayedEvents();
        assertEquals(onlyOff, hpo.getNumberOfDisplayedEvents());

        hpo.setShowOnlyOwnCountry(true);
        int onlyOn = hpo.getNumberOfDisplayedEvents();
        assertEquals(onlyOn, hpo.getNumberOfDisplayedEvents());
    }

    @Test
    public void testCreateEventsFromDifferenUsers(){
        String user1 = "me";
        createAndLogNewUser(user1, "one", "one", "Norway");
        CreateEventPageObject event1 = hpo.goToCreateEvent();
        hpo = event1.createEvent("Title", "Some text", "Norway", "Oslo");
        hpo.logout();

        String user2 = "you";
        createAndLogNewUser(user2, "two", "two", "Germany");
        CreateEventPageObject event2 = hpo.goToCreateEvent();
        hpo = event2.createEvent("Event", "Text", "Germany", "Berlin");

        hpo.setShowOnlyOwnCountry(false);
        int n = hpo.getNumberOfDisplayedEvents();

        assertEquals(n, hpo.getNumberOfDisplayedEvents());
        assertTrue(getPageSource().contains(user1));
        assertTrue(getPageSource().contains(user2));


    }

    @Test
    public void testCreateAndAttendEvent(){

    }

    @Test
    public void testTwoUsersAttendingSameEvent(){

    }

}
