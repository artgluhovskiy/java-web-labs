package org.art.web.labs.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.model.chat.Message;
import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.chat.Chat;
import org.art.web.labs.web.responses.MessageListResponse;
import org.art.web.labs.web.responses.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SessionAttributes(names = {"user"})
public class MessageInitializerController {

    private static final Logger LOG = LogManager.getLogger(MessageInitializerController.class);

    private final Chat chat;

    @Autowired
    public MessageInitializerController(Chat chat) {
        this.chat = chat;
    }

    @PostMapping(value = "initMessages.do", produces = "application/json")
    public MessageListResponse initMessages(
            @RequestParam("chatRoom") String chatRoom,
            @SessionAttribute("user") User user) {

        List<Message> messages = chat.getMessages(user, 1L);
        return new MessageListResponse(messages, ResponseStatus.OK);
    }
}
