package org.art.web.chat.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.web.chat.model.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {

    private List<Message> messages;
    private ResponseStatus responseStatus;
}
