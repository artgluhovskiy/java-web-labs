package org.art.web.memoization.services;

import org.art.web.memoization.annotations.Memoize;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

public class FibonacciServiceImpl implements FibonacciService {

    /**
     * Calculates the n-th number in Fibonacci sequence.
     */
    @Memoize
    @Override
    public int calculate(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        /*
          Note: It is necessary to use explicit 'current proxy' calls to 'detect'
          recursive method invocations by Spring aspects. This solution totally
          couples the code to Spring AOP, and it makes the class itself aware of
          the fact that it is being used in an AOP context.
         */
        return ((FibonacciServiceImpl) AopContext.currentProxy()).calculate(n - 2) +
                ((FibonacciServiceImpl) AopContext.currentProxy()).calculate(n - 1);
    }
}
