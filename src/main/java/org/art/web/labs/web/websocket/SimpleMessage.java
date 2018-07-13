package org.art.web.labs.web.websocket;

import lombok.Data;

@Data
public class SimpleMessage {

    private String from;
    private String text;
}
