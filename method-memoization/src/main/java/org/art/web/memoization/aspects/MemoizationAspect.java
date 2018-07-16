package org.art.web.memoization.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.memoization.annotations.Memoize;
import org.art.web.memoization.jmx.MemoizationConfig;
import org.art.web.memoization.pojo.InvocationContext;
import org.art.web.memoization.services.CacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Provides the 'memoize' functionality for methods
 * annotated with {@link Memoize} annotation by means
 * of method result caching in order to avoid repeatable
 * time consuming processing.
 */
@Aspect
@Component
public class MemoizationAspect {

    private static final Logger LOG = LogManager.getLogger(MemoizationAspect.class);

    private static final String JMX_CONFIG_BEAN_NAME = "org.art.web.memoization.jmx:type=MemoizationConfig";

    private MemoizationConfig jmxConfig;

    private CacheService cache;

    @Autowired
    public MemoizationAspect(CacheService cache) {
        this.cache = cache;
    }

    @PostConstruct
    public void init() {
        LOG.debug("MemoizationAspect: init()");
        //Memoization JMX Config bean registration
        try {
            this.jmxConfig = new MemoizationConfig();
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName mBeanName = new ObjectName(JMX_CONFIG_BEAN_NAME);
            platformMBeanServer.registerMBean(jmxConfig, mBeanName);
        } catch (Exception e) {
            LOG.error("MemoizationAspect: init() - initialization failed!", e);
            throw new BeanInitializationException("MemoizationAspect: initialization failed!");
        }
    }

    @Around("execution(* *(..)) && @annotation(org.art.web.memoization.annotations.Memoize)")
    public Object memoizeResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        if (jmxConfig != null && jmxConfig.isMemoizationEnabled()) {
            Class<?> targetClass = joinPoint.getSignature().getDeclaringType();
            String targetMethod = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            InvocationContext invocationContext = new InvocationContext(targetClass, targetMethod, args);
            Object memoizedResult = cache.get(invocationContext);
            if (memoizedResult != null) {
                LOG.trace("MemoizationAspect: memoizeResult() - cache hit: {}", invocationContext);
                result = memoizedResult;
            } else {
                result = joinPoint.proceed();
                LOG.trace("MemoizationAspect: memoizeResult() - cache miss: {}", invocationContext);
                cache.put(invocationContext, result);
            }
        } else {
            result = joinPoint.proceed();
        }
        return result;
    }

    @PreDestroy
    public void destroy() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName mBeanName = new ObjectName(JMX_CONFIG_BEAN_NAME);
        platformMBeanServer.unregisterMBean(mBeanName);
    }
}
