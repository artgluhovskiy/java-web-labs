package org.art.web.chat.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.web.chat.model.Message;

@Data
@AllArgsConstructor
public class MessageResponse {

    private Message message;
    private ResponseStatus responseStatus;
}
