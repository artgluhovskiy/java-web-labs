package org.art.web.persistence;

import org.art.web.persistence.model.Address;
import org.art.web.persistence.model.User;
import org.art.web.persistence.model.User_;
import org.art.web.persistence.model.enums.Role;
import org.art.web.persistence.utils.JPAProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaticMetaModelTest {

    @BeforeAll
    static void initAll() {
        Address address = new Address("Pushkina", "Zip", "Moscow");
        User user = new User("John", "Smith", address, Role.USER);
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    @DisplayName("Simple static meta model test")
    void test1() {
        EntityManager em = JPAProvider.getEntityManager();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> a = q.from(User.class);
        Path<String> firstNameParam = a.get(User_.firstName);
        Path<String> lastNameParam = a.get(User_.lastName);
        q.where(cb.and(
                cb.like(firstNameParam, "%n"),
                cb.like(lastNameParam, "%h")

        ));
        List<User> users = em.createQuery(q).getResultList();
        assertAll(
                () -> assertNotNull(users),
                () -> assertFalse(users.isEmpty())
        );
        em.getTransaction().commit();
        em.close();
    }
}
