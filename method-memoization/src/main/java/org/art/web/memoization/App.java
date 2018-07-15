package org.art.web.memoization;

import org.art.web.memoization.services.CacheService;
import org.art.web.memoization.services.CacheServiceImpl;
import org.art.web.memoization.services.FibonacciService;
import org.art.web.memoization.services.FibonacciServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Method memoization example.
 */
public class App {

    public static void main(String[] args) {

        String fibonacciServiceName = "fibonacciService";
        String cacheServiceName = "cacheService";

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        FibonacciService fibonacciService = context.getBean(fibonacciServiceName, FibonacciServiceImpl.class);
        CacheService cacheService = context.getBean(cacheServiceName, CacheServiceImpl.class);

        System.out.println(fibonacciService.calculate(5));
        System.out.println(cacheService.getCacheStatistics());
        System.out.println(fibonacciService.calculate(5));
        System.out.println(cacheService.getCacheStatistics());
    }
}
