package org.art.web.profiling.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.profiling.annotations.Profiling;

public class DummyServiceImpl implements DummyService {

    private static final Logger LOG = LogManager.getLogger(DummyServiceImpl.class);

    @Profiling
    @Override
    public void profiledMethod() {
        LOG.debug("DummyServiceImpl: profiledMethod()");
        try {
            //Some work here...
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
            //NOP
        }
    }

    @Override
    public void regularMethod() {
        LOG.debug("DummyServiceImpl: regularMethod()");
        try {
            //Some work here...
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {
            //NOP
        }
    }
}
