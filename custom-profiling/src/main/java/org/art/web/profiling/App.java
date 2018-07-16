package org.art.web.profiling;

import org.art.web.profiling.services.DummyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Method profiling test.
 */
public class App {
    public static void main(String[] args) {

        String dummyServiceName = "dummyService";

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        DummyService dummyService = context.getBean(dummyServiceName, DummyService.class);

        dummyService.profiledMethod();

        dummyService.regularMethod();
    }
}
