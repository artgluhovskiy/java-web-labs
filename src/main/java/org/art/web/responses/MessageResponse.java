package org.art.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.entities.Message;

@Data
@AllArgsConstructor
public class MessageResponse {

    private Message message;
    private ResponseStatus responseStatus;
}
