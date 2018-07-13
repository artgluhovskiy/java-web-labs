package org.art.web.labs.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.web.labs.model.chat.Message;

@Data
@AllArgsConstructor
public class MessageResponse {

    private Message message;
    private ResponseStatus responseStatus;
}
