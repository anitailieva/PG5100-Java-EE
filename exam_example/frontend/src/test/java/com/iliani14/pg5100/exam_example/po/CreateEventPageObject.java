package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;

/**
 * Created by anitailieva on 16/10/2016.
 */
public class CreateEventPageObject extends PageObject {

    public CreateEventPageObject(WebDriver driver){
        super(driver);

        assertEquals("Create New Event", driver.getTitle());

    }
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Create New Event");
    }

    public HomePageObject createEvent(String title, String description, String country, String location){
        setText("createEventForm:title",title);
        setText("createEventForm:description",description);
        setText("createEventForm:location",location);


        new Select(getDriver().findElement(By.id("createEventForm:country"))).selectByVisibleText(country);

        getDriver().findElement(By.id("createEventForm:createButton")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        } else {
            return new HomePageObject(getDriver());
        }
    }

}
