package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.WebDriver;

/**
 * Created by anitailieva on 11/10/2016.
 */
public class HomePageObject extends PageObject {


    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public HomePageObject toStartingPage() {
        String context = "/exam_example";
        driver.get("localhost:8080" + context + "/home.jsf");
        waitForPageToLoad();

        return this;
    }

    public boolean isOnPage() {
        return driver.getTitle().equals("Event List Home Page");
    }

}
