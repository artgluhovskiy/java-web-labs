package org.art.java_web.labs.entities.simple_domain_test_model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLOutput;
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
        User user = new User("Harry", "Potter");
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

        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDownAll() {
        emf.close();
    }
}
