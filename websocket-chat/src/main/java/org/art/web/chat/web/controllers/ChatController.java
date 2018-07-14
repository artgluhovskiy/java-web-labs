package org.art.web.chat.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.chat.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import static org.art.web.chat.web.controllers.ControllerConstants.CHAT;
import static org.art.web.chat.web.controllers.ControllerConstants.MAIN_REDIRECT;


@Controller
@SessionAttributes(names = {"user"})
public class ChatController {

    private static final Logger LOG = LogManager.getLogger(ChatController.class);

    @RequestMapping(value = "/chat.do", method = RequestMethod.GET)
    public String chat(@ModelAttribute("user") User user,
                       ModelMap model) {
        LOG.debug("In 'ChatController': chat(...)");
        if (user == null) {
            return MAIN_REDIRECT;
        } else {
            return CHAT;
        }
    }

    @ModelAttribute("user")
    public User getUser() {
        return null;
    }
}
