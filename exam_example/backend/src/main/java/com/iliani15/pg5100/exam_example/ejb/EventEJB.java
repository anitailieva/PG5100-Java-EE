package com.iliani15.pg5100.exam_example.ejb;

import com.iliani15.pg5100.exam_example.Countries;
import com.iliani15.pg5100.exam_example.entity.Event;
import com.iliani15.pg5100.exam_example.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by anitailieva on 07/10/2016.
 */
public class EventEJB {

    @PersistenceContext
    private EntityManager em;


    public EventEJB(){

    }

    public Event getEvent(Long id){
        return em.find(Event.class, id);
    }
    public Long createEvent(String title, String text, String country, String location, String userId){
        if(Countries.getCountries().contains(country)) {
            throw new IllegalArgumentException("Invalid country: " + country);
        }
        User u = em.find(User.class, userId);
        if(u == null){
            throw new IllegalArgumentException("No user with id: "+userId);
        }

    Event event = new Event();
        event.setTitle(title);
        event.setText(text);
        event.setCountry(country);
        event.setLocation(location);
        event.setAuthor(userId);
        em.persist(event);

        return event.getId();
    }

    public List<Event> getAllEvents(){
        Query query = em.createNamedQuery(Event.GET_ALL_EVENTS);
        List<Event> events = query.getResultList();

        return events;
    }

    public List<Event> getEvents(String country){
    Query query = em.createNamedQuery(Event.GET_COUNTRIES);
        query.setParameter("country",country);

        List<Event> eventsFromCountry = query.getResultList();

        return eventsFromCountry;
    }
}
