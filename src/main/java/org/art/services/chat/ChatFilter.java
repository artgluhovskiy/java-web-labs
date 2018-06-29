package org.art.services.chat;

import org.art.entities.Message;

public interface ChatFilter {

    void processMessage(Message message);
    void doChatFilter(ChatFilterChain filterChain);
}
