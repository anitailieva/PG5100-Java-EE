package com.iliani14.pg5100.exam_example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anitailieva on 06/10/2016.
 */
@Entity
public class User {

    @Id
    @Pattern(regexp = "[A-Za-z0-9]{1,32}")
    private String userId;

    @NotNull
    private String hash;

    @NotNull
    @Size(min = 1, max = 30)
    private String salt;

    @NotNull
    @Size(min = 1, max = 40)
    private String name;

    @Size(min = 1, max = 40)
    private String surname;

    @Enumerated(value = EnumType.ORDINAL)
    private Country country;


    @ManyToMany(mappedBy = "attendingUsers", fetch = FetchType.EAGER)
    private List<Event> eventList;

    public User(){

    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getHash(){
        return hash;
    }
    public void setHash(String hash){
        this.hash = hash;
    }
    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public Country getCountry(){
        return country;
    }
    public void setCountry(Country country){
        this.country = country;
    }
    public List<Event> getEvents(){
        if(eventList == null){
            return new ArrayList<>();
        }
        return eventList;
    }
}
