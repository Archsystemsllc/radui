package com.archsystemsinc.rad.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.model.SystemIssueForm;
import com.archsystemsinc.rad.model.User;

@Controller
public class SystemIssueFormController {
	private static final Logger log = Logger.getLogger(SystemIssueFormController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	
	@RequestMapping(value ={"/admin/systemissueform", "/quality_manager/systemissueform", "/cms_user/systemissueform",
			 "/mac_admin/systemissueform","/mac_user/systemissueform","/quality_monitor/systemissueform"})	
	public String viewSystemIssueForm(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewSystemIssueForm <--");
		
		SystemIssueForm systemIssueForm = new SystemIssueForm();
		
		
		User userObject = (User) session.getAttribute("LoggedInUserForm");
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);			
			
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		systemIssueForm.setUserId(userObject.getId().intValue());
		model.addAttribute("systemIssueForm", systemIssueForm);
		//session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "system_issue_form";
	}
}