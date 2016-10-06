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
import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 13/09/16.
 */

@Ignore
public class CommentTest {

    private EntityManagerFactory emf;
    private EntityManager em;


    @Before
    public void init() throws Exception {
        emf = Persistence.createEntityManagerFactory("DB");
        em = emf.createEntityManager();
    }

    @After
    public void close() throws Exception{
        em.close();
        emf.close();
    }

    @Test
    public void testAddComment(){
        Comment c1 = new Comment();
        c1.setContent("First comment");

        Comment c2 = new Comment();
        c2.setContent("Second comment");


        assertTrue(persistATransation(c1, c2));
        assertNotNull(c1.getId());
        assertNotNull(c2.getId());
    }


    private boolean persistATransation(Object ... obj){
        EntityTransaction t = em.getTransaction();
        t.begin();

        try{
            for(Object o: obj) {
                em.persist(o);
            }
            t.commit();
        }catch(Exception e){
            t.rollback();
            return false;
        }
        return true;

    }

    @Test
    public void addCommentToComment(){

        Comment c1 = new Comment();
        c1.setContent("First comment");
        Comment c2 = new Comment();
        c2.setContent("Second comment");

        c1.setComments(new ArrayList<>());
        c1.getComments().add(c2);
        assertTrue(persistATransation(c1, c2));

    }
    @Test
    public void testCommentWithAuthor(){
        Post post = new Post();
        post.setTitle("Text");
        post.setContent("text");
        post.setCreationDate(new Date());
        assertTrue(persistATransation(post));

        User user = new User();
        user.setFirstName("aa");
        user.setLastName("ab");
        user.setEmail("aa@live.com");
        assertEquals(0, user.getPosts().size());
        user.setPost(new ArrayList<>());
        user.getPosts().add(post);

        assertTrue(persistATransation(user));
        assertEquals(1, user.getPosts().stream().count());

        Comment comment = new Comment();
        comment.setContent("Comment");
        post.setComments(new ArrayList<>());
        post.getComments().add(comment);

        assertTrue(persistATransation(post, comment));
        assertEquals(1, post.getComments().stream().count());
    }

}
