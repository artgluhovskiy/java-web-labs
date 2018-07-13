package org.art.web.labs.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.web.websocket.OutputMessage;
import org.art.web.labs.web.websocket.SimpleMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebsocketMessageController {

    private static final Logger LOG = LogManager.getLogger(WebsocketMessageController.class);

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(SimpleMessage message) throws Exception {
        LOG.debug("In socket controller");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
