package org.art.web.labs.services.dummy;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyServiceImplTest {

    private static final String BEAN_DEF_NAME = "web-labs.xml";

    private static ApplicationContext context;

    @BeforeAll
    static void initAll() {
        context = new ClassPathXmlApplicationContext(BEAN_DEF_NAME);
    }

    @Test
    @DisplayName("Bean test")
    void test() {
        String param = "Hello";
        DummyService dummyService = context.getBean("dummyService", DummyService.class);

        String retString = dummyService.echo(param);
        assertEquals(param, retString);

        String result = dummyService.getHello();
        assertEquals("Hello", result);
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context).close();
    }
}
