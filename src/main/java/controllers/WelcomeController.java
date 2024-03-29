/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;

import domain.Actor;
import utilities.ControllerUtils;
import utilities.UserAccountUtils;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
    @RequestMapping(value = "/index")
    public ModelAndView index() throws LoginException
    {
        return new ModelAndView("welcome/index");
    }

    @RequestMapping(value = "/blocked")
    public ModelAndView blocked()
    {
        Actor principal = findPrincipal();
        if (principal != null && principal.getBanned()) {
            ModelAndView result = new ModelAndView("welcome/blocked");

            // Log user out.
            UserAccountUtils.setSessionAccount(null);
            return result;
        }

        // If not actually blocked, redirect to index.
        return ControllerUtils.redirect("/welcome/index.do");
    }
}
