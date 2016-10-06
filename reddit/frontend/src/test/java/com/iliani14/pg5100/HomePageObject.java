package com.iliani14.pg5100;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by anitailieva on 03/10/2016.
 */
public class HomePageObject extends PageObject{

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Welcome to your profile page!");
    }

    public void toStartingPage() {
        getDriver().get(getBaseUrl() + "/users/home.jsf");
        waitForPageToLoad();
        doLogout();
    }



    public boolean isLoggedIn(){
        /*
            When dealing with an element that might be missing, should use "findElements"
            rather than "findElement", as this latter does throw an exception if the
            element is missing.
         */
        List<WebElement> elements = getDriver().findElements(By.id("logoutForm:logoutButton"));
        return ! elements.isEmpty();
    }

    public boolean isLoggedOut(){
        List<WebElement> elements = getDriver().findElements(By.id("loginButton"));
        return ! elements.isEmpty();
    }

    public void doLogout(){
        if(isLoggedIn()){
            WebElement logout =  getDriver().findElement(By.id("logoutForm:logoutButton"));
            logout.click();
            waitForPageToLoad();
        }
    }
    public LoginPageObject doLogin(){

        if(isLoggedIn()){
            return null;
        }

        WebElement login =  getDriver().findElement(By.id("loginButton"));
        login.click();
        waitForPageToLoad();

        LoginPageObject po = new LoginPageObject(getDriver());
        return po;
    }


    public int getNumberOfPosts(){

        return getDriver()
                .findElements(By.xpath("//div[@id = 'posts']//div[@class = 'post']"))
                .size();
    }

    public int getNumberOfPostsThatCanBeDeleted(){

        return getDriver()
                .findElements(By.xpath("//div[@id = 'posts']//div[@class = 'post']//div[@class = 'deleteButton']"))
                .size();
    }
    public boolean createPost( String text){
        List<WebElement> input = getDriver().findElements(By.id("postForm:postText"));
        if(input.isEmpty()){
            return false;
        }

        List<WebElement> create = getDriver().findElements(By.id("postForm:createPost"));
        if(create.isEmpty()){
            return false;
        }

        input.get(0).clear();
        input.get(0).sendKeys(text);
        create.get(0).click();
        waitForPageToLoad();
        return true;
    }


}
