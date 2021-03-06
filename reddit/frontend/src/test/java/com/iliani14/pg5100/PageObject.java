package com.iliani14.pg5100;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by anitailieva on 03/10/2016.
 */

public abstract class PageObject {

    private final WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    /*
        Is the browser currently on the page represented by this Page Object?
        We need a method to check if we are on the right page, eg to check we were
        not redirected to an error message page, or if we followed the wrong link
     */
    public abstract boolean isOnPage();

    protected WebDriver getDriver(){
        return driver;
    }


    public void setText(String id, String text){
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(text);
    }

    protected String getBaseUrl(){
        return "http://localhost:8080/reddit";
    }

    protected Boolean waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 10); //give up after 10 seconds

        //keep executing the given JS till it returns "true", when page is fully loaded and ready
        return wait.until((ExpectedCondition<Boolean>) input -> (
                (JavascriptExecutor) input).executeScript("return document.readyState").equals("complete"));
    }
}
