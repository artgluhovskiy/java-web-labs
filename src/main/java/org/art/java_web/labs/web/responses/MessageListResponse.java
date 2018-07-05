package org.art.java_web.labs.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.java_web.labs.entities.chat.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {

    private List<Message> messages;
    private ResponseStatus responseStatus;
}
