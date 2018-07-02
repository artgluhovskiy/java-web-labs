package org.art.java_web.labs.web.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.art.java_web.labs.entities.Message;

@Data
@AllArgsConstructor
public class MessageResponse {

    private Message message;
    private ResponseStatus responseStatus;
}
