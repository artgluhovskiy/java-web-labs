package org.art.web.labs.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.art.web.labs.web.controllers.ControllerConstants.MAIN;

@Controller
public class MainController {

    private static final Logger LOG = LogManager.getLogger(MainController.class);

    @RequestMapping(value = "/main.do", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        LOG.debug("In 'MainController': welcomePage(...)");
        model.addAttribute("message", "Hello from 'Main Controller'");
        return MAIN;
    }
}
