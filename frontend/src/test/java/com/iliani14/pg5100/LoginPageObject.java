package com.iliani14.pg5100;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by anitailieva on 03/10/2016.
 */
public class LoginPageObject extends PageObject{


    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Login / Create New User");
    }

    public HomePageObject logInUser(String userId, String password){

        WebElement id = getDriver().findElement(By.id("loginForm:userId"));
        WebElement pwd = getDriver().findElement(By.id("loginForm:password"));
        WebElement login = getDriver().findElement(By.id("loginForm:logIn"));

        id.clear();
        id.sendKeys(userId);
        pwd.clear();
        pwd.sendKeys(password);
        login.click();
        waitForPageToLoad();

        HomePageObject po = new HomePageObject(getDriver());
        return po;
    }
}
