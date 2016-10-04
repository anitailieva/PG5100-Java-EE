package com.iliani14.pg5100;


import com.iliani14.pg5100.entities.Comment;
import com.iliani14.pg5100.entities.Post;
import com.iliani14.pg5100.entities.User;
import com.iliani14.pg5100.services.CommentEJB;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by anitailieva on 30/09/16.
 */
@Named
@RequestScoped
public class CommentController implements Serializable {

    private String formText;


    @EJB
    private CommentEJB ejb;

    public void createNewComment(User user, Post post){
        ejb.createNewComment(user, post, formText);
    }

    public List<Comment> getMostRecentComments(int max){
        return ejb.getMostRecentComments(max);
    }

    public void deleteComment(Long id){
        ejb.deleteComment(id);
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }


}
