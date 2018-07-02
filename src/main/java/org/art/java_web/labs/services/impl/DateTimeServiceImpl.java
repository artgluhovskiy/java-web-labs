package org.art.java_web.labs.services.impl;

import org.art.java_web.labs.services.DateTimeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeServiceImpl implements DateTimeService, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, BeanPostProcessor, DisposableBean {

    private String serviceId;

    @ConstructorProperties({"serviceId"})
    public DateTimeServiceImpl(String serviceId) {
        this.serviceId = serviceId;
    }

    public DateTimeServiceImpl() {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: constructor stage!");
    }

    /* Factory method */

    public static DateTimeService createDateTimeService(String serviceId) {
        return new DateTimeServiceImpl(serviceId);
    }

    /* Service methods */

    @Override
    public String getDate() {
        return LocalDate.now().toString();
    }

    @Override
    public String getTime() {
        return LocalTime.now().toString();
    }

    @Override
    public String getDateTime() {
        return LocalDateTime.now().toString();
    }

    /* Getters / Setters */

//    @Required
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    /* Bean life cycle callbacks */

    @Override
    public void setBeanName(String name) {
        System.out.printf("*** LIFE CYCLE: DATE TIME SERVICE: 'setBeanName' - %s!%n", name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'setBeanClassLoader' stage!");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'setBeanFactory' stage!");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'postProcessorBeforeInitialization' stage!");
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'setApplicationContext' stage!");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'postConstruct' stage!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'afterPropertiesSet' stage!");
    }

    public void initMethod() {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'initMethod' stage!");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'preDestroy' stage!");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'destroy' stage!");
    }

    public void destroyMethod() {
        System.out.println("*** LIFE CYCLE: DATE TIME SERVICE: 'destroyMethod' stage!");
    }
}
