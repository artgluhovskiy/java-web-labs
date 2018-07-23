package org.art.web.persistence.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Hibernate session provider.
 */
public class HibernateProvider {

    private static final Logger LOG = LogManager.getLogger(HibernateProvider.class);

    private static final String DEFAULT_HIBERNATE_CONFIG = "hibernate.cfg.xml";

    private static SessionFactory sessionFactory;

    //Session factory eager initialization
    static {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure(DEFAULT_HIBERNATE_CONFIG)
                    .build();

            sessionFactory = new MetadataSources(serviceRegistry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception ex) {
            LOG.error("Hibernate Session Factory: initialization failed!", ex);
            throw new HibernateException(ex);
        }
    }

    /**
     * Returns Hibernate Session Factory instance.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Creates a new Hibernate Session instance.
     */
    public static Session createSession() {
        return sessionFactory.openSession();
    }

    /**
     * Closes Hibernate Session Factory.
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

