package com.iliani14.pg5100.services;


import com.iliani14.pg5100.entities.Countries;
import com.iliani14.pg5100.entities.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by anitailieva on 28/09/16.
 */
@Stateless
public class UserEJB  implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PostEJB post;

    public UserEJB(){

    }


    public User createNewUser(@NotNull String userId, @NotNull String password, @NotNull String firstname,
                              String lastname, @NotNull String email, Countries country) {
        User user = new User();
        user.setUserId(userId);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setCountry(country);

        String salt = getSalt();
        user.setSalt(salt);

        String hash = computeHash(password, salt);
        user.setHash(hash);

        em.persist(user);
        return user;
    }

    public User findUserById(long id){
        return em.find(User.class, id);

    }

    public boolean login(String userId, String password) {

        User u = getUser(userId);

        if (u == null) {
            computeHash(password, getSalt());
            return false;
        }

        String hash = computeHash(password, u.getSalt());

        return hash.equals(u.getHash());
    }



    public User getUser(String userId) {

        try {
            Query query = em.createNamedQuery(User.GET_USER_BY_USERNAME);
            query.setParameter("userId", userId);
            return (User) query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        Query query = em.createQuery("select u from User u");
        List<User> users = query.getResultList();
        return users;

    }

    public long numberOfUsers(){
        Query query = em.createNamedQuery(User.COUNT_ALL_USERS);
        List <Long> r = query.getResultList();
        return r.get(0);
    }

    public List<User> topUsers(){
        Query query = em.createNamedQuery(User.GET_TOP_USERS);
        List <User> topUsers = query.getResultList();

        return topUsers;
    }

    public List<Countries> getCountries(){
        Query query = em.createNamedQuery(User.GET_COUNTRIES);
        return query.getResultList();

    }
    public long usersFromCountry(Countries country){
        Query query = em.createNamedQuery(User.COUNT_USERS_FROM_COUNTRY);
        query.setParameter("country",country);
        List<Long> usersFromCountry =  query.getResultList();
        return usersFromCountry.get(0);

    }

    @NotNull
    protected String computeHash(String password, String salt) {

        String combined = password + salt;


        return DigestUtils.sha256Hex(combined);
    }

    @NotNull
    protected String getSalt() {
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32;
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }


}