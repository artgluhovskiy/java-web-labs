package org.art.web.labs.services.dummy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.services.annotations.Profiling;

public class DummyServiceImpl implements DummyService {

    private static final Logger LOG = LogManager.getLogger(DummyServiceImpl.class);

    private String serviceName;

    public DummyServiceImpl() {
    }

    public DummyServiceImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Profiling
    @Override
    public String echo(String str) {
        return str;
    }

    @Profiling
    public String getHello() {
        return "Hello";
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
