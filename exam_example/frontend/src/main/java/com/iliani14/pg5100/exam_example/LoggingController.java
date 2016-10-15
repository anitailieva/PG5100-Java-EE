import com.iliani14.pg5100.exam_example.Countries;
import com.iliani14.pg5100.exam_example.ejb.UserEJB;
import com.iliani14.pg5100.exam_example.entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by anitailieva on 10/10/2016.
 */

@Named
@SessionScoped
public class LoggingController implements Serializable {


    @EJB
    private UserEJB userEJB;

    private String formUserName;
    private String formPassword;
    private String formConfirmPassword;
    private String formFirstName;
    private String formLastName;
    private String formCountry;
    private String registeredUser;


    public LoggingController(){

    }
    public String logIn() {
        boolean valid = userEJB.login(formUserName, formPassword);
        if (valid) {
            registeredUser = formUserName;
            return "home.jsf";
        } else {
            return "login.jsf";
        }
    }

    public String logOut(){
    registeredUser = null;
        return "home.jsf";
    }

    public String getRegisteredUser(){
        return registeredUser;
    }

    public List<String> getUserCountry() {
    return Countries.getCountries();
    }

    public String getCountry() {
        if (registeredUser == null) {
            return null;
        }
        return userEJB.getUser(registeredUser).getCountry();
    }

    public String registerNew(){
        if(! formPassword.equals(formConfirmPassword)){
            return "newUser.jsf";
        }
        boolean registered = userEJB.createUser(formUserName, formPassword, formFirstName, formLastName, formCountry);

        if(registered){
            registeredUser = formUserName;
            return "home.jsf";
        } else {
            return "newUser.jsf";
        }
    }

    public boolean isLoggedIn() {
        return registeredUser != null;
    }

    public User getUser() {
    return userEJB.getUser(registeredUser);
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

    public String getFormConfirmPassword() {
        return formConfirmPassword;
    }

    public void setFormConfirmPassword(String formConfirmPassword) {
        this.formConfirmPassword = formConfirmPassword;
    }

    public String getFormFirstName() {
        return formFirstName;
    }

    public void setFormFirstName(String formFirstName) {
        this.formFirstName = formFirstName;
    }

    public String getFormLastName() {
        return formLastName;
    }

    public void setFormLastName(String formLastName) {
        this.formLastName = formLastName;
    }

    public String getFormCountry() {
        return formCountry;
    }

    public void setFormCountry(String formCountry) {
        this.formCountry = formCountry;
    }
}


