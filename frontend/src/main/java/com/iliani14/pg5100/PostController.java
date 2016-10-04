package com.iliani14.pg5100;

import com.iliani14.pg5100.entities.Post;
import com.iliani14.pg5100.entities.User;
import com.iliani14.pg5100.services.PostEJB;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by anitailieva on 28/09/16.
 */
@Named
@RequestScoped
public class PostController implements Serializable {

    @Inject
    private PostEJB postEJB;

    private String formText;
    private String formTitle;


    public String doPostText(User user){
        Post posted = postEJB.createNewPost(user, formTitle, formText);
        if(posted != null){
            formText = "";
            formTitle = "";
        }
        return "home.jsf";
    }
    public List<Post> getAllPosts(){
        return postEJB.getAllPosts();
    }

    public String deletePost(long id){
        postEJB.deletePost(id);
        return "home.jsf";
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }




}