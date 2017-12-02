/**
 * 
 */
package com.archsystemsinc.rad.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.model.User;

/**
 * @author PrakashTotta
 *
 */
@Controller
public class HomeController {

	private static final Logger log = Logger.getLogger(HomeController.class);
	
	 @RequestMapping(value = "/admin/dashboard1")
	 public String showAdminDashboard(Model model) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  return "homepage";
	}
	
	/*@RequestMapping(value = "/admin/uploadFile", method = RequestMethod.GET)
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
	}*/
}
