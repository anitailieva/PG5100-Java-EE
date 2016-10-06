package com.iliani14.pg5100.exam_example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anitailieva on 06/10/2016.
 */
@Entity
public class Event {


    @Id @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 40)
    private String title;

    @NotNull
    @Size(min = 1, max = 1024)
    private String text;

    private Country country;

    @NotNull
    @Size(min = 1, max = 40)
    private String author;

    @NotNull
    @Size(min = 1, max = 40)
    private String location;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> attendingUsers;


    public List<User> getAttendingUsers(){
        if(attendingUsers == null){
            return new ArrayList<>();
        }
        return attendingUsers;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
    public Country getCountry(){
        return country;
    }
    public void setCountry(Country country){
        this.country = country;
    }
}
