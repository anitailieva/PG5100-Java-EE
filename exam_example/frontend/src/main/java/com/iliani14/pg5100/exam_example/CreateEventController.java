package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.ejb.EventEJB;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by anitailieva on 15/10/2016.
 */
@Named
@RequestScoped
public class CreateEventController implements Serializable{

    private String formTitle;
    private String formDescription;
    private String formCountry;
    private String formLocation;

    @EJB
    private EventEJB eventEJB;

    @Inject
    private LoggingController loggingController;

    public CreateEventController(){
    }

    public String createEvent(){

        try{
            eventEJB.createEvent(formTitle, formDescription, formCountry, formLocation, loggingController.getRegisteredUser());
        }catch (Exception e){
            return "newEvent.jsf";
        }
        return "home.jsf";
    }

    public String getFormTitle(){
        return formTitle;
    }
    public void setFormTitle(String formTitle){
        this.formTitle = formTitle;
    }
    public String getFormDescription(){
        return formDescription;
    }
    public void setFormDescription(String formDescription){
        this.formDescription = formDescription;
    }
    public String getFormCountry(){
        return formCountry;
    }
    public void setFormCountry(String formCountry){
        this.formCountry = formCountry;
    }
    public String getFormLocation(){
        return formLocation;
    }
    public void setFormLocation(String formLocation){
        this.formLocation = formLocation;
    }
}
