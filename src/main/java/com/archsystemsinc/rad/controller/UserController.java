package com.archsystemsinc.rad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This is the Spring Controller Class for User Login Functionality.
 */
@Controller
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);
	/**
	 * 
	 * This method provides the functionalities for the User to login to the
	 * application.
	 * 
	 * @param model
	 * @param error
	 * @param logout
	 * @return
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(Model model, String error) {
		log.debug("error::"+error);
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		

		return "login";
	}

	@RequestMapping(value = { "/Access_Denied" }, method = RequestMethod.GET)
	public String accessDenied(Model model, String error) {
		if (error != null)
			model.addAttribute("error", "Access Deinied.");
		return "login";
	}

	/**
	 * This method provides the functionalities for the User logout to the
	 * application and redirect to the login page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";

	}
}