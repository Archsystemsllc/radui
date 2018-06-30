/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.Location;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class LocationController {

	private static final Logger log = Logger.getLogger(LocationController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	 
	@RequestMapping(value ={"/admin/locationlist", "/quality_manager/locationlist", "/cms_user/locationlist",
			 "/mac_admin/locationlist","/mac_user/locationlist","/quality_monitor/locationlist"})		
	public String getLocationList(final Model model,HttpServletRequest request, Authentication authentication,HttpServletResponse response) {
		log.debug("--> getLocationList Screen <--");
		
		HashMap<Integer,Location> resultsMap = new HashMap<Integer,Location>();
		
		List<Location> locationTempList = null;
		
		
		ArrayList<Location> resultsList= new ArrayList<Location>();
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			
			String roles = authentication.getAuthorities().toString();
			Date today = new Date(); 			
			
			Location locationSearchObject = new Location();
						
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "locationList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, locationSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			locationTempList = responseEntity.getBody();
			
			List<Location> locationObjectList = mapper.convertValue(locationTempList, new TypeReference<List<Location> >() { });
			
			model.addAttribute("locationObjectList",mapper.writeValueAsString(locationObjectList).replaceAll("'", " "));	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "locationlist";
	}
	
	@RequestMapping(value ={"/admin/edit-location/{id}", "/quality_manager/edit-location/{id}", "/cms_user/edit-location/{id}",
			 "/mac_admin/edit-location/{id}","/mac_user/edit-location/{id}","/quality_monitor/edit-location/{id}"})		
	public String viewNewLocationScreenGetMethod(@PathVariable("id") final Integer locationId,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewLocationScreenGetMethod <--");
		  		
		Location locationObject = null;
		
		ArrayList<Location> locationObjectList = new ArrayList<Location>();
		
		
		List<User> locationListResultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
		ObjectMapper mapper = new ObjectMapper();
			
		Location locationSearchObject = new Location();
		locationSearchObject.setId(locationId);
		
		
		String ROOT_URI_MAC_INFO = new String(HomeController.RAD_WS_URI + "searchLocation");
		ResponseEntity<Location> locationResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_MAC_INFO, locationSearchObject, Location.class);
		Location locationReturnObject = locationResponseEntity.getBody();
		model.addAttribute("location", locationReturnObject);
		return "location";
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateLocation", "/quality_manager/saveOrUpdateLocation", "/cms_user/saveOrUpdateLocation",
			 "/mac_admin/saveOrUpdateLocation","/mac_user/saveOrUpdateLocation","/quality_monitor/saveOrUpdateLocation"})	
	public String savelocationScreenGetMethod(@ModelAttribute("location") Location location, final BindingResult result,
			final RedirectAttributes redirectAttributes, final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {
		log.debug("--> savelocationScreenGetMethod <--");
		  
		String returnView = "";
		log.debug("--> saveorupdatelocation <--");
		HashMap<Integer,String> locationMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		
			try {
				
				
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateLocation");
				
				Date currentDateTime = new Date();
				  
				String currentDateString = utilityFunctions.convertToStringFromDate(currentDateTime);
				
				if(location.getId() == null || location.getId()== 0) {
					location.setCreatedBy(userForm.getUserName());
					location.setCreatedDate(currentDateString);
				} else {
					location.setUpdatedBy(userForm.getUserName());
					location.setUpdatedDate(currentDateString);
				}
				
				
				
				ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, location, String.class);
				
				if(location.getId()== null || location.getId() == 0) {
					redirectAttributes.addFlashAttribute("success",
							"success.create.location");
				} else {
					redirectAttributes.addFlashAttribute("success",
							"success.edit.location");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "redirect:/"+userFolder+"/locationlist";
			url = response.encodeRedirectURL(url);
			returnView =  url;
		

		return returnView;
		
	}
	
	 @RequestMapping(value ={"/admin/new-location", "/quality_manager/new-location", "/cms_user/new-location",
			 "/mac_admin/new-location","/mac_user/new-location","/quality_monitor/new-location"}, method = RequestMethod.GET)
	
	public String newLocationGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		Location location = new Location();
		
		model.addAttribute("menu_highlight", "scorecard");
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		
		model.addAttribute("location", location);
		
		String roles = authentication.getAuthorities().toString();
		
		return "location";
	}

	
	
	
}
