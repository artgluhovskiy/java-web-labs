package org.art.web.memoization.jmx;

public interface MemoizationConfigMBean {

    void enableMemoization(boolean enable);

    boolean isMemoizationEnabled();
}
