package org.art.java_web.labs.services.impl;

import org.art.java_web.labs.services.DateTimeService;
import org.art.java_web.labs.services.MessageService;
import org.art.java_web.labs.utils.ClassAnalyzer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class DateTimeServiceImplTest {

    //Context metadata paths
    private static final String XML_APP_CONTEXT_PATH = "services.xml";

    //Bean identifiers
    private static final String DATE_TIME_SERVICE_CONSTRUCTOR_ID = "dateTimeService_constr";
    private static final String DATE_TIME_SERVICE_FACTORY_METHOD_ID = "dateTimeService_stat_factory";
    private static final String DATE_TIME_SERVICE_FACTORY_BEAN_ID = "dateTimeService_bean_factory";
    private static final String DATE_TIME_SERVICE_CONSTR_ARG_ID = "dateTimeService_constr_arg";

    private static final String MESSAGE_SERVICE_ID = "messageService";

    private static ApplicationContext context1;
    private static ApplicationContext context2;

    private static DateTimeService dateTimeService_constr;
    private static DateTimeService dateTimeService_stat_factory;
    private static DateTimeService dateTimeService_bean_factory;
    private static DateTimeService dateTimeService_constr_arg;

    private static MessageService messageService1;
    private static MessageService messageService2;

    @BeforeAll
    static void initAll() {
        context1 = new ClassPathXmlApplicationContext(XML_APP_CONTEXT_PATH);
        context2 = new ClassPathXmlApplicationContext(XML_APP_CONTEXT_PATH);

        dateTimeService_constr = context1.getBean(DATE_TIME_SERVICE_CONSTRUCTOR_ID, DateTimeService.class);
        dateTimeService_stat_factory = context1.getBean(DATE_TIME_SERVICE_FACTORY_METHOD_ID, DateTimeService.class);
        dateTimeService_bean_factory = context1.getBean(DATE_TIME_SERVICE_FACTORY_BEAN_ID, DateTimeService.class);
        dateTimeService_constr_arg = context1.getBean(DATE_TIME_SERVICE_CONSTR_ARG_ID, DateTimeService.class);

        messageService1 = context1.getBean(MESSAGE_SERVICE_ID, MessageService.class);
        messageService2 = context1.getBean(MESSAGE_SERVICE_ID, MessageService.class);
    }

    @Test
    @DisplayName("Initial DateTime / Message Service Test")
    void test1() {
        System.out.printf("Service ID: %s: current date: %s, current time: %s.%n",
                dateTimeService_constr.getServiceId(), dateTimeService_constr.getDate(), dateTimeService_constr.getTime());
        System.out.printf("Service ID: %s: current date: %s, current time: %s.%n",
                dateTimeService_stat_factory.getServiceId(), dateTimeService_stat_factory.getDate(), dateTimeService_stat_factory.getTime());
        System.out.printf("Service ID: %s: current date: %s, current time: %s.%n",
                dateTimeService_bean_factory.getServiceId(), dateTimeService_bean_factory.getDate(), dateTimeService_bean_factory.getTime());
        System.out.printf("Service ID: %s: current date: %s, current time: %s.%n",
                dateTimeService_constr_arg.getServiceId(), dateTimeService_constr_arg.getDate(), dateTimeService_constr_arg.getTime());
        System.out.printf("Service ID: %s.%n", messageService1.getServiceId());
    }

    @Test
    @DisplayName("Equality bean test")
    void test2() {
        //Singleton beans (default scope - singleton) from one context
        System.out.println(messageService1 == messageService2);     //true

        //Singleton beans from different contexts
        MessageService messageService1 = context1.getBean(MESSAGE_SERVICE_ID, MessageService.class);
        MessageService messageService2 = context2.getBean(MESSAGE_SERVICE_ID, MessageService.class);
        System.out.println(messageService1 == messageService2);     //false
    }

    @Test
    @DisplayName("Bean structure test")
    void test3() {
        MessageService messageService = context1.getBean(MESSAGE_SERVICE_ID, MessageService.class);
        ClassAnalyzer analyzer = new ClassAnalyzer();
        analyzer.analyze(messageService);
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context1).close();
    }
}
