package org.art.web.memoization.services;

import org.art.web.memoization.pojo.InvocationContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceImplTest {

    private static final String CONTEXT_NAME = "context.xml";
    private static final String CACHE_SERVICE_NAME = "cacheService";

    private static ApplicationContext context;

    private static CacheService cacheService;

    @BeforeAll
    static void initAll() {
        context = new ClassPathXmlApplicationContext(CONTEXT_NAME);
        cacheService = context.getBean(CACHE_SERVICE_NAME, CacheServiceImpl.class);
    }

    @Test
    @DisplayName("Result caching test")
    void test1() {
        InvocationContext c1 = new InvocationContext(String.class, "targetMethod1", new Object[]{1, 2, 3});
        InvocationContext c2 = new InvocationContext(Integer.class, "targetMethod2", new Object[]{4, 5, 6});
        InvocationContext c3 = new InvocationContext(StringBuilder.class, "targetMethod3", new Object[]{7, 8, 9});

        cacheService.put(c1, "result1");
        cacheService.put(c2, "result2");
        cacheService.put(c3, "result3");

        assertSame(cacheService.getCurrentCacheSize(), 3);

        Object res1 = cacheService.get(c1);
        Object res2 = cacheService.get(c2);
        Object res3 = cacheService.get(c3);

        assertAll(
                () -> assertNotNull(res1),
                () -> assertNotNull(res2),
                () -> assertNotNull(res3),
                () -> assertEquals(res1, "result1"),
                () -> assertEquals(res2, "result2"),
                () -> assertEquals(res3, "result3")
        );

        assertSame(cacheService.getCurrentCacheSize(), 3);
        assertSame(cacheService.getCacheStatistics().getCacheHits(), 3);

        InvocationContext newContext = new InvocationContext(Runnable.class, "targetMethod4", new Object[]{1});
        Object res4 = cacheService.get(newContext);

        assertNull(res4);
        assertSame(cacheService.getCacheStatistics().getCacheMisses(), 1);

        cacheService.clearCache();
        assertAll(
                () -> assertSame(cacheService.getCacheStatistics().getCacheMisses(), 0),
                () -> assertSame(cacheService.getCacheStatistics().getCacheHits(), 0),
                () -> assertSame(cacheService.getCurrentCacheSize(), 0)
        );
    }

    @Test
    @DisplayName("Cache eviction test")
    void test2() {
        cacheService.clearCache();
        assertAll(
                () -> assertSame(cacheService.getCacheStatistics().getCacheMisses(), 0),
                () -> assertSame(cacheService.getCacheStatistics().getCacheHits(), 0),
                () -> assertSame(cacheService.getCurrentCacheSize(), 0)
        );

        int cacheSize = cacheService.getCacheSize();
        InvocationContext firstContext = null;
        for (int i = 0; i < cacheSize; i++) {
            if (i == 0) {
                firstContext = new InvocationContext(String.class, "t" + i, new Object[]{i});
                String firstResult = "res" + i;
                cacheService.put(firstContext, firstResult);
            } else {
                cacheService.put(new InvocationContext(String.class, "t" + i, new Object[]{i}), "res" + i);
            }
        }

        assertSame(cacheService.getCacheSize(), cacheSize);
        assertSame(cacheService.getCurrentCacheSize(), cacheSize);

        InvocationContext newContext = new InvocationContext(Integer.class, "t", new Object[]{2, 1});
        cacheService.put(newContext, "result");

        assertSame(cacheService.getCurrentCacheSize(), 10);

        assertNull(cacheService.get(firstContext));

        assertEquals(cacheService.get(newContext), "result");

        cacheService.clearCache();
    }

    @AfterAll
    static void tearDownAll() {
        ((ClassPathXmlApplicationContext) context).close();
    }
}
