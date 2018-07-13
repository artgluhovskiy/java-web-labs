package org.art.web.labs.model.model;

import org.art.web.labs.dao.utils.HibernateProvider;
import org.art.web.labs.dao.utils.JPAProvider;
import org.art.web.labs.model.*;
import org.art.web.labs.model.enums.Role;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DomainModelTest {

    @BeforeAll
    static void initAll() {

        //Database initialization
        Item item = new Item();
        item.getImages().add(new Image(item, "Image_path_7"));
        item.getImages().add(new Image(item, "Image_path_8"));
        item.getImages().add(new Image(item, "Image_path_9"));

        Bid bid1 = new Bid(item, "Bid type 4");
        Bid bid2 = new Bid(item, "Bid type 5");
        Bid bid3 = new Bid(item, "Bid type 6");

        item.getBids().add(bid1);
        item.getBids().add(bid2);
        item.getBids().add(bid3);

        Address homeAddress = new Address("Odoevskogo", "ZipCode", "Gomel");
        User user = new User("Vinny", "Pooh", homeAddress, Role.USER);

        user.getBids().add(bid1);
        user.getBids().add(bid2);
        user.getBids().add(bid3);

        bid1.setOwner(user);
        bid2.setOwner(user);
        bid3.setOwner(user);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(item);
        em.persist(user);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Simple domain meta model test")
    void test1() {
        Metamodel metamodel = JPAProvider.getEMFactory().getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        entities.forEach(entity -> {
            System.out.printf("Entity: %s, managed type: %s%n", entity.getName(), entity.getPersistenceType());
            entity.getAttributes().forEach(attr ->
                    System.out.printf("Attribute: %s, type: %s%n", attr.getName(), attr.getJavaType()));
        });
    }

    @Test
    @DisplayName("Simple persistence test")
    void test2() {
        //User persistence
        Long userId;
        EntityManager em = JPAProvider.getEntityManager();
        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("Harry", "Potter", homeAddress, Role.USER);
        em.getTransaction().begin();
        em.persist(user);
        userId = user.getId();

        assertNotNull(userId);

        em.getTransaction().commit();
        em.close();

        //User retrieving
        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        List<User> users = em.createQuery("Select u from User u", User.class).getResultList();

        assertFalse(users.isEmpty());

        User user1 = em.find(User.class, userId);

        assertEquals("Harry Potter", user1.getFullName());

        System.out.println(user1.getRegDate().getClass().getName());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Inheritance test")
    void test3() {
        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("John", "Doe", homeAddress, Role.USER);
        BankAccount bankAccount = new BankAccount(user, "account_1", "AlphaBank");
        CreditCard creditCard = new CreditCard(user, "23432", LocalDate.now().plusMonths(2));

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(bankAccount);
        em.persist(creditCard);
        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        List<BillingDetails> billingDetails = em.createQuery("Select d from BillingDetails d", BillingDetails.class).getResultList();
        em.getTransaction().commit();
        em.close();
        System.out.println(billingDetails);
    }

    @Test
    @DisplayName("Polymorphic association test")
    void test4() {
        Long userId;
        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("John", "Doe", homeAddress, Role.USER);
        CreditCard defaultBilling = new CreditCard(user, "2343", LocalDate.now().plusMonths(3));
        CreditCard additionalBill1 = new CreditCard(user, "1111", LocalDate.now().plusMonths(4));
        BankAccount additionalBill2 = new BankAccount(user, "account", "Belinvest");

        user.setDefaultBilling(defaultBilling);
        user.getAdditionalBillings().add(additionalBill1);
        user.getAdditionalBillings().add(additionalBill2);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(user);
        em.persist(defaultBilling);
        em.persist(additionalBill1);
        em.persist(additionalBill2);

        userId = user.getId();
        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        User user1 = em.find(User.class, userId);

        assertEquals("John Doe", user1.getFullName());

        CreditCard creditCard1 = em.getReference(CreditCard.class, user1.getDefaultBilling().getId());

        assertEquals(creditCard1.getClass(), CreditCard.class);

        System.out.println(user1.getAdditionalBillings());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Collection mapping test")
    void test5() {
        Item item = new Item();
        item.getImages().add(new Image(item, "Image_path_1"));
        item.getImages().add(new Image(item, "Image_path_2"));
        item.getImages().add(new Image(item, "Image_path_3"));

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(item);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Entity association test")
    void test6() {
        Long itemId;
        Item item = new Item();
        item.getImages().add(new Image(item, "Image_path_4"));
        item.getImages().add(new Image(item, "Image_path_5"));
        item.getImages().add(new Image(item, "Image_path_6"));

        Bid bid1 = new Bid(item, "Bid type 1");
        Bid bid2 = new Bid(item, "Bid type 2");
        Bid bid3 = new Bid(item, "Bid type 3");

        item.getBids().add(bid1);
        item.getBids().add(bid2);
        item.getBids().add(bid3);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(item);
        itemId = item.getId();

        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        Item item1 = em.find(Item.class, itemId);

        assertSame(itemId, item1.getId());
        assertSame(3, item1.getImages().size());
        assertSame(3, item1.getBids().size());

        Bid firstBid = item1.getBids().iterator().next();
        item1.getBids().remove(firstBid);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Cascade type test")
    void test7() {
        Item item = new Item();
        item.getImages().add(new Image(item, "Image_path_4"));
        item.getImages().add(new Image(item, "Image_path_5"));
        item.getImages().add(new Image(item, "Image_path_6"));

        Bid bid1 = new Bid(item, "Bid type 1");
        Bid bid2 = new Bid(item, "Bid type 2");
        Bid bid3 = new Bid(item, "Bid type 3");

        item.getBids().add(bid1);
        item.getBids().add(bid2);
        item.getBids().add(bid3);

        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("John", "Doe", homeAddress, Role.USER);

        user.getBids().add(bid1);
        user.getBids().add(bid2);
        user.getBids().add(bid3);

        item.setBuyer(user);

        user.getItems().add(item);

        bid1.setOwner(user);
        bid2.setOwner(user);
        bid3.setOwner(user);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(item);
        em.persist(user);

        Long itemId = item.getId();
        Long userId = user.getId();

        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        User user1 = em.find(User.class, userId);
        Item item1 = em.find(Item.class, itemId);

        Bid firstBid = item1.getBids().iterator().next();
        item1.getBids().remove(firstBid);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Query test")
    void test8() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        String targetFirstName = "Vinny";

        //JPQL query
        TypedQuery<User> jpqlQuery = em.createQuery("Select u from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", targetFirstName);

        User user1 = jpqlQuery.getSingleResult();

        //Criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        CriteriaQuery<User> criteriaQuery = criteria.select(from).where(cb.equal(
                from.get("firstName"),
                cb.parameter(String.class, "firstName")
        ));
        User user2 = em.createQuery(criteriaQuery).setParameter("firstName", targetFirstName)
                .getSingleResult();

        assertEquals(user1, user2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Detached Criteria test")
    void test9() {
        String firstName = "Vinny";
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Restrictions.eq("firstName", firstName));

        Session session1 = HibernateProvider.getSessionFactory().getCurrentSession();
        session1.beginTransaction();

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) criteria.getExecutableCriteria(session1).list();

        System.out.println(users);

        System.out.println("*** Current session: " + session1);

        Session session2 = HibernateProvider.getSessionFactory().getCurrentSession();

        assertSame(session1, session2);

        session1.getTransaction().commit();
        session1.close();
    }

    @Test
    @DisplayName("Simple second-level cache test")
    void test10() {
        Address homeAddress = new Address("Kuprevicha", "Zip", "Minsk");
        User user = new User("John", "Williams", homeAddress, Role.USER);

        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();

        em.persist(user);
        Long userId = user.getId();

        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.find(User.class, userId);
        em.getTransaction().commit();
        em.close();

        em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.find(User.class, userId);
        em.getTransaction().commit();
        em.close();

        Statistics stats = JPAProvider.getEMFactory().unwrap(SessionFactory.class).getStatistics();
        SecondLevelCacheStatistics itemCacheStats = stats.getSecondLevelCacheStatistics(User.class.getName());
        System.out.println(itemCacheStats.getElementCountInMemory());
        System.out.println(itemCacheStats.getPutCount());
    }

    @Test
    @DisplayName("Simple 'save/saveOrUpdate/merge/persist' test")
    void test11() {

        TestEntity entity1 = new TestEntity("Entity_1");

        //'save()' test
        Session session = JPAProvider.getEntityManager().unwrap(Session.class);
        session.beginTransaction();
        Long e1Id = (Long) session.save(entity1);
        assertNotNull(e1Id);
        session.getTransaction().commit();

        session.detach(entity1);
        assertFalse(session.contains(entity1));

        session.beginTransaction();
        session.save(entity1);              //Is allowed
        session.getTransaction().commit();

        //'saveOrUpdate()' test
        entity1.setName("Entity_1_updated");
        session.beginTransaction();
        session.saveOrUpdate(entity1);
        session.getTransaction().commit();

        entity1.setName("Entity_1_updated_updated");
        session.beginTransaction();
        session.update(entity1);
        session.getTransaction().commit();

        TestEntity entity2 = new TestEntity("Entity_2");
        session.beginTransaction();
        session.saveOrUpdate(entity2);
        session.getTransaction().commit();
        session.close();

        //'persist()' test
        EntityManager em = JPAProvider.getEntityManager();

        em.getTransaction().begin();
        TestEntity entity3 = em.merge(entity2);
        assertEquals(entity2.getId(), entity3.getId());
        em.persist(entity3);
        em.getTransaction().commit();

        //'merge()' test
        em.getTransaction().begin();
        entity3.setName("Entity_2_updated");
        em.merge(entity3);
        em.getTransaction().commit();

        TestEntity entity4 = new TestEntity("Entity_4");
        em.getTransaction().begin();
        em.merge(entity4);
        em.getTransaction().commit();

        em.close();
    }

    @Test
    @DisplayName("Simple 'get/load/find/getReference' test")
    void test12() {
        TestEntity entity1 = new TestEntity("Entity_1");

        //'load()' test
        Session session = JPAProvider.getEntityManager().unwrap(Session.class);
        session.beginTransaction();
        TestEntity entity2 = session.load(TestEntity.class, 2000L);     //returns proxy

        assertThrows(EntityNotFoundException.class, entity2::getId);

        System.out.println(entity2.getClass().getName());
        session.getTransaction().commit();

        session.beginTransaction();
        Long e1Id = (Long) session.save(entity1);
        session.getTransaction().commit();

        session.beginTransaction();
        session.load(TestEntity.class, e1Id);
        session.getTransaction().commit();

        session.evict(entity1);

        //'get()' test
        session.beginTransaction();
        TestEntity entity6 = session.get(TestEntity.class, e1Id);
        assertNotNull(entity6);
        TestEntity entity7 = session.get(TestEntity.class, 20000L);
        assertNull(entity7);
        session.getTransaction().commit();

        session.close();

        //'getReference()' test
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        TestEntity entity8 = em.getReference(TestEntity.class, 2000L);
        em.getTransaction().commit();

        em.getTransaction().begin();
        TestEntity entity9 = em.getReference(TestEntity.class, e1Id);
        System.out.println(entity9.getClass().getName());
        em.getTransaction().commit();

        em.detach(entity9);

        assertThrows(LazyInitializationException.class, entity9::getName);

        em.close();
    }

    @AfterAll
    static void tearDownAll() {
        JPAProvider.closeEMFactory();
    }
}
