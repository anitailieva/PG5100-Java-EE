package com.iliani14.pg5100.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Created by anitailieva on 05/09/16.
 */

@Ignore
public class UserTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void init() {
        emf = Persistence.createEntityManagerFactory("DB");
        em = emf.createEntityManager();
      createUsers();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }


    private void createUsers() {
        User user1 = new User();
        user1.setFirstName("AAA");
        user1.setLastName("III");
        user1.setAddress("CK 32");
        user1.setEmail("aaa@live.com");
        user1.setCity("Oslo");
        user1.setCountry(Countries.Norway);


        User user2 = new User();
        user2.setFirstName("BBB");
        user2.setLastName("CCC");
        user2.setAddress("Industrigata 43");
        user2.setEmail("bbb@live.com");
        user2.setCity("Oslo");
        user2.setCountry(Countries.Norway);

        User user3 = new User();
        user3.setFirstName("Jeg");
        user3.setLastName("Du");
        user3.setAddress("Some Street");
        user3.setEmail("jeg@gmail.com");
        user3.setCity("Berlin");
        user3.setCountry(Countries.Germany);

        User user4 = new User();
        user4.setFirstName("En");
        user4.setLastName("To");
        user4.setAddress("5th avenue");
        user4.setEmail("en@gmail.com");
        user4.setCity("New York");
        user4.setCountry(Countries.USA);

        User user5 = new User();
        user5.setFirstName("Name");
        user5.setLastName("Surname");
        user5.setAddress("Trondheimsveien");
        user5.setEmail("name@gmail.com");
        user5.setCity("Oslo");
        user5.setCountry(Countries.Norway);




        assertTrue(persistInATransaction(user1, user2, user3, user4, user5));
    }


    private boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (Object o : obj) {
                em.persist(o);
            }
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("FAILED TRANSACTION: " + e.toString());

            return false;
        }
        return true;
    }


    @Test
    public void testEmptyUser() {
        User user = new User();
        assertFalse(persistInATransaction(user));

    }


    @Test
    public void testGetAllUsers() {
        Query q1 = em.createQuery("select u from User u");
        List<User> users = q1.getResultList();

        assertEquals(5, users.size());
        System.out.println("The number of users is " + users.size());


    }

    @Test
    public void testUserFromCountry() {
        Query query = em.createQuery("select u from User u where u.country = 'Norway'");
        List<User> users = query.getResultList();


        //Users from Norway
        assertEquals(3, users.size());
    }

    @Test
    public void testSetOfCountries() {
        Query query = em.createNamedQuery(User.GET_COUNTRIES);
        List<User> c = query.getResultList();

        assertEquals(3, c.size());

    }

    @Test
    public void testUserNames() {
        User user6 = new User();
        user6.setFirstName("Niclas");
        user6.setLastName("Svendsen");
        user6.setAddress("Grefsenveien");
        user6.setEmail("nic@live.com");
        user6.setCity("Oslo");
        user6.setCountry(Countries.Norway);

        User user7 = new User();
        user7.setFirstName("John");
        user7.setLastName("Smith");
        user7.setAddress("Street 2324");
        user7.setEmail("john@live.com");
        user7.setCity("London");
        user7.setCountry(Countries.UK);
        persistInATransaction(user6, user7);

        Query query = em.createQuery("select u from User u");
        List<User> users = query.getResultList();

        assertEquals(7, users.size());
        assertTrue(users.stream().anyMatch(a -> a.getFirstName().equals(user6.getFirstName())));
        assertTrue(users.stream().anyMatch(b -> b.getFirstName().equals(user7.getFirstName())));


    }

    @Test
    public void testCaseWhen() {
        Query query = em.createQuery(
                "select CASE u.country WHEN country THEN 'yes' ELSE 'no' END " +
                        "from User u");

        List<String> osloCounters = query.getResultList();
        assertEquals(5, osloCounters.size());

        assertEquals(3, osloCounters.stream().filter(s -> s.equals("yes")).count());
        assertEquals(2, osloCounters.stream().filter(s -> s.equals("no")).count());
    }

    @Test
    public void testBindingParameters() {

        assertEquals(3, findUsers(Countries.Norway, "Oslo").size());
        assertEquals(1, findUsers(Countries.Norway, "New York").size());

    }

    private List<User> findUsers(Countries country, String city){
        Query query = em.createQuery("select u from User u where u.country = ?1 and u.city = ?2");
        query.setParameter(1, country);
        query.setParameter(2, city);

        return query.getResultList();
    }

    @Test
    public void testDeleteUsers(){
      Query query = em.createQuery("select u from User u");

        assertEquals(5, query.getResultList().size());


        Query delete = em.createQuery("delete from User u");

        EntityTransaction t = em.getTransaction();
        t.begin();

        try{
            delete.executeUpdate();
        }catch (Exception e){
            t.rollback();
            fail();
        }

        assertEquals(0, query.getResultList().size());

    }
}

