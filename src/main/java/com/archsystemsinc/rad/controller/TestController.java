/**
 * 
 */
package com.archsystemsinc.rad.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author PrakashTotta
 *
 */
@Controller
public class TestController {

	private static final Logger log = Logger.getLogger(TestController.class);
	  @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
	public String showAdminDashboard() {
		  log.debug("--> showAdminDashboard <--");
		  return "AdminDashboard";
	}
}
