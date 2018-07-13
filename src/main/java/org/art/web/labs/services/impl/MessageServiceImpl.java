package org.art.web.labs.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.DateTimeService;
import org.art.web.labs.services.MessageService;
import org.art.web.labs.services.beans.Message;

public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LogManager.getLogger(MessageServiceImpl.class);

    private DateTimeService dateTimeService;

    private String serviceId;

    public MessageServiceImpl(String serviceId) {
        this.serviceId = serviceId;
    }

    /* Service methods */

    @Override
    public Message createMessage(String messageText, User from, User to) {
        if (from == null && to == null) {
            throw new IllegalArgumentException();
        }
        return new Message(messageText, dateTimeService.getDateTime(), from, to);
    }

    @Override
    public void sendMessage(Message message) {
        LOG.info("*** Message Service: The message was sent: {}", message);
    }

    public void initMethod() {
        System.out.println("*** LIFE CYCLE: MESSAGE SERVICE: init() method");
    }

    public DateTimeService getDateTimeService() {
        return dateTimeService;
    }

    public void setDateTimeService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
