package com.archsystemsinc.rad.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.model.CsrUploadForm;
import com.archsystemsinc.rad.model.User;

@Controller
public class CsrListController {
	private static final Logger log = Logger.getLogger(CsrListController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	
	@RequestMapping(value ={"/admin/csrlist", "/quality_manager/csrlist", "/cms_user/csrlist",
			 "/mac_admin/csrlist","/mac_user/csrlist","/quality_monitor/csrlist"})	
	public String viewCSRList(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> showAdminDashboard <--");
		
		CsrUploadForm csrUploadForm = new CsrUploadForm();
		
		
		User userObject = (User) session.getAttribute("LoggedInUserForm");
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			session.getAttribute("LOGGED_IN_USER_MAC_MAP");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );			
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		csrUploadForm.setUserId(userObject.getId().intValue());
		model.addAttribute("csrUploadForm", csrUploadForm);
		//session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "csrlist";
	}
}