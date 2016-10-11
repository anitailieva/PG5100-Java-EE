package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.WebDriver;

/**
 * Created by anitailieva on 11/10/2016.
 */
public class HomePageObject extends PageObject {

    public HomePageObject(WebDriver driver){
    super(driver);
    }

    public boolean isOnPage() {
        return driver.getTitle().equals("Event List Home Page");
    }
    public HomePageObject toStartingPage(){
        driver.get("localhost:8080/exam_example/home.jsf");
        waitForPageToLoad();
        return this;
    }
}
