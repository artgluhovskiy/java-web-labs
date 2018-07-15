package org.art.web.memoization.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class FibonacciServiceImplTest {

    private static final String CONTEXT_NAME = "context.xml";
    private static final String CACHE_SERVICE_NAME = "cacheService";
    private static final String FIBONACCI_SERVICE_NAME = "fibonacciService";

    private static ApplicationContext context;

    private static CacheService cacheService;
    private static FibonacciService fibonacciService;

    @BeforeAll
    static void initAll() {
        context = new ClassPathXmlApplicationContext(CONTEXT_NAME);
        cacheService = context.getBean(CACHE_SERVICE_NAME, CacheServiceImpl.class);
        fibonacciService = context.getBean(FIBONACCI_SERVICE_NAME, FibonacciServiceImpl.class);
    }

    @Test
    @DisplayName("Cache service singleton test")
    void test1() {
        CacheService cacheServiceCopy = context.getBean(CACHE_SERVICE_NAME, CacheServiceImpl.class);
        assertSame(cacheService, cacheServiceCopy);
    }

    @Test
    @DisplayName("Fibonacci service test")
    void test2() {
        assertAll(
                () -> assertEquals(fibonacciService.calculate(0), 1),
                () -> assertEquals(fibonacciService.calculate(1), 1),
                () -> assertEquals(fibonacciService.calculate(2), 2),
                () -> assertEquals(fibonacciService.calculate(3), 3),
                () -> assertEquals(fibonacciService.calculate(4), 5),
                () -> assertEquals(fibonacciService.calculate(5), 8),
                () -> assertEquals(fibonacciService.calculate(15), 987)
        );

        cacheService.clearCache();
        assertAll(
                () -> assertSame(cacheService.getCacheStatistics().getCacheMisses(), 0),
                () -> assertSame(cacheService.getCacheStatistics().getCacheHits(), 0),
                () -> assertSame(cacheService.getCurrentCacheSize(), 0)
        );
    }

    @Test
    @DisplayName("Results caching test")
    void test3() {
        cacheService.clearCache();
        assertAll(
                () -> assertSame(cacheService.getCacheStatistics().getCacheMisses(), 0),
                () -> assertSame(cacheService.getCacheStatistics().getCacheHits(), 0),
                () -> assertSame(cacheService.getCurrentCacheSize(), 0)
        );

        fibonacciService.calculate(5);
        assertAll(
                () -> assertSame(cacheService.getCurrentCacheSize(), 6),
                () -> assertSame(cacheService.getCacheStatistics().getCacheHits(), 3),
                () -> assertSame(cacheService.getCacheStatistics().getCacheMisses(), 6)
        );

        cacheService.clearCache();
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context).close();
    }
}
