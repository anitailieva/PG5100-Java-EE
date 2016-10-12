package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by anitailieva on 11/10/2016.
 */
public class HomePageObject extends PageObject {

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public void toStartingPage() {
        getDriver().get(getBaseUrl() + "home.jsf");
        waitForPageToLoad();
    }
    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Event List Home Page");
    }

    public LoginPageObject toLogin(){
        if(isLoggedIn()){
            logout();
        }

        getDriver().findElement(By.id("login")).click();
        waitForPageToLoad();
        return new LoginPageObject(getDriver());
    }

}
