package com.iliani14.pg5100.services;

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

@Stateless
public class PostEJB {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserEJB userEJB;

    public PostEJB(){}

    public Post createNewPost(@NotNull User user, @NotNull String title,
                              @NotNull String text){
        Post post = new Post();
        post.setTitle(title);
        post.setContent(text);
        post.setCreationDate(new Date());

        em.persist(post);

        User u = userEJB.findUserById(user.getId());
        u.getPosts().add(post);

        return post;
    }


    public Post findPostById(long id){
        return em.find(Post.class, id);

    }

    public User getPostAuthor(Post p){
        return p.getAuthor();
    }

    public long numberOfPosts(){
        Query query = em.createNamedQuery(User.COUNT_ALL_POSTS);
        List<Long> r = query.getResultList();
        return r.get(0);
    }

    public List<Post> getAllPosts(){
        Query query = em.createQuery("select p from Post p");
        List<Post> posts = query.getResultList();
        return posts;
    }

    public void deletePost(long id){
        Query query = em.createQuery("delete from Post p where p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public int addVoteToPost(long postId, int vote) {

        Post p = em.find(Post.class, postId);

        if (vote > 0) {
            p.setUpVotes(p.getUpVotes() + 1);
        } else {
            p.setDownVotes(p.getDownVotes() + 1);
        }
        return vote;
    }

}