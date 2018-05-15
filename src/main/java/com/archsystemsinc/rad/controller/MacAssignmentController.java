/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.ReportsForm;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class MacAssignmentController {

	private static final Logger log = Logger.getLogger(MacAssignmentController.class);
	
	@RequestMapping(value ={"/admin/macassignment", "/quality_manager/macassignment", "/cms_user/macassignment",
			 "/mac_admin/macassignment","/mac_user/macassignment","/quality_monitor/macassignment"}, method = RequestMethod.GET)		
	public String viewMacAssignmentScreenGetMethod(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewMacAssignmentScreenGetMethod <--");
		  
		HashMap<String,HashMap<Integer,String>> macJurisProgramMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP;
		ReportsForm reportsForm = new ReportsForm();
		QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = null;
		
		ArrayList<QamMacByJurisdictionReviewReport> qamMacJurisProgramList = new ArrayList<QamMacByJurisdictionReviewReport>();
		
		Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = new TreeMap<String, QamMacByJurisdictionReviewReport>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(String macJurisCode : macJurisProgramMap.keySet()) {
			
			String[] stringList = macJurisCode.split("_");
			String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(stringList[0]));
			String jurisdictionName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(stringList[1]));
			HashMap<Integer,String> jurisProgramMap = macJurisProgramMap.get(macJurisCode);
			
			for(Integer programId: jurisProgramMap.keySet()) {
				
				String programName = jurisProgramMap.get(programId);
				
				qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				qamMacByJurisdictionReviewReport.setMacName(macName);
				qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionName);
				qamMacByJurisdictionReviewReport.setProgram(programName);
				qamMacByJurisdictionReviewReport.setPlannedCalls(20);
				qamMacByJurisdictionReviewReport.setCreatedMethod("Auto");
				qamMacByJurisdictionReviewReport.setAssignedCalls(6);
				
				qamMacJurisProgramList.add(qamMacByJurisdictionReviewReport);
			}
		}
		model.addAttribute("reportsForm", reportsForm);
		
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		User userSearchObject = new User();
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		List<User> resultsMap = new ArrayList<User> ();
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
		ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
		
		resultsMap = responseEntity.getBody();
		List<User> userList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
		HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
		for(User userTemp: userList) {
			pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
		}
		
		
		try {
			model.addAttribute("pccContactPersonMap",mapper.writeValueAsString(pccContactPersonMap).replaceAll("'", " "));
			model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(qamMacJurisProgramList).replaceAll("'", " "));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
					
		return "mac_assignment";
	}
	
	@RequestMapping(value ={"/admin/savemacassignment", "/quality_manager/savemacassignment", "/cms_user/savemacassignment",
			 "/mac_admin/savemacassignment","/mac_user/savemacassignment","/quality_monitor/savemacassignment"}, method = RequestMethod.POST)		
	public String saveMacAssignmentScreenGetMethod(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewMacAssignmentScreenGetMethod <--");
		  
		HashMap<String,HashMap<Integer,String>> macJurisProgramMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP;
		ReportsForm reportsForm = new ReportsForm();
		QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = null;
		
		ArrayList<QamMacByJurisdictionReviewReport> qamMacJurisProgramList = new ArrayList<QamMacByJurisdictionReviewReport>();
		
		Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = new TreeMap<String, QamMacByJurisdictionReviewReport>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(String macJurisCode : macJurisProgramMap.keySet()) {
			
			String[] stringList = macJurisCode.split("_");
			String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(stringList[0]));
			String jurisdictionName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(stringList[1]));
			HashMap<Integer,String> jurisProgramMap = macJurisProgramMap.get(macJurisCode);
			
			for(Integer programId: jurisProgramMap.keySet()) {
				
				String programName = jurisProgramMap.get(programId);
				
				qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				qamMacByJurisdictionReviewReport.setMacName(macName);
				qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionName);
				qamMacByJurisdictionReviewReport.setProgram(programName);
				qamMacByJurisdictionReviewReport.setPlannedCalls(20);
				qamMacByJurisdictionReviewReport.setCreatedMethod("Auto");
				qamMacByJurisdictionReviewReport.setAssignedCalls(6);
				
				qamMacJurisProgramList.add(qamMacByJurisdictionReviewReport);
			}
		}
		model.addAttribute("reportsForm", reportsForm);
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		User userSearchObject = new User();
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		List<User> resultsMap = new ArrayList<User> ();
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		userSearchObject.setRoleString(UIGenericConstants.QUALITY_MONITOR_ROLE_STRING);
		ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
		
		resultsMap = responseEntity.getBody();
		List<User> userList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
		HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
		for(User userTemp: userList) {
			pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
		}
		
		
		try {
			model.addAttribute("pccContactPersonMap",pccContactPersonMap);
			model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(qamMacJurisProgramList).replaceAll("'", " "));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
					
		return "mac_assignment";
	}
	
	
}
