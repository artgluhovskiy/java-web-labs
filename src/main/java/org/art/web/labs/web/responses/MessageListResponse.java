package org.art.web.labs.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.web.labs.model.chat.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {

    private List<Message> messages;
    private ResponseStatus responseStatus;
}
