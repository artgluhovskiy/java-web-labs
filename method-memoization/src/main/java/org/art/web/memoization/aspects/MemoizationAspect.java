package org.art.web.memoization.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.memoization.annotations.Memoize;
import org.art.web.memoization.jmx.MemoizationConfig;
import org.art.web.memoization.pojo.InvocationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private MemoizationConfig jmxConfig;

    private final Map<InvocationContext, Object> cache = new ConcurrentHashMap<>();

    private static final Object DUMMY = new Object();

    @PostConstruct
    public void init() {
        LOG.debug("MemoizationAspect: init()");
        //Memoization JMX Config bean registration
        try {
            this.jmxConfig = new MemoizationConfig();
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName mBeanName = new ObjectName("org.art.web.memoization.jmx:type=MemoizationConfig");
            platformMBeanServer.registerMBean(jmxConfig, mBeanName);
        } catch (Exception e) {
            LOG.error("MemoizationAspect: init() - initialization failed!", e);
            throw new BeanInitializationException("MemoizationAspect: initialization failed!");
        }
    }

    @Around("@annotation(org.art.web.memoization.annotations.Memoize) && execution(* *(..))")
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
}
