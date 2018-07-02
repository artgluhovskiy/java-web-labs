package org.art.java_web.labs.web.bean_post_processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_web.labs.web.annotations.Profiling;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} implementation
 * that checks if a bean or some bean's method is annotated with {@link Profiling}
 * annotation. If so, creates dynamic JDK proxy which intercepts invoked method and
 * measures elapsed method invocation time.
 */
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOG = LogManager.getLogger(ProfilingAnnotationBeanPostProcessor.class);

    private final Map<String, Class> beans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOG.info("*** ProfilingAnnotationBeanPostProcessor. Bean: {}, before initialization...", beanName);
        //Check if bean class annotated with '@Profiling' annotation
        Class<?> beanClass = bean.getClass();
        Profiling targetAnnotation = beanClass.getAnnotation(Profiling.class);
        if (targetAnnotation != null) {
            beans.put(beanName, beanClass);
            return bean;
        }
        //Check if bean class has methods annotated with '@Profiling' annotation
        Method[] methods = beanClass.getDeclaredMethods();
        for (Method method : methods) {
            targetAnnotation = method.getAnnotation(Profiling.class);
            if (targetAnnotation != null) {
                beans.put(beanName, beanClass);
                return bean;
            }
        }
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOG.info("*** ProfilingAnnotationBeanPostProcessor. Bean: {}, after initialization...", beanName);
        if (beans.containsKey(beanName)) {
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        System.out.printf("Start profiling. Bean %s: method name: %s%n", beanName, method.getName());
                        System.out.println("*** Interfaces: " + bean.getClass().getInterfaces());
                        long start = System.nanoTime();
                        Method targetMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        targetMethod.invoke(bean, args);
                        long end = System.nanoTime();
                        System.out.printf("End profiling. Bean %s: method name: %s. Elapsed time: %d ms",
                                beanName, method.getName(), (end - start) / (long) 10e6);
                        return null;
                    });
        }
        return bean;
    }
}
