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
import com.archsystemsinc.rad.model.QamEnvironmentForm;
import com.archsystemsinc.rad.model.User;

@Controller
public class QamEnvironmentFormController {
	private static final Logger log = Logger.getLogger(QamEnvironmentFormController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	
	@RequestMapping(value ={"/admin/qamenvironmentform", "/quality_manager/qamenvironmentform", "/cms_user/qamenvironmentform",
			 "/mac_admin/qamenvironmentform","/mac_user/qamenvironmentform","/quality_monitor/qamenvironmentform"})	
	public String viewQamEnvironmentForm(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewQamEnvironmentForm <--");
		
		QamEnvironmentForm qamEnvironmentForm = new QamEnvironmentForm();
		
		
		User userObject = (User) session.getAttribute("LoggedInUserForm");
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);			
			
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		qamEnvironmentForm.setUserId(userObject.getId().intValue());
		model.addAttribute("qamEnvironmentForm", qamEnvironmentForm);
		//session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "qam_environment_change";
	}
}