package com.archsystemsinc.rad.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrUploadForm;
import com.archsystemsinc.rad.model.QamEnvironmentForm;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP"));			
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		qamEnvironmentForm.setUserId(userObject.getId().intValue());
		model.addAttribute("qamEnvironmentForm", qamEnvironmentForm);
		//session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "qam_environment_change";
	}
	
	
	@RequestMapping(value ={"/admin/download-qamenvironmentform/{id}", "/quality_manager/download-qamenvironmentform", "/cms_user/download-qamenvironmentform/{id}",
			 "/mac_admin/download-qamenvironmentform/{id}","/mac_user/download-qamenvironmentform/{id}","/quality_monitor/download-qamenvironmentform/{id}"}, method = RequestMethod.GET)	
	public void downloadQamEnvironmentForm(@PathVariable("id") final Integer id, HttpSession session,HttpServletResponse response) {
		QamEnvironmentForm qamEnvironmentForm = new QamEnvironmentForm();
		ObjectMapper mapper = new ObjectMapper();
		qamEnvironmentForm.setQamEnvironmentChangeFormId(Long.valueOf(id));
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "findEnvironmentalChangeControlForm");
		ResponseEntity<QamEnvironmentForm> webServiceResponse = basicAuthRestTemplate.postForEntity(ROOT_URI, qamEnvironmentForm,QamEnvironmentForm.class);
		
		QamEnvironmentForm qamEnvironmentFormTemp = mapper.convertValue(webServiceResponse, new TypeReference<QamEnvironmentForm>() { });
		
		
		try {
			response.setContentType(qamEnvironmentFormTemp.getFileType());
			response.setContentLength(qamEnvironmentFormTemp.getDocumentContent().length);
			response.setHeader("Content-Disposition","attachment; filename=\"" + qamEnvironmentFormTemp.getDocumentName() +"\"");
						 
			response.addHeader("Content-Disposition","attachment; filename=\"" + qamEnvironmentFormTemp.getDocumentName() +"\"");
			response.addHeader("X-Frame-Options", "ALLOWALL");
						 
			InputStream targetStream = new ByteArrayInputStream(qamEnvironmentFormTemp.getDocumentContent());
			IOUtils.copy(targetStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}