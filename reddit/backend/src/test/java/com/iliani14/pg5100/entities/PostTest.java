package com.iliani14.pg5100.entities;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class PostTest {
    private EntityManagerFactory emf;
    private EntityManager em;


    @Before
    public void init() throws Exception {
        emf = Persistence.createEntityManagerFactory("DB");
        em = emf.createEntityManager();

    }

    @After
    public void tearDown() throws Exception {
        em.close();
        emf.close();

    }


    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(Object o: obj) {
                em.persist(o);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Failed transaction: " + e.toString());

            return false;
        }
        return true;
    }

    @Test
    public void testNewPost(){
        Post post = new Post();
        post.setTitle("First post");
        post.setContent("Words");

        assertTrue(persistInATransaction(post));

        assertNotNull(post.getId());
    }

    @Test
    public void testPostWithAuthor(){
        Post p = new Post();
        p.setTitle("Title");
        p.setContent("Content");

        User u = new User();;
        u.setFirstName("Tom");
        u.setLastName("Svendsen");
        u.setAddress("Grefsenveien");
        u.setEmail("tom@live.com");
        u.setCity("Oslo");
        u.setCountry(Countries.Norway);

        assertTrue(persistInATransaction(p, u));

    }

    @Test
    public void testNumberOfVotes(){
        Post postUp = new Post();
        postUp.setTitle("Voting up");
        postUp.setContent("Votes UP");
        postUp.setUpVotes(10);

        Post postDown = new Post();
        postDown.setTitle("Voting down");
        postDown.setContent("Post DOWN");
        postDown.setDownVotes(4);

        assertTrue(persistInATransaction(postUp,postDown));
        assertEquals(10, postUp.getUpVotes());
        assertEquals(4, postDown.getDownVotes());
    }

    @Test
    public void addCommentToPost(){
        Post post = new Post();
        post.setContent("Add some text");
        post.setTitle("New post");

        Comment comment = new Comment();
        comment.setContent("Comment here");

        post.setComments(new ArrayList<>());
        post.getComments().add(comment);

        assertTrue(persistInATransaction(post, comment));

    }

}