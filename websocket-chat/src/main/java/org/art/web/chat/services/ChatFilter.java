package org.art.web.chat.services;

import org.art.web.chat.model.Message;

public interface ChatFilter {

    void processMessage(Message message);
    void doChatFilter(ChatFilterChain filterChain);
}
