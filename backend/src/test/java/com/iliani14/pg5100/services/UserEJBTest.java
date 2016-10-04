package com.iliani14.pg5100.services;


import com.iliani14.pg5100.entities.Comment;
import com.iliani14.pg5100.entities.Post;
import com.iliani14.pg5100.entities.User;
import com.iliani14.pg5100.test.DeleterEJB;
import com.iliani14.pg5100.entities.Countries;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 13/09/16.
 *
 */


@RunWith(Arquillian.class)
public class UserEJBTest {
    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.iliani14.pg5100")
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }
        @EJB
        private UserEJB user;

        @EJB
        private PostEJB post;

        @EJB
        private CommentEJB comment;

        @EJB
        private DeleterEJB del;


        @Before
        @After
        public void clearDatabase() {
            del.deleteEntities(User.class);
            del.deleteEntities(Post.class);
            del.deleteEntities(Comment.class);
        }


        @Test
        public void testCreateNewUser(){
            user.createNewUser("u1", "password1","aaa", "bbb", "aaa@live.com", Countries.Bulgaria);
            user.createNewUser("u2", "password2","name", "lastname", "name@gmail.com", Countries.Australia);
            user.createNewUser("u3", "password3","fornavn", "etternavn", "fornavn@live.no", Countries.Norway);

            long numberOfUsers = user.numberOfUsers();
            assertEquals(3, numberOfUsers);

        }

        @Test
        public void testTopUsersWithMostPostsComments(){

            User u1 = user.createNewUser("u1", "password1","aaa", "bbb", "aaa@live.com", Countries.Bulgaria);
            User u2 = user.createNewUser("u2", "password2","name", "lastname", "name@gmail.com", Countries.Australia);
            User u3 = user.createNewUser("u3", "password3","fornavn", "etternavn", "fornavn@live.no", Countries.Norway);

            Post p = post.createNewPost(u2, "Title", "Some text");
            post.createNewPost(u2, "Title", "Some text");
            post.createNewPost(u3, "Title", "Some text");
            post.createNewPost(u1, "Title", "Some text");

            comment.createNewComment(u2, p, "Comment text");
            comment.createNewComment(u3, p, "Comment text");
            comment.createNewComment(u3, p, "Comment text");


            List<User> topUsers = user.topUsers();

            assertTrue(topUsers.get(0).getId() == (u2.getId()));

        }

        @Test
        public void testCreateNewPost() {
            User u = user.createNewUser("u1", "password1", "aaa", "bbb", "aaa@live.com", Countries.Bulgaria);
            post.createNewPost(u, "Title", "Some text");

            long posts = post.numberOfPosts();
            assertEquals(1, posts);
        }

        @Test
        public void testCreateNewComment() {
            User u = user.createNewUser("u1", "password1", "aaa", "bbb", "aaa@live.com", Countries.Bulgaria);
            Post p = post.createNewPost(u, "Title", "Some text");

            Comment c = comment.createNewComment(u, p, "Comment text");
            comment.addCommentToComment(u, c, "Comment text 2");

            long comments = comment.numberOfComments();

            Assert.assertEquals(2, user.getAllUsers().get(0).getComments().size());
            assertEquals(2, comments);
        }



        @Test
        public void testGetAllCountries(){
            user.createNewUser("u1", "password1","aaa", "bbb", "aaa@live.com", Countries.Bulgaria);
            user.createNewUser("u2", "password2","name", "lastname", "name@gmail.com", Countries.Australia);
            user.createNewUser("u3", "password3","fornavn", "etternavn", "fornavn@live.no", Countries.Norway);

            List<Countries> co = user.getCountries();

            assertEquals(3, co.size());

        }



    }