package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

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

    public LoginPageObject goToLogin(){
        if(isLoggedIn()){
            logout();
        }

        getDriver().findElement(By.id("login")).click();
        waitForPageToLoad();
        return new LoginPageObject(getDriver());
    }

    public CreateEventPageObject goToCreateEvent() {
        if (!isLoggedIn()) {
            return null;
        }

        getDriver().findElement(By.id("createEvent")).click();
        waitForPageToLoad();
        return new CreateEventPageObject(getDriver());
    }

    public int getNumberOfAttendees(String title){
        List<WebElement> elements = getDriver().findElements(By.xpath("\"//table[@id='eventTable']//tbody//tr[contains(td[2], '\" + title + \"')]/td[4]\""));

        if(elements.isEmpty()){
            return -1;
        }
        return  Integer.parseInt(elements.get(0).getText());
    }

    public int getNumberOfDisplayedEvents() {
        List<WebElement> elements = getDriver().findElements(
                By.xpath("//table[@id='eventTable']//tbody//tr[string-length(text()) > 0]"));

        return elements.size();
    }

    public void setShowOnlyOwnCountry(boolean value) {

        List<WebElement> elements = getDriver().findElements(By.id("showOnlyOwnCountryForm:showOnlyOwnCountry"));
        WebElement e = elements.get(0);

        if ((value && !e.isSelected()) || (!value && e.isSelected())) {
            e.click();
            waitForPageToLoad();
        }
    }

    public boolean isAttending(String title){
        List<WebElement> elements = getDriver().findElements(By.xpath("//table[@id='eventTable']//tbody//tr[contains(td[2], '" + title + "')]/td[5]/form/input[@type='checkbox' and @checked='checked']"));

        return !elements.isEmpty();
    }

    public void setAttendance(String title, boolean value){
        boolean alreadyAttending = isAttending(title);
        if((value && alreadyAttending)  || (!value && !alreadyAttending)){
            return;
        }
        List<WebElement> elements = getDriver().findElements(By.xpath("//table[@id='eventTable']//tbody//tr[contains(td[2], '" + title + "')]/td[5]/form/input[@type='checkbox']")
        );

        if(elements.isEmpty()){
            return;
        }

        elements.get(0).click();
        waitForPageToLoad();
    }


}
