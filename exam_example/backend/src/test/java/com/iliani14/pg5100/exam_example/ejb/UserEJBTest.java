package com.iliani14.pg5100.exam_example.ejb;

import com.iliani14.pg5100.exam_example.entity.User;
import com.iliani14.pg5100.exam_example.util.DeleterEJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by anitailieva on 07/10/2016.
 */

@RunWith(Arquillian.class)
public class UserEJBTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "com.iliani14.pg5100.exam_example")
                .addClass(DeleterEJB.class)
                .addPackages(true, "org.apache.commons.codec")
                .addAsResource("META-INF/persistence.xml");
    }


    @EJB
    private UserEJB userEJB;

    @EJB
    private EventEJB eventEJB;

    @EJB
    private DeleterEJB deleterEJB;

    @Before
    @After
    public void emptyDatabase(){
        deleterEJB.deleteEntities(User.class);
    }



    @Test
    public void testCreateAUser(){
        boolean isCreated =  userEJB.createUser("user", "password", "name", "surname", "Norway");
        assertTrue(isCreated);


    }

    @Test
    public void testUserLogin(){
        String user = "user";
        String password = "pass";
        userEJB.createUser(user, password, "name", "surname", "Norway");

        boolean isLoggedIn = userEJB.login(user, password);
        assertTrue(isLoggedIn);
    }



    @Test
    public void testCreateAUserWithEmptyUsername() {
        String user = "";
        String password = "password";

        boolean emptyUser = userEJB.createUser(user,password,"a","b","Norway");
        assertFalse(emptyUser);
    }

    @Test
    public void testAttendingEvent(){
        String user = "user";
        String password = "pass";
        userEJB.createUser(user, password, "name", "surname", "Norway");
        Long id = eventEJB.createEvent("Title", "Text", "Norway", "Oslo", "user");

        userEJB.addEvent(id, user);
        assertTrue(userEJB.isUserAttending(user, id));

        userEJB.removeEvent(id, user);
        assertFalse(userEJB.isUserAttending(user, id));
    }




}

