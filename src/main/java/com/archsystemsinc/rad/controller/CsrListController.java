package com.archsystemsinc.rad.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.model.CsrUploadForm;

@Controller
public class CsrListController {
	private static final Logger log = Logger.getLogger(CsrListController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/admin/csrlist")
	public String viewCSRList(Model model, HttpSession session) {
		log.debug("--> showAdminDashboard <--");
		
		CsrUploadForm csrUploadForm = new CsrUploadForm();
		model.addAttribute("csrUploadForm", csrUploadForm);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "csrlist";
	}
}