package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.HomePageObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
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

}