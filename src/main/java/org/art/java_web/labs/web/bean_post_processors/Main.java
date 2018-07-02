package org.art.java_web.labs.web.bean_post_processors;

import org.art.java_web.labs.services.dummy.DummyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("web-labs.xml");
        String param = "Hello";
        DummyService dummyService = context.getBean("dummyService", DummyService.class);
        String retString = dummyService.echo(param);
        System.out.println(retString);
        dummyService.getHello();
    }
}
