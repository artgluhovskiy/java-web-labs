package org.art.web.labs.services.impl;

import org.art.web.labs.services.DateTimeService;

public class ServiceLocator {

    private static DateTimeService dateTimeService = new DateTimeServiceImpl("date_time_service_3_bean_factory");

    private ServiceLocator() {}

    public DateTimeService createDateTimeService() {
        return dateTimeService;
    }
}
