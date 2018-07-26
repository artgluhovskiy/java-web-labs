package org.art.web.persistence;

import org.art.web.persistence.model.*;
import org.art.web.persistence.model.enums.Role;
import org.art.web.persistence.utils.JPAProvider;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityGraphTest {

    private static Long userId;

    @BeforeAll
    static void initAll() {
        Address address = new Address("Times Road", "Zip", "Moscow");
        User user = new User("John", "Smith", address, Role.USER);
        Set<BillingDetails> billings = new HashSet<>();
        BillingDetails bil1 = new CreditCard(user, "Card 1", LocalDate.now().plusMonths(3));
        BillingDetails bil2 = new CreditCard(user, "Card 2", LocalDate.now().plusMonths(2));
        BillingDetails bil3 = new CreditCard(user, "Card 3", LocalDate.now().plusMonths(1));
        billings.add(bil1);
        billings.add(bil2);
        billings.add(bil3);
        user.setAdditionalBillings(billings);
        Set<Item> items = new HashSet<>();
        Item i1 = new Item("Item 1");
        Item i2 = new Item("Item 2");
        i1.setBuyer(user);
        i2.setBuyer(user);
        items.add(i1);
        items.add(i2);
        user.setItems(items);
        Bid bd1 = new Bid("Bid type 1");
        Bid bd2 = new Bid("Bid type 1");
        bd1.setOwner(user);
        bd2.setOwner(user);
        Set<Bid> bids = new HashSet<>();
        bids.add(bd1);
        bids.add(bd2);
        user.setBids(bids);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        userId = user.getId();
        em.close();
    }

    @Test
    @DisplayName("Lazy collection loading (+ @LazyCollection test) test")
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
        //Enables fetch profiling
        em.unwrap(Session.class).enableFetchProfile(User.PROFILE_USER_BIDS);
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        PersistenceUnitUtil persistUnitUtil = JPAProvider.getPersistenceUnitUtil();
        assertTrue(persistUnitUtil.isLoaded(user.getBids()));
        em.getTransaction().commit();
        em.unwrap(Session.class).disableFetchProfile(User.PROFILE_USER_BIDS);
        em.clear();
        em.getTransaction().begin();
        User newUser = em.find(User.class, userId);
        assertFalse(persistUnitUtil.isLoaded(newUser.getBids()));
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Entity graph test")
    void test4() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        PersistenceUnitUtil persistUnitUtil = JPAProvider.getPersistenceUnitUtil();
        Set<Item> items = user.getItems();
        assertFalse(persistUnitUtil.isLoaded(items));
        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        Map<String, Object> properties = new HashMap<>();
        properties.put(
                "javax.persistence.loadgraph",
                em.getEntityGraph(User.ENTITY_GRAPH_USER_ITEMS)
        );
        em.getTransaction().begin();
        user = em.find(User.class, userId, properties);
        items = user.getItems();
        assertTrue(persistUnitUtil.isLoaded(items));
        em.getTransaction().commit();
        em.close();
    }
}
