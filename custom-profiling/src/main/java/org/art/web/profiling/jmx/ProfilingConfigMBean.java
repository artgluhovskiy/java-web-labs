package org.art.web.profiling.jmx;

/**
 * JMX MBean Profiling Configuration interface.
 */
public interface ProfilingConfigMBean {

    void setEnabledProfiling(boolean enabledProfiling);

    boolean isEnabledProfiling();

    void printStatus();
}
