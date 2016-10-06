package com.iliani14.pg5100.entities;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anitailieva on 05/09/16.
 */

@NamedQueries({

        @NamedQuery(name = User.COUNT_ALL_USERS, query = "select count(*) from User u"),

        @NamedQuery(name = User.COUNT_ALL_POSTS, query = "select count(*) from Post p"),

        @NamedQuery(name = User.COUNT_ALL_COMMENTS, query = "select count(*) from Comment c"),

        @NamedQuery(name = User.GET_COUNTRIES, query = "select u.country from User u group by u.country"),

        @NamedQuery(name = User.COUNT_USERS_FROM_COUNTRY, query = "select count(*) from User u " +
                "where u.country = :country"),

        @NamedQuery(name = User.GET_TOP_USERS, query = "select u from User u order by posts.size+comments.size DESC "),

        @NamedQuery(name = User.GET_POSTS_FROM_COUNTRY, query = "select sum(u.posts.size) from User u where u.country = :country"),

        @NamedQuery(name = User.GET_USER_BY_USERNAME, query ="select u from User u  WHERE userId = :userId")

})
@Entity
public class User {

    public static final String COUNT_ALL_USERS = "COUNT ALL USERS";
    public static final String COUNT_ALL_POSTS = "COUNT ALL POSTS";
    public static final String COUNT_ALL_COMMENTS = "COUNT ALL COMMENTS";
    public static final String GET_COUNTRIES = "GET ALL COUNTRIES";
    public static final String COUNT_USERS_FROM_COUNTRY = "COUNT USERS FROM COUNTRY";
    public static final String GET_TOP_USERS = "GET TOP USERS WITH MOST POSTS/COMMENTS";
    public static final String GET_POSTS_FROM_COUNTRY = "GET POSTS FROM COUNTRY";
    public static final String GET_USER_BY_USERNAME = "GET USER BY USERNAME";

    @Id @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 2,max = 120)
    private String firstName;


    @Size (min = 2,max = 120)
    private String lastName;


    @Size(min = 2, max = 120)
    private String address;

    @NotNull
    @Pattern(regexp ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;


    @Size(min = 2, max = 120)
    private String city;

    @NotNull
    @Size(min = 2, max = 30)
    private String userId;

    @NotNull
    private String hash;

    @NotNull
    @Size(max = 26)
    private String salt;

    @Enumerated(EnumType.STRING)
    private Countries country;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Post> posts;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comments;

    public User(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts(){
        if(posts == null){
            posts = new ArrayList<>();
        }
        return posts;
    }
    public void setPost(List<Post> posts) {
        this.posts = posts;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Comment> getComments(){
        if(comments == null){
            comments = new ArrayList<>();
        }
        return comments;
    }

    public void setComments( List<Comment> comments) {
        this.comments = comments;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public Countries getCountry(){
        return country;
    }

    public void setCountry(Countries country){
        this.country = country;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean equals(Object other)
    {
        return other instanceof User && (userId != null) ? userId.equals(((User) other).userId) : (other == this);
    }

    public int hashCode()
    {
        return userId != null ? this.getClass().hashCode() + userId.hashCode() : super.hashCode();
    }

    public String toString()
    {
        return "User" + userId;
    }

}