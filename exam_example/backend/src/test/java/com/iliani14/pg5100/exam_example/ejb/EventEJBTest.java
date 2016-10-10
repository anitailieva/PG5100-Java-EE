package com.iliani14.pg5100.exam_example.ejb;

import com.iliani14.pg5100.exam_example.entity.Event;
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
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by anitailieva on 07/10/2016.
 */
@RunWith(Arquillian.class)
public class EventEJBTest {


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
    public void clearDatabase(){
        deleterEJB.deleteEntities(User.class);
        deleterEJB.deleteEntities(Event.class);
    }

    @Test
    public void testCreateEvent(){
        String userId = "hei";
        userEJB.createUser(userId, "password", "pass", "surname", "United States");
        eventEJB.createEvent("title", "text", "United States", "New York", userId);

        assertEquals(1, eventEJB.getAllEvents().size());
        assertEquals(1, eventEJB.getEvents("United States").size());
    }

    @Test
    public void testEventsFromACountry(){
        String user = "user";
        userEJB.createUser(user,"password", "name", "surname", "United States");
        eventEJB.createEvent("title", "text", "United States", "New York", user);

        assertTrue(eventEJB.getAllEvents().stream().anyMatch(e -> e.getCountry().equals("United States")));


    }

    @Test
    public void testEmptyEventList(){
        List<Event> events = eventEJB.getAllEvents();
        assertEquals(0, events.size());
    }
}
