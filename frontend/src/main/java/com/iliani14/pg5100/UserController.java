package com.iliani14.pg5100;


import com.iliani14.pg5100.entities.Countries;
import com.iliani14.pg5100.entities.User;
import com.iliani14.pg5100.services.UserEJB;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by anitailieva on 27/09/16.
 */
@Named
@SessionScoped
public class UserController implements Serializable {

    @Inject
    private UserEJB userEJB;

    private User registeredUser;
    private String formUserName;
    private String formPassword;


    public UserController() {
    }


    public boolean isLoggedIn() {
        return registeredUser != null;
    }

    public User getRegisteredUser() {
        return registeredUser;
    }

    public String logOut() {
        registeredUser = null;
        return "home.jsf";
    }


    public String logIn() {
        boolean valid = userEJB.login(formUserName, formPassword);
        if (valid) {
            registeredUser = userEJB.getUser(formUserName);
            return "home.jsf";
        } else {
            return "login.jsf";
        }
    }

    public String registerNew() {
        User registered = userEJB.createNewUser(formUserName, formPassword, "aaa", "bbb", "aaa@live.com", Countries.Australia);
        if (registered != null) {
            registeredUser = registered;
            return "home.jsf";
        } else {
            return "register.jsf";
        }
    }

    public String getFormUserName() {
        return formUserName;
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getFormPassword() {
        return formPassword;
    }

    public void setFormPassword(String formPassword) {
        this.formPassword = formPassword;
    }
}
