package org.art.web.persistence;

import org.art.web.persistence.model.Address;
import org.art.web.persistence.model.BillingDetails;
import org.art.web.persistence.model.CreditCard;
import org.art.web.persistence.model.User;
import org.art.web.persistence.model.enums.Role;
import org.art.web.persistence.utils.JPAProvider;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityGraphTest {

    private static Long userId;

    @BeforeAll
    static void initAll() {
        Address address = new Address("Times Road", "Zip", "Moscow");
        User user = new User("John", "Smith", address, Role.USER);

        Set<BillingDetails> billings = new HashSet<>();
        BillingDetails b1 = new CreditCard(user, "Card_1", LocalDate.now().plusMonths(3));
        BillingDetails b2 = new CreditCard(user, "Card_2", LocalDate.now().plusMonths(2));
        BillingDetails b3 = new CreditCard(user, "Card_3", LocalDate.now().plusMonths(1));
        billings.add(b1);
        billings.add(b2);
        billings.add(b3);
        user.setAdditionalBillings(billings);
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        userId = user.getId();
        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Simple lazy loading test")
    void test1() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        User user = em.createQuery("Select u from User u where u.firstName like '%n'", User.class).getSingleResult();
        assertNotNull(user);
        PersistenceUnitUtil persistUnitUtil = JPAProvider.getPersistenceUnitUtil();
        Set<BillingDetails> billings = user.getAdditionalBillings();
        assertFalse(persistUnitUtil.isLoaded(billings));

        //'size()' should not trigger collection loading (because of @LazyCollection)
        assertSame(billings.size(), 3);
        assertFalse(persistUnitUtil.isLoaded(billings));

        BillingDetails billing = billings.iterator().next();
        //Billings are loaded after starting the iteration
        assertTrue(persistUnitUtil.isLoaded(billing));
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Simple lazy loading (via Id) test")
    void test2() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        User user = em.getReference(User.class, userId);
        PersistenceUnitUtil persistUnitUtil = JPAProvider.getPersistenceUnitUtil();
        assertFalse(persistUnitUtil.isLoaded(user));
        assertNotNull(user.getId());
        assertTrue(persistUnitUtil.isLoaded(user));
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Fetch profile test")
    void test3() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        //Enables fetch profiling
        em.unwrap(Session.class).enableFetchProfile(User.PROFILE_JOIN_BILLINGS);
        User user = em.find(User.class, userId);
        PersistenceUnitUtil persistUnitUtil = JPAProvider.getPersistenceUnitUtil();
        assertTrue(persistUnitUtil.isLoaded(user.getAdditionalBillings()));
        em.getTransaction().commit();
        em.clear();
        em.getTransaction().begin();
        User newUser = em.find(User.class, userId);
        assertFalse(persistUnitUtil.isLoaded(newUser.getAdditionalBillings()));
        em.getTransaction().commit();
        em.close();
    }
}
