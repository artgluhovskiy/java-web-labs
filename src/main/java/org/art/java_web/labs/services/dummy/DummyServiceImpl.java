package org.art.java_web.labs.services.dummy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.java_web.labs.web.annotations.Profiling;
import org.springframework.beans.factory.InitializingBean;

@Profiling
public class DummyServiceImpl implements DummyService, InitializingBean {

    private static final Logger LOG = LogManager.getLogger(DummyServiceImpl.class);

    private String serviceName;

    public DummyServiceImpl() {
    }

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

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("*** DummyServiceImpl. afterPropertiesSet()");
    }
}
