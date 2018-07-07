package org.art.java_web.labs.entities.simple_domain_test_model;

import org.art.java_web.labs.entities.simple_domain_test_model.enums.Role;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DomainModelTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void initAll() {
        emf = Persistence.createEntityManagerFactory("domain-test-model");
    }

    @Test
    @DisplayName("Simple domain meta model test")
    void test1() {
        Metamodel metamodel = emf.getMetamodel();
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
        EntityManager em = emf.createEntityManager();
        Address homeAddress = new Address("Pushkina", "Zip", "Minsk");
        User user = new User("Harry", "Potter", homeAddress, Role.USER);
        em.getTransaction().begin();
        em.persist(user);
        userId = user.getId();

        assertNotNull(userId);

        em.getTransaction().commit();
        em.close();

        //User retrieving
        em = emf.createEntityManager();
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

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.persist(bankAccount);
        em.persist(creditCard);
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
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

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(user);
        em.persist(defaultBilling);
        em.persist(additionalBill1);
        em.persist(additionalBill2);

        userId = user.getId();
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        User user1 = em.find(User.class, userId);

        assertEquals("John Doe", user1.getFullName());

        CreditCard creditCard1 = em.getReference(CreditCard.class, user1.getDefaultBilling().getId());

        assertEquals(creditCard1.getClass(), CreditCard.class);

        System.out.println(user1.getAdditionalBillings());

        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDownAll() {
        emf.close();
    }
}
