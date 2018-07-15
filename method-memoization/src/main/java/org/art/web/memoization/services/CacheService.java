package org.art.web.memoization.services;

import org.art.web.memoization.pojo.CacheStatistics;
import org.art.web.memoization.pojo.InvocationContext;

public interface CacheService {

    void put(InvocationContext invocationContext, Object result);

    Object get(InvocationContext invocationContext);

    CacheStatistics getCacheStatistics();

    int getCacheSize();

    int getCurrentCacheSize();

    void clearCache();
}
