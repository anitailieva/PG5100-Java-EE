package com.iliani15.pg5100.exam_example.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anitailieva on 07/10/2016.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull @Size(min=1, max=50)
    private String title;

    @NotNull @Size(min=1, max=1024)
    private String text;

    @NotNull @Size(min=1, max=32)
    private String author;

    @Enumerated(value = EnumType.ORDINAL)
    private String country;

    @NotNull @Size(min=1, max=256)
    private String location;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> attendingUsers;


    public void setAttendingUsers(List<User> attendingUsers) {
        this.attendingUsers = attendingUsers;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<User> getAttendingUsers() {
        if(attendingUsers == null){
            return new ArrayList<>();
        }
        return attendingUsers;
    }


}
