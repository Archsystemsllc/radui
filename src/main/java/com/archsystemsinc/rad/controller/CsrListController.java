package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrLists;
import com.archsystemsinc.rad.model.CsrUploadForm;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	
	@RequestMapping(value ={"/admin/csrListNamesUI", "/quality_manager/csrListNamesUI", "/cms_user/csrListNamesUI",
			 "/mac_admin/csrListNamesUI","/mac_user/csrListNamesUI","/quality_monitor/csrListNamesUI"}, method = RequestMethod.GET)	
	public @ResponseBody List<CsrLists> getCsrListNames(@RequestParam("term")  String nameLiteral,@RequestParam("macIdS") String macLookupId, 
			@RequestParam("jurisdictionS") String jurisdiction, @RequestParam("programS") String program) {
		
		
		CsrLists csrList = new CsrLists();
		List<CsrLists> csrListNames = new ArrayList<CsrLists>();
		try {
			csrList.setMacLookupId(Long.valueOf(macLookupId));
			csrList.setJurisdiction(jurisdiction);
			csrList.setProgram(program);
			csrList.setSearchStringLiteral(nameLiteral);
			List<CsrLists> resultsMap = new ArrayList<CsrLists> ();
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "csrListNames");
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, csrList, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			csrListNames = mapper.convertValue(resultsMap, new TypeReference<List<CsrLists>>() { });
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return csrListNames;
	}
}