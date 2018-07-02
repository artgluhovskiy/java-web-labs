package org.art.java_web.labs.services.chat;

import org.art.java_web.labs.entities.Message;

public interface ChatFilter {

    void processMessage(Message message);
    void doChatFilter(ChatFilterChain filterChain);
}
