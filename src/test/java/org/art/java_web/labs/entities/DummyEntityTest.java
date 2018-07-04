package org.art.java_web.labs.entities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DummyEntityTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void initAll() {
        emf = Persistence.createEntityManagerFactory("hello");
    }

    @Test
    @DisplayName("Initial 'Hello world' test")
    void test1() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        DummyEntity dummy = new DummyEntity("Hello entity");
        em.persist(dummy);
        dummy.setName("Updated entity");
        System.out.println(dummy.getClass().getName());
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    static void tearDownAll() {
        emf.close();
    }
}
