package org.art.java_web.labs.services;

import org.art.java_web.labs.entities.chat.User;
import org.art.java_web.labs.services.beans.Message;

public interface MessageService {

    Message createMessage(String messageText, User from, User to);
    void sendMessage(Message message);
    String getServiceId();
}
