package org.art.java_web.labs.services.dummy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ComponentServiceImpl implements ComponentService {

    private static final Logger LOG = LogManager.getLogger(ComponentServiceImpl.class);

    private String serviceName;

    public ComponentServiceImpl() {}

    public ComponentServiceImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String echo(String str) {
        return null;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
