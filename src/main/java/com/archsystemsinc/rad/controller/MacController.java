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
import com.archsystemsinc.rad.model.MacAssignmentObject;
import com.archsystemsinc.rad.model.MacInfo;
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
public class MacController {

	private static final Logger log = Logger.getLogger(MacController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	 
	@RequestMapping(value ={"/admin/macinfolist", "/quality_manager/macinfolist", "/cms_user/macinfolist",
			 "/mac_admin/macinfolist","/mac_user/macinfolist","/quality_monitor/macinfolist"})		
	public String getMacList(final Model model,HttpServletRequest request, Authentication authentication,HttpServletResponse response) {
		log.debug("--> getMacList Screen <--");
		
		HashMap<Integer,MacInfo> resultsMap = new HashMap<Integer,MacInfo>();
		
		List<MacInfo> macInfoTempList = null;
		
		
		ArrayList<MacInfo> resultsList= new ArrayList<MacInfo>();
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			
			String roles = authentication.getAuthorities().toString();
			Date today = new Date(); 			
			
			MacInfo macInfoSearchObject = new MacInfo();
						
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "macInfoList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, macInfoSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			macInfoTempList = responseEntity.getBody();
			
			List<MacInfo> macInfoObjectList = mapper.convertValue(macInfoTempList, new TypeReference<List<MacInfo> >() { });
			
			model.addAttribute("macObjectList",mapper.writeValueAsString(macInfoObjectList).replaceAll("'", " "));	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "macinfolist";
	}
	
	@RequestMapping(value ={"/admin/edit-macinfo/{id}", "/quality_manager/edit-macinfo/{id}", "/cms_user/edit-macinfo/{id}",
			 "/mac_admin/edit-macinfo/{id}","/mac_user/edit-macinfo/{id}","/quality_monitor/edit-macinfo/{id}"})		
	public String viewNewMacInfoScreenGetMethod(@PathVariable("id") final Integer macId,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewMacInfoScreenGetMethod <--");
		  		
		MacInfo macInfoObject = null;
		
		ArrayList<MacInfo> macInfoObjectList = new ArrayList<MacInfo>();
		
		
		List<User> macInfoListResultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
		ObjectMapper mapper = new ObjectMapper();
			
		MacInfo macInfoSearchObject = new MacInfo();
		macInfoSearchObject.setId(macId.longValue());
		
		
		String ROOT_URI_MAC_INFO = new String(HomeController.RAD_WS_URI + "searchMacInfo");
		ResponseEntity<MacInfo> macInfoResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_MAC_INFO, macInfoSearchObject, MacInfo.class);
		MacInfo macInfoReturnObject = macInfoResponseEntity.getBody();
		model.addAttribute("macinfo", macInfoReturnObject);
		return "macinfo";
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateMacInfo", "/quality_manager/saveOrUpdateMacInfo", "/cms_user/saveOrUpdateMacInfo",
			 "/mac_admin/saveOrUpdateMacInfo","/mac_user/saveOrUpdateMacInfo","/quality_monitor/saveOrUpdateMacInfo"})	
	public String savemacInfoScreenGetMethod(@ModelAttribute("macinfo") MacInfo macInfo, final BindingResult result,
			final RedirectAttributes redirectAttributes, final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {
		log.debug("--> savemacInfoScreenGetMethod <--");
		  
		String returnView = "";
		log.debug("--> saveorupdatemac <--");
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
			try {
				
				
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateMacInfo");
				
				Date currentDateTime = new Date();
				  
				String currentDateString = utilityFunctions.convertToStringFromDate(currentDateTime);
				
				if(macInfo.getId() == null || macInfo.getId()== 0) {
					macInfo.setCreatedBy(userForm.getUserName());
					macInfo.setCreatedDate(currentDateString);
				} else {
					macInfo.setUpdatedBy(userForm.getUserName());
					macInfo.setUpdateddDate(currentDateString);
				}
				
				
				
				ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, macInfo, String.class);
				
				if(macInfo.getId() == 0) {
					redirectAttributes.addFlashAttribute("success",
							"success.create.mac");
				} else {
					redirectAttributes.addFlashAttribute("success",
							"success.edit.mac");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "redirect:/"+userFolder+"/macinfolist";
			url = response.encodeRedirectURL(url);
			returnView =  url;
		

		return returnView;
		
	}
	
	 @RequestMapping(value ={"/admin/new-macinfo", "/quality_manager/new-macinfo", "/cms_user/new-macinfo",
			 "/mac_admin/new-macinfo","/mac_user/new-macinfo","/quality_monitor/new-macinfo"}, method = RequestMethod.GET)
	
	public String newMacInfoGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		MacInfo macInfo = new MacInfo();
		
		model.addAttribute("menu_highlight", "scorecard");
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		
		model.addAttribute("macinfo", macInfo);
		
		String roles = authentication.getAuthorities().toString();
		
		return "macinfo";
	}

	
	
	
}
