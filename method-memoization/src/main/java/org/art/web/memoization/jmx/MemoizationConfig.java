package org.art.web.memoization.jmx;

public class MemoizationConfig implements MemoizationConfigMBean {

    private boolean memoizationEnabled = true;

    @Override
    public void enableMemoization(boolean enable) {
        this.memoizationEnabled = enable;
    }

    @Override
    public boolean isMemoizationEnabled() {
        return this.memoizationEnabled;
    }
}
