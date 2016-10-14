package com.iliani14.pg5100.exam_example.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by anitailieva on 14/10/2016.
 */
public class CreateUserPageObject extends PageObject {



    public CreateUserPageObject(WebDriver driver){
        super(driver);
    }
    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().equals("Create User");
    }

    public HomePageObject createUser(String userId, String password, String confirmPassword, String name, String surname, String country){

        setText("createUserForm:username", userId);
        setText("createUserForm:password", password);
        setText("createUserForm:confirmPassword", confirmPassword);
        setText("createUserForm:name", name);
        setText("createUserForm:surname", surname);


        try {
            new Select(getDriver().findElement(By.id("createUserForm:country"))).selectByVisibleText(country);
        }catch (Exception e){
            return null;
        }

        getDriver().findElement(By.id("createUserForm:createButton")).click();
        waitForPageToLoad();

        if(isOnPage()){
            return null;
        }else{
            return new HomePageObject(getDriver());
        }

    }

}
