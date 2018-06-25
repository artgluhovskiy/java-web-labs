package org.art.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.entities.Message;

import java.util.List;

@Data
@AllArgsConstructor
public class MessageListResponse {

    private List<Message> messages;
    private ResponseStatus responseStatus;
}
