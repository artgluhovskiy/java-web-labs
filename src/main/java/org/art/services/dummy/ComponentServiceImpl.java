package org.art.services.dummy;

import org.springframework.stereotype.Component;

@Component
public class ComponentServiceImpl implements ComponentService {

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
