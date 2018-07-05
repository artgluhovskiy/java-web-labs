package org.art.java_web.labs.services.chat;

import org.art.java_web.labs.entities.chat.Message;
import org.art.java_web.labs.entities.chat.User;

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
