package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by anitailieva on 12/10/2016.
 */
public class LoginPageObject extends PageObject{

    public LoginPageObject(WebDriver driver) {
        super(driver);

        assertEquals("Login", driver.getTitle());
        assertFalse(isLoggedIn());

    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Login");
    }

    public HomePageObject doLogin(String user, String password){

        setText("loginForm:username", user);
        setText("loginForm:password", password);
        getDriver().findElement(By.id("loginForm:loginButton")).click();

        if(isOnPage()){
            return null;
        }else {
            return new HomePageObject(getDriver());
        }
    }

    public HomePageObject doCancel(){
        getDriver().findElement(By.id("cancel")).click();
        waitForPageToLoad();
        return new HomePageObject(getDriver());
    }
}
