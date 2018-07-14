package org.art.web.chat.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.art.web.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes(names = {"user"})
public class LoginController {

    private static final Logger LOG = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public RedirectView login(@RequestParam("login") String login, ModelMap model) {
        LOG.debug("In 'LoginController': login(...)");
        try {
            User user = userService.getUserByLogin(login);
            model.put("user", user);
        } catch (ServiceBusinessException e) {
            e.printStackTrace();
            return new RedirectView("main.do");
        } catch (ServiceSystemException e) {
            e.printStackTrace();
            return new RedirectView("main.do");
        }
        return new RedirectView("main.do");
    }
}
