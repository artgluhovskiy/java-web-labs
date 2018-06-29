package org.art.services.dummy;

public class DummyServiceImpl implements DummyService {

    private String serviceName;

    public DummyServiceImpl() {}

    public DummyServiceImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String echo(String str) {
        return str;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
