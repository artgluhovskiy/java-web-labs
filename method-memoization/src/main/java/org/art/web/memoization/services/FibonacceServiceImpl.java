package org.art.web.memoization.services;

import org.art.web.memoization.annotations.Memoize;
import org.springframework.stereotype.Service;

@Service
public class FibonacceServiceImpl implements FibonacciService {

    /**
     * Calculates the n-th number in Fibonacci sequence.
     */
    @Memoize
    @Override
    public int calculate(int n) {
        if (n < 2) {
            return 1;
        }
        return calculate(n - 1) + calculate(n - 2);
    }
}
