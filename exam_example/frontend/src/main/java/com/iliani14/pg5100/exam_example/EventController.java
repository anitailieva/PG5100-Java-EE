package com.iliani14.pg5100.exam_example;

import com.iliani14.pg5100.exam_example.ejb.EventEJB;
import com.iliani14.pg5100.exam_example.ejb.UserEJB;
import com.iliani14.pg5100.exam_example.entity.Event;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by anitailieva on 10/10/2016.
 */
@Named
@SessionScoped
public class EventController implements Serializable {

    private boolean showOnlyOwnCountry;
    private Map<Long, Boolean> attend;

    @EJB
    private EventEJB eventEJB;

    @EJB
    private UserEJB userEJB;

    @Inject
    private LoggingController loggingController;

    @PostConstruct
    public void init(){
        showOnlyOwnCountry = true;
        attend = new ConcurrentHashMap<>();
    }

    public List<Event> getEvents() {
        List<Event> events;


        if (!loggingController.isLoggedIn() || !showOnlyOwnCountry) {
            events = eventEJB.getAllEvents();
        } else {
            events = eventEJB.getEvents(loggingController.getCountry());
        }

        String regUser = loggingController.getRegisteredUser();

        if (regUser != null) {
            events.stream().map(Event::getId).
                    forEach(id -> {
                        if (userEJB.isUserAttending(regUser, id)) {
                            attend.put(id, true);
                        } else {
                            attend.put(id, false);
                        }
                    });
        }
        return events;

    }

    public Map<Long, Boolean> getAttend(){
        return attend;
    }

    public void updateAttending(Long id, Boolean attend){

        if(attend != null && attend) {
            userEJB.addEvent(id, loggingController.getRegisteredUser());
        } else {
            userEJB.removeEvent(id, loggingController.getRegisteredUser());

        }

    }
    public boolean isShowOnlyOwnCountry(){
        return showOnlyOwnCountry;
    }
    public void setShowOnlyOwnCountry(boolean showOnlyOwnCountry){
        this.showOnlyOwnCountry = showOnlyOwnCountry;
    }
}
