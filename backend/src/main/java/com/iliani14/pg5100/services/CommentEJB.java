package com.iliani14.pg5100.services;

import com.iliani14.pg5100.entities.Comment;
import com.iliani14.pg5100.entities.Post;
import com.iliani14.pg5100.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;



/**
 * Created by anitailieva on 29/09/16.
 */
@Stateless
public class CommentEJB {

    @PersistenceContext
    private EntityManager em;


    @EJB
    private PostEJB postEJB;

    @EJB
    private UserEJB userEJB;

    public CommentEJB(){

    }


    public Comment createNewComment(@NotNull User user, @NotNull Post post, @NotNull String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreationDate(new Date());
        em.persist(comment);

        User u = userEJB.findUserById(user.getId());
        Post p = postEJB.findPostById(post.getId());
        u.getComments().add(comment);
        p.getComments().add(comment);

        return comment;
    }


    public List<Comment> getAllComments(){
        Query query = em.createQuery("select c from Comment c");
        List<Comment> comments = query.getResultList();

        return comments;
    }

    public long numberOfComments(){
        Query query = em.createNamedQuery(User.COUNT_ALL_COMMENTS);
        List<Long> numberOfComments = query.getResultList();

        return numberOfComments.get(0);
    }

    public Comment addCommentToComment(@NotNull User user, @NotNull Comment com, @NotNull String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreationDate(new Date());

        em.persist(comment);

        User u = em.find(User.class, user.getId());
        Comment comment1 = em.find(Comment.class, com.getId());
        u.getComments().add(comment);
        comment1.getComments().add(comment);

        return comment;
    }

    public void addVoteToComment(long id, int vote){
        Comment comment = em.find(Comment.class, id);
        if(vote > 0) {
            comment.setUpVotes(comment.getUpVotes() + 1);
        } else {
            comment.setDownVotes(comment.getDownVotes() + 1);
        }
    }
    public void deleteComment(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }

    }

    public Comment findCommentById(long id){
      return em.find(Comment.class, id);

    }

    public List<Comment> getMostRecentComments(int max){

        Query query = em.createQuery("select c from Comment c order by c.date DESC ");
        query.setMaxResults(max);

        return query.getResultList();
    }

}