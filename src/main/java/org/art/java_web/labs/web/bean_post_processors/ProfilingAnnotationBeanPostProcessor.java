package org.art.java_web.labs.web.bean_post_processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_web.labs.jmx.ProfilingConfig;
import org.art.java_web.labs.web.annotations.Profiling;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} implementation
 * that checks if a bean or some bean's method is annotated with {@link Profiling}
 * annotation. If so, creates dynamic JDK proxy which intercepts invoked method and
 * measures elapsed method invocation time.
 * Can be enabled/disabled via JMX {@link ProfilingConfig}.
 */
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOG = LogManager.getLogger(ProfilingAnnotationBeanPostProcessor.class);

    private ProfilingConfig profilingConfig;

    private final Map<String, Class> targetBeanClasses = new HashMap<>();

    private final Map<String, Set<String>> targetBeanMethods = new HashMap<>();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        //Profiling config JMX MBean registering
        this.profilingConfig = new ProfilingConfig();
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName mBeanName = new ObjectName("org.art.java_web.labs.jmx:type=ProfilingConfig");
        platformMBeanServer.registerMBean(profilingConfig, mBeanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOG.info("*** ProfilingAnnotationBeanPostProcessor. Bean: {}, before initialization...", beanName);
        //Check if bean class annotated with '@Profiling' annotation
        Class<?> beanClass = bean.getClass();
        Profiling targetAnnotation = beanClass.getAnnotation(Profiling.class);
        if (targetAnnotation != null) {
            targetBeanClasses.put(beanName, beanClass);
            return bean;
        }
        //Check if bean class has methods annotated with '@Profiling' annotation
        Method[] methods = beanClass.getDeclaredMethods();
        Set<String> targetMethods = new HashSet<>();
        for (Method method : methods) {
            targetAnnotation = method.getAnnotation(Profiling.class);
            if (targetAnnotation != null) {
                targetMethods.add(method.getName());
            }
        }
        if (!targetMethods.isEmpty()) {
            targetBeanMethods.put(beanName, targetMethods);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOG.info("*** ProfilingAnnotationBeanPostProcessor. Bean: {}, after initialization...", beanName);
        if (targetBeanClasses.containsKey(beanName)) {
            return createClassProfilingProxy(bean, beanName);
        } else if (targetBeanMethods.containsKey(beanName)) {
            Set<String> targetMethods = targetBeanMethods.get(beanName);
            return createMethodProfilingProxy(bean, beanName, targetMethods);
        }
        return bean;
    }

    private Object createClassProfilingProxy(Object bean, String beanName) {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                (proxy, method, args) -> invokeWithProfiling(bean, beanName, method, args));
    }

    private Object createMethodProfilingProxy(Object bean, String beanName, Set<String> targetMethods) {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    if (targetMethods.contains(method.getName())) {
                        return invokeWithProfiling(bean, beanName, method, args);
                    } else {
                        return ReflectionUtils.invokeMethod(method, bean, args);
                    }
                });
    }

    private Object invokeWithProfiling(Object bean, String beanName, Method method, Object[] args) {
        Object ret;
        if (profilingConfig.isEnabledProfiling()) {
            LOG.info("*** Start profiling. Bean {}: method name: {}", beanName, method.getName());
            long start = System.nanoTime();
            ret = ReflectionUtils.invokeMethod(method, bean, args);
            long end = System.nanoTime();
            LOG.info("*** End profiling. Bean {}: method name: {}. Elapsed time: {} mcs",
                    beanName, method.getName(), (end - start) / (long) 10e3);
        } else {
            ret = ReflectionUtils.invokeMethod(method, bean, args);
        }
        return ret;
    }
}
