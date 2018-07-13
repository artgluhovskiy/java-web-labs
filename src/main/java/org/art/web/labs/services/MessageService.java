package org.art.web.labs.services;

import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.beans.Message;

public interface MessageService {

    Message createMessage(String messageText, User from, User to);
    void sendMessage(Message message);
    String getServiceId();
}
