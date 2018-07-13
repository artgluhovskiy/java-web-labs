package org.art.web.labs.dao.utils;

import org.art.web.labs.model.Address;
import org.art.web.labs.model.User;
import org.art.web.labs.model.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateProviderTest {

    @Test
    @DisplayName("Hibernate Provider test")
    void test1() {
        //User persistence
        Long userId;
        Session session = HibernateProvider.createSession();

        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("Harry", "Potter", homeAddress, Role.USER);
        session.beginTransaction();
        session.save(user);
        userId = user.getId();

        assertNotNull(userId);

        session.getTransaction().commit();
        session.close();

        //User retrieving
        session = HibernateProvider.createSession();
        session.beginTransaction();

        List<User> users = session.createQuery("Select u from User u", User.class).getResultList();

        assertFalse(users.isEmpty());

        User user1 = session.load(User.class, userId);

        assertEquals("Harry Potter", user1.getFullName());

        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    static void tearDownAll() {
        HibernateProvider.closeSessionFactory();
    }
}
