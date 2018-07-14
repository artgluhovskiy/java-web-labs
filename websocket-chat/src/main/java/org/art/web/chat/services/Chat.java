package org.art.web.chat.services;

import org.art.web.chat.model.Message;
import org.art.web.chat.model.User;

import java.util.List;

public interface Chat {

    boolean subscribe(User user, Long roomId);
    boolean unSubscribe(User user, Long roomId);
    boolean isUserSubscribed(User user, Long roomId);
    void receiveMessage(User user, Long roomId, Message message);
    void sendMessage(Message message);
    List<Message> getMessages(User user, Long roomId);
    void addChatFilter(ChatFilter chatFilter);
}
