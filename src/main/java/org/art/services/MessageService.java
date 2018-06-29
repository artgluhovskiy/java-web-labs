package org.art.services;

import org.art.entities.User;
import org.art.services.beans.Message;

public interface MessageService {

    Message createMessage(String messageText, User from, User to);
    void sendMessage(Message message);
    String getServiceId();
}
