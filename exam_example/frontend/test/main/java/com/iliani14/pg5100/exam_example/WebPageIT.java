package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.po.HomePageObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeTrue;

/**
 * Created by anitailieva on 11/10/2016.
 */

public class WebPageIT extends WebTestBase {

    @Before
    public void startFromInitialPage() {

        assumeTrue(JBossUtil.isJBossUpAndRunning());

        home = new HomePageObject(getDriver());
        home.toStartingPage();
        home.logout();
        assertTrue(home.isOnPage());
        assertFalse(home.isLoggedIn());
    }

    @Test
    public void testHomePage() {
        home.toStartingPage();
        assertTrue(home.isOnPage());
    }

}