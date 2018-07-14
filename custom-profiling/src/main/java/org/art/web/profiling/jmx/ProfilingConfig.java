package org.art.web.profiling.jmx;

public class ProfilingConfig implements ProfilingConfigMBean {

    private boolean enabledProfiling = true;

    @Override
    public void setEnabledProfiling(boolean enabledProfiling) {
        this.enabledProfiling = enabledProfiling;
    }

    @Override
    public boolean isEnabledProfiling() {
        return enabledProfiling;
    }

    @Override
    public void printStatus() {
        System.out.println("Profiling config enabled: " + enabledProfiling);
    }
}
