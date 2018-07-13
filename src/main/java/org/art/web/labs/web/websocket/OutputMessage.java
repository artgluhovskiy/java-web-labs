package org.art.web.labs.web.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputMessage {

    private String from;
    private String text;
    private String time;
}
