package org.art.web.websocket;

import lombok.Data;

@Data
public class SimpleMessage {

    private String from;
    private String text;
}
