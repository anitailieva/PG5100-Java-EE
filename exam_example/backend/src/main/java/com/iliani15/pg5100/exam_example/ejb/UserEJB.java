package com.iliani15.pg5100.exam_example.ejb;

import com.iliani15.pg5100.exam_example.entity.Event;
import com.iliani15.pg5100.exam_example.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Iterator;

/**
 * Created by anitailieva on 07/10/2016.
 */
@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em;



    public UserEJB(){

    }

    public User getUser(String userId) {
        return em.find(User.class, userId);

    }

    public boolean createUser(String userId, String password, String name, String surname, String country){
        if(userId == null || userId.isEmpty() || password == null || password.isEmpty())
        return false;

        User u = getUser(userId);
        if(u == null){
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



    public boolean login(String userId, String password){
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        User u = getUser(userId);
        if(u == null){
            return false;
        }

        String hash = computeHash(password, u.getSalt());
        u.setHash(hash);

        boolean loggedIn = hash.equals(u.getHash());

        return loggedIn;

    }
    public void addEvent(Long id, String userId){
        User user = em.find(User.class, userId);
        Event event = em.find(Event.class, id);

        if(user.getEventList().stream().anyMatch(e -> e.getId().equals(id))){
            return;
        }

        user.getEventList().add(event);
        event.getAttendingUsers().add(user);


    }
    public void removeEvent(Long id, String userId){
        User u = em.find(User.class, userId);

        Iterator<Event> iterator = u.getEventList().iterator();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            if (e.getId().equals(id)) {
                iterator.remove();

                Iterator<User> userIterator = e.getAttendingUsers().iterator();
                User user = userIterator.next();
                if (userId.equals(user.getUserId())) {
                    userIterator.remove();
                    break;
                }
            }
            break;
        }

    }

    public boolean getAttentingUsers(Long eventId, String userId){
        User user = getUser(userId);
        if(user == null){
            return false;
        }
    return user.getEventList().stream().anyMatch(e -> e.getId().equals(eventId));
    }


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
