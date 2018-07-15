package org.art.web.memoization.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.memoization.pojo.CacheStatistics;
import org.art.web.memoization.pojo.InvocationContext;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple cache service implementation with FIFO cache policy
 * based on {@link LinkedHashMap} collection.
 */
public class CacheServiceImpl implements CacheService {

    private static final Logger LOG = LogManager.getLogger(CacheServiceImpl.class);

    private int cacheSize;
    private int hits;
    private int misses;

    private final Map<InvocationContext, Object> cache = Collections.synchronizedMap(
            new LinkedHashMap<InvocationContext, Object>() {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    boolean removeEldest = size() > cacheSize;
                    if (removeEldest) {
                        LOG.debug("Removing the eldest element from cache: {}", eldest);
                    }
                    return removeEldest;
                }
            });

    public CacheServiceImpl(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public void put(InvocationContext invocationContext, Object result) {
        cache.put(invocationContext, result);
    }

    @Override
    public Object get(InvocationContext invocationContext) {
        Object storedResult = cache.get(invocationContext);
        if (storedResult != null) {
            this.hits++;
        } else {
            this.misses++;
        }
        return storedResult;
    }

    @Override
    public CacheStatistics getCacheStatistics() {
        return new CacheStatistics(cacheSize, hits, misses);
    }

    @Override
    public int getCacheSize() {
        return cacheSize;
    }

    @Override
    public int getCurrentCacheSize() {
        return cache.size();
    }

    @Override
    public void clearCache() {
        this.cache.clear();
        this.hits = 0;
        this.misses = 0;
    }
}
