/**
 * 
 */
package com.archsystemsinc.rad.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.model.User;

/**
 * @author PrakashTotta
 *
 */
@Controller
public class TestController {

	private static final Logger log = Logger.getLogger(TestController.class);
	
	@RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
	public String showAdminDashboard(Model model) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  return "AdminDashboard";
	}
	
	@RequestMapping(value = "/admin/uploadFile", method = RequestMethod.GET)
	public String fileUpload(Model model) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  return "CSRListFileUpload";
	}
	
	@RequestMapping(value = "/admin/showCsrLists", method = RequestMethod.GET)
	public String showCsrLists(Model model) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  return "showCsrLists";
	}
}
