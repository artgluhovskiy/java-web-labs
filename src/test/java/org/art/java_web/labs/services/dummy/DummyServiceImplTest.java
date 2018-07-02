package org.art.java_web.labs.services.dummy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class DummyServiceImplTest {

    private static final String BEAN_DEF_NAME = "web-labs.xml";

    private static ApplicationContext context;

    @BeforeAll
    static void initAll() {
        context = new ClassPathXmlApplicationContext(BEAN_DEF_NAME);
    }

    @Test
    @DisplayName("Bean initialization test")
    void test() {
        DummyService dummyService = context.getBean("dummyService", DummyServiceImpl.class);
        System.out.println(dummyService.echo("Hello!"));
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context).close();
    }
}
