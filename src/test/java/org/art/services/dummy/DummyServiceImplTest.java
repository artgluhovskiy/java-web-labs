package org.art.services.dummy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class DummyServiceImplTest {

    private static final String BEAN_DEF_NAME = "dummy-service-bean-def.xml";

    private static ApplicationContext context;

    @BeforeAll
    static void initAll() {
        context = new ClassPathXmlApplicationContext(BEAN_DEF_NAME);
    }

    @Test
    @DisplayName("Bean initialization test")
    void test() {
        DummyService dummyService1 = context.getBean("dummyService1", DummyService.class);
        System.out.println(dummyService1.echo("Hello!"));
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context).close();
    }
}
