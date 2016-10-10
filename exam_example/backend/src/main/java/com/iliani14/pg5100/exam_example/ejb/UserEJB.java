package com.iliani14.pg5100.exam_example.ejb;

import com.iliani14.pg5100.exam_example.entity.Event;
import com.iliani14.pg5100.exam_example.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Iterator;

/**
 * Created by anitailieva on 07/10/2016.
 */
@Stateless
public class UserEJB implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public UserEJB(){
    }


    public User getUser(String userId) {
    return em.find(User.class, userId);

    }
    public boolean createUser(String userId, String password, String name, String surname, String country){

        if(userId == null || userId.isEmpty() || password == null || password.isEmpty()){
            return false;
        }

        User u = getUser(userId);
        if(u != null){
            return false;

        }
        u = new User();
        u.setUserId(userId);

        String salt = getSalt();
        u.setSalt(salt);

        String hash = computeHash(password, salt);
        u.setHash(hash);

        u.setName(name);
        u.setSurname(surname);
        u.setCountry(country);

        em.persist(u);

        return true;

    }

    public boolean login( String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        User userLogin = getUser(userId);
        if( userLogin == null){
            return false;

        }
        String hash = computeHash(password, userLogin.getSalt());

        boolean loggedIn = hash.equals(userLogin.getHash());

        return loggedIn;

    }

        public void addEvent(Long id, String userId){
            User u = em.find(User.class, userId);
            Event event = em.find(Event.class, id);

            if( u.getEventList().stream().anyMatch(e -> e.getId().equals(id))){
                return;
            }


            u.getEventList().add(event);
            event.getAttendingUsers().add(u);
        }

        public void removeEvent(Long id, String userId){
           User user = em.find(User.class, userId);

            Iterator<Event> eventIterator = user.getEventList().iterator();
            while (eventIterator.hasNext()){
                Event e = eventIterator.next();
                if(e.getId().equals(id)){
                    eventIterator.remove();

                    Iterator<User> userIterator = e.getAttendingUsers().iterator();
                    while (userIterator.hasNext()){
                        User u = userIterator.next();
                        if(u.getUserId().equals(userId)){
                            userIterator.remove();
                            break;
                        }
                    }
                    break;
                }
            }
        }

        public boolean isUserAttending(String userId, Long eventId){
            User u = getUser(userId);
            if(userId == null){
                return false;
            }

            return u.getEventList().stream().anyMatch(e -> e.getId().equals(eventId));
        }
    //------------------------------------------------------------------------


    @NotNull
    protected String computeHash(String password, String salt){
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }

    @NotNull
    protected String getSalt(){
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }
}
