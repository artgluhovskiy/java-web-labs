package org.art.web.labs.services.chat;

import org.art.web.labs.model.chat.Message;

public interface ChatFilter {

    void processMessage(Message message);
    void doChatFilter(ChatFilterChain filterChain);
}
