/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;

import com.archsystemsinc.rad.model.JurisdictionInfo;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class JurisdictionController {

	private static final Logger log = Logger.getLogger(JurisdictionController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	 
	@RequestMapping(value ={"/admin/jurisdictionlist", "/quality_manager/jurisdictionlist", "/cms_user/jurisdictionlist",
			 "/mac_admin/jurisdictionlist","/mac_user/jurisdictionlist","/quality_monitor/jurisdictionlist"})		
	public String getJurisdictionList(final Model model,HttpServletRequest request, Authentication authentication,HttpServletResponse response) {
		log.debug("--> getJurisdictionList Screen <--");
		
		HashMap<Integer,JurisdictionInfo> resultsMap = new HashMap<Integer,JurisdictionInfo>();
		
		List<JurisdictionInfo> jurisdictionInfoTempList = null;
		
		
		ArrayList<JurisdictionInfo> resultsList= new ArrayList<JurisdictionInfo>();
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			
			String roles = authentication.getAuthorities().toString();
			Date today = new Date(); 			
			
			JurisdictionInfo jurisdictionInfoSearchObject = new JurisdictionInfo();
						
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "jurisdictionList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, jurisdictionInfoSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			jurisdictionInfoTempList = responseEntity.getBody();
			
			List<JurisdictionInfo> jurisdictionInfoObjectList = mapper.convertValue(jurisdictionInfoTempList, new TypeReference<List<JurisdictionInfo> >() { });
			
			model.addAttribute("jurisdictionObjectList",mapper.writeValueAsString(jurisdictionInfoObjectList).replaceAll("'", " "));	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "jurisdictionlist";
	}
	
	@RequestMapping(value ={"/admin/edit-jurisdiction/{id}", "/quality_manager/edit-jurisdiction/{id}", "/cms_user/edit-jurisdiction/{id}",
			 "/mac_admin/edit-jurisdiction/{id}","/mac_user/edit-jurisdiction/{id}","/quality_monitor/edit-jurisdiction/{id}"})		
	public String viewNewJurisdictionInfoScreenGetMethod(@PathVariable("id") final Integer jurisdictionId,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewJurisdictionInfoScreenGetMethod <--");
		  		
		JurisdictionInfo jurisdictionInfoObject = null;
		
		ArrayList<JurisdictionInfo> jurisdictionInfoObjectList = new ArrayList<JurisdictionInfo>();
		
		
		List<User> jurisdictionInfoListResultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
		ObjectMapper mapper = new ObjectMapper();
			
		JurisdictionInfo jurisdictionInfoSearchObject = new JurisdictionInfo();
		jurisdictionInfoSearchObject.setId(jurisdictionId.longValue());
		
		
		String ROOT_URI_MAC_INFO = new String(HomeController.RAD_WS_URI + "searchJurisdiction");
		ResponseEntity<JurisdictionInfo> jurisdictionInfoResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_MAC_INFO, jurisdictionInfoSearchObject, JurisdictionInfo.class);
		JurisdictionInfo jurisdictionInfoReturnObject = jurisdictionInfoResponseEntity.getBody();
		model.addAttribute("jurisdiction", jurisdictionInfoReturnObject);
		return "jurisdiction";
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateJurisdiction", "/quality_manager/saveOrUpdateJurisdiction", "/cms_user/saveOrUpdateJurisdiction",
			 "/mac_admin/saveOrUpdateJurisdiction","/mac_user/saveOrUpdateJurisdiction","/quality_monitor/saveOrUpdateJurisdiction"})	
	public String savejurisdictionInfoScreenGetMethod(@ModelAttribute("jurisdiction") JurisdictionInfo jurisdictionInfo, final BindingResult result,
			final RedirectAttributes redirectAttributes, final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {
		log.debug("--> savejurisdictionInfoScreenGetMethod <--");
		  
		String returnView = "";
		log.debug("--> saveorupdatejurisdiction <--");
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
			try {
				
				
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateJurisdiction");
				
				Date currentDateTime = new Date();
				  
				String currentDateString = utilityFunctions.convertToStringFromDate(currentDateTime);
				
				if(jurisdictionInfo.getId() == null || jurisdictionInfo.getId()== 0) {
					jurisdictionInfo.setCreatedBy(userForm.getUserName());
					jurisdictionInfo.setCreatedDate(currentDateString);
				} else {
					jurisdictionInfo.setUpdatedBy(userForm.getUserName());
					jurisdictionInfo.setUpdateddDate(currentDateString);
				}
				
				
				
				ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, jurisdictionInfo, String.class);
				
				if(jurisdictionInfo.getId()== null || jurisdictionInfo.getId() == 0) {
					redirectAttributes.addFlashAttribute("success",
							"success.create.jurisdiction");
				} else {
					redirectAttributes.addFlashAttribute("success",
							"success.edit.jurisdiction");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "redirect:/"+userFolder+"/jurisdictionlist";
			url = response.encodeRedirectURL(url);
			returnView =  url;
		

		return returnView;
		
	}
	
	 @RequestMapping(value ={"/admin/new-jurisdiction", "/quality_manager/new-jurisdiction", "/cms_user/new-jurisdiction",
			 "/mac_admin/new-jurisdiction","/mac_user/new-jurisdiction","/quality_monitor/new-jurisdiction"}, method = RequestMethod.GET)
	
	public String newJurisdictionInfoGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		JurisdictionInfo jurisdictionInfo = new JurisdictionInfo();
		
		model.addAttribute("menu_highlight", "scorecard");
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		
		model.addAttribute("jurisdiction", jurisdictionInfo);
		
		String roles = authentication.getAuthorities().toString();
		
		return "jurisdiction";
	}

	
	
	
}
