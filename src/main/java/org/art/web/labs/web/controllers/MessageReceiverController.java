package org.art.web.labs.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.labs.model.chat.Message;
import org.art.web.labs.model.chat.User;
import org.art.web.labs.services.chat.Chat;
import org.art.web.labs.web.responses.MessageResponse;
import org.art.web.labs.web.responses.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@SessionAttributes(names = {"user"})
public class MessageReceiverController {

    private static final Logger LOG = LogManager.getLogger(MessageReceiverController.class);

    private final Chat chat;

    @Autowired
    public MessageReceiverController(Chat chat) {
        this.chat = chat;
    }

    @PostMapping(value = "sendMessage.do", produces = "application/json")
    public MessageResponse receiveMessage(
            @RequestParam("message") String mes,
            @RequestParam("chatRoom") String chatRoom,
            @SessionAttribute(name = "user") User user) {

        LOG.debug("In 'ReceiveMessageController': receiveMessage(...)");
        Message message = new Message(user.getFullName(), chatRoom, LocalDate.now().toString(), mes);
        chat.receiveMessage(user, 1L, message);

        return new MessageResponse(message, ResponseStatus.OK);
    }
}
