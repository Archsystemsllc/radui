package com.archsystemsinc.rad.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.client.RestClientException;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrLog;
import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ReportsForm;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportsController {
	private static final Logger log = Logger.getLogger(ReportsController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	
	 @RequestMapping(value ={"/admin/reports", "/quality_manager/reports", "/cms_user/reports",
			 "/mac_admin/reports","/mac_user/reports","/quality_monitor/reports"})		
	public String viewReports(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewReports <--");
		
		ReportsForm reportsForm = new ReportsForm();
		reportsForm.setMainReportSelect("ScoreCard");
		model.addAttribute("reportsForm", reportsForm);
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );
			
			String loggedInJurisIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");	
			
			String[] jurisIdStrings = loggedInJurisIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
			
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			
			for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(loggedInUserMacId.toString());
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);			
		}	
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		
		return "reports";
	}
	 
	 @RequestMapping(value ={"/mac_admin/reports_macadmin","/mac_user/reports_macadmin"})		
	public String viewMacAdminReports(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewReports MAC Admin <--");
		
		ReportsForm reportsForm = new ReportsForm();
		reportsForm.setMainReportSelect("ScoreCard");
		model.addAttribute("reportsForm", reportsForm);
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );
			
			String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");
			
			String[] jurisIdStrings = loggedInJurisdictionIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(loggedInUserMacId.toString());
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);		
			
		}	
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		
		return "reports_macadmin";
	}

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	
	 @RequestMapping(value ={"/admin/goBackMacJurisReport", "/quality_manager/goBackMacJurisReport", "/cms_user/goBackMacJurisReport",
			 "/mac_admin/goBackMacJurisReport","/mac_user/goBackMacJurisReport","/quality_monitor/goBackMacJurisReport"})		
	public String goBackGetMacJuris(final Model model,HttpSession session, Authentication authentication) {
		log.debug("--> showAdminDashboard <--");
		HashMap<Integer,String> locationMap = null;
		HashMap<Integer,String> jurisMap = null;
		HashMap<Integer,String> programMap = null;
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		ReportsForm reportsForm = (ReportsForm) session.getAttribute("ReportsFormSession");
		model.addAttribute("reportsForm", reportsForm);
		model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );
			
			//String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			programMap = new HashMap<Integer, String> ();
			locationMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
				jurIdArrayList.add(jurisIdSingle);
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			reportsForm.setJurIdList(jurIdArrayList);
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(loggedInUserMacId.toString());
			
		} else {
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				jurisMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getMacId()));
				
			} else {
				jurisMap = HomeController.JURISDICTION_MAP;
				
			}
			
			model.addAttribute("jurisMapEdit", jurisMap);
			
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(reportsForm.getMacId()+"_"+reportsForm.getJurisId());
				
			} else {
				programMap = HomeController.ALL_PROGRAM_MAP;
				
			}
			
			model.addAttribute("programMapEdit", programMap);
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
							&& !reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				locationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(reportsForm.getMacId())+"_"+Integer.valueOf(reportsForm.getJurisId())+"_"+Integer.valueOf(reportsForm.getProgramId()));			
				
			} else {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			}
			
			model.addAttribute("locationMapEdit", locationMap);
		}
		
		
		return "reports";
	}
	 
	@RequestMapping(value ={"/mac_admin/goBackMacAdminReports","/mac_user/goBackMacAdminReports"})		
	public String goBackMacAdminReports(final Model model,HttpSession session, Authentication authentication) {
		log.debug("--> showAdminDashboard <--");
		HashMap<Integer,String> locationMap = null;
		HashMap<Integer,String> jurisMap = null;
		HashMap<Integer,String> programMap = null;
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		ReportsForm reportsForm = (ReportsForm) session.getAttribute("ReportsFormSession");
		model.addAttribute("reportsForm", reportsForm);
		model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );
			
			//String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			programMap = new HashMap<Integer, String> ();
			locationMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			
			for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
				jurIdArrayList.add(jurisIdSingle);
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			reportsForm.setJurIdList(jurIdArrayList);
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(loggedInUserMacId.toString());
			
		} else {
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				jurisMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getMacId()));
				
			} else {
				jurisMap = HomeController.JURISDICTION_MAP;
				
			}
			
			model.addAttribute("jurisMapEdit", jurisMap);
			
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(reportsForm.getMacId()+"_"+reportsForm.getJurisId());
				
			} else {
				programMap = HomeController.ALL_PROGRAM_MAP;
				
			}
			
			model.addAttribute("programMapEdit", programMap);
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)
							&& !reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				locationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(reportsForm.getMacId())+"_"+Integer.valueOf(reportsForm.getJurisId())+"_"+Integer.valueOf(reportsForm.getProgramId()));			
				
			} else {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			}
			
			model.addAttribute("locationMapEdit", locationMap);
		}
		
		
		return "reports_macadmin";
	}
	
	
	
	
	 @RequestMapping(value ={"/admin/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}", "/quality_manager/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}", 
			 "/cms_user/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}", "/mac_admin/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}",
			 "/mac_user/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}","/quality_monitor/mac-jur-report-drilldown/{macName}/{jurisName}/{programName}/{searchString}"})	
	public String macJurReportDrillDown(@PathVariable("macName") final String macName, @PathVariable("jurisName") final String jurisName, 
			@PathVariable("programName") final String programName, @PathVariable("searchString") final String searchString, final Model model,HttpSession session) {
		
		try {
			HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
			HashMap<String,ArrayList<ScoreCard>> scoreCardSessionMap = (HashMap<String,ArrayList<ScoreCard>>) session.getAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT");
			ArrayList<ScoreCard> scoreCardList = scoreCardSessionMap.get(macName+"_"+jurisName+"_"+programName);
			
			for(ScoreCard scoreCard: scoreCardList) {
				scoreCard.setMacName(macName);
				scoreCard.setJurisdictionName(jurisName);
				
				String qamStartdateTimeString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime(), UIGenericConstants.DATE_TYPE_FULL);
				scoreCard.setQamStartdateTimeString(qamStartdateTimeString);
				if(searchString.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
					resultsMap.put(scoreCard.getId(), scoreCard);
				} else if(searchString.equalsIgnoreCase("ScoreableOnly")) {
					if(scoreCard.getScorecardType().equalsIgnoreCase("Scoreable"))
						resultsMap.put(scoreCard.getId(), scoreCard);
				}else if(searchString.equalsIgnoreCase("ScoreablePass")) {
					if(scoreCard.getFinalScoreCardStatus().equalsIgnoreCase("Pass")) 
					 resultsMap.put(scoreCard.getId(), scoreCard);
				}else if(searchString.equalsIgnoreCase("ScoreableFail")) {
					if(scoreCard.getFinalScoreCardStatus().equalsIgnoreCase("Fail"))
							 resultsMap.put(scoreCard.getId(), scoreCard);
				} else if(searchString.equalsIgnoreCase("Non-Scoreable")) {
					if(scoreCard.getScorecardType().equalsIgnoreCase("Non-Scoreable"))
						 resultsMap.put(scoreCard.getId(), scoreCard);
				} else if(searchString.equalsIgnoreCase("Does Not Count")) {
					if(scoreCard.getScorecardType().equalsIgnoreCase("Does Not Count"))
						 resultsMap.put(scoreCard.getId(), scoreCard);
				}			
			}
			
			ObjectMapper mapper = new ObjectMapper();
			//Convert the result set to string and replace single character with spaces
			model.addAttribute("scoreCardList",mapper.writeValueAsString(resultsMap.values()).replaceAll("'", " "));
			//session.setAttribute("SESSION_SCOPE_SCORECARDS_MAP",mapper.writeValueAsString(resultsMap.values()).replaceAll("'", " "));
			session.setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
			model.addAttribute("ReportSearchString",searchString);
			model.addAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
			model.addAttribute("ReportFlag",true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "scorecardlist";
	}
	 
	 @RequestMapping(value ={"/admin/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}", "/quality_manager/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}", 
			 "/cms_user/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}", "/mac_admin/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}",
			 "/mac_user/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}","/quality_monitor/rebuttal-report-drilldown/{macId}/{jurisId}/{callCategoryId}/{rebuttalStatus}"})	
	public String rebuttalReportDrillDown(@PathVariable("macId") final String macIdString, @PathVariable("jurisId") final String jurIdString, @PathVariable("callCategoryId") final String callCategoryId,@PathVariable("rebuttalStatus") final String rebuttalStatus, final Model model,HttpSession session) {
		
		HashMap<Integer,Rebuttal> resultsMap = new HashMap<Integer,Rebuttal>();
		HashMap<String,ArrayList<Rebuttal>> rebuttalSessionMap = (HashMap<String,ArrayList<Rebuttal>>) session.getAttribute("REBUTTAL_MAC_JURIS_REPORT_SESSION_OBJECT");
		ArrayList<Rebuttal> rebuttalList = rebuttalSessionMap.get(macIdString+"_"+jurIdString);
		
		for(Rebuttal rebuttal: rebuttalList) {
			if(callCategoryId.equalsIgnoreCase(UIGenericConstants.ALL_STRING) && rebuttalStatus.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				resultsMap.put(rebuttal.getId(), rebuttal);
			} else if(callCategoryId.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				if(rebuttal.getRebuttalStatus().equalsIgnoreCase(rebuttalStatus))
					resultsMap.put(rebuttal.getId(), rebuttal);
			}else if(rebuttalStatus.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
				if(rebuttal.getCallCategory().equalsIgnoreCase(callCategoryId)) 
					resultsMap.put(rebuttal.getId(), rebuttal);
			}else {
				if((rebuttal.getCallCategory().equalsIgnoreCase(callCategoryId) && rebuttal.getRebuttalStatus().equalsIgnoreCase(rebuttalStatus)))
						resultsMap.put(rebuttal.getId(), rebuttal);
			} 		
		}
		session.setAttribute("SESSION_SCOPE_REBUTTALS_REPORT_MAP", resultsMap);
		model.addAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		model.addAttribute("ReportFlag",true);
		
		return "rebuttalreportlist";
	}
	 
 
	@RequestMapping(value ={"/admin/getMacJurisReport", "/quality_manager/getMacJurisReport", "/cms_user/getMacJurisReport",
			 "/mac_admin/getMacJurisReport","/mac_user/getMacJurisReport","/quality_monitor/getMacJurisReport"})			
	public String getMacJurisReport(@ModelAttribute("reportsForm") ReportsForm reportsForm,  final BindingResult result,
			final Model model, HttpServletRequest request, HttpSession session, Authentication authentication) {

		String returnView = "";
		log.debug("--> getMacJurisReport <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		
		HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
		HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();
		String roles = authentication.getAuthorities().toString();
			
		try {
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
				SimpleDateFormat myMonthYearPathFormat = new SimpleDateFormat("yyyy-MM_dd hh:mm:ss a");
							
				if(reportsForm.getFromDateStringMonthYear() != null && 
						!reportsForm.getFromDateStringMonthYear().equalsIgnoreCase("")) {
					
					String filterFromDateString = reportsForm.getFromDateStringMonthYear()+"_01 00:00:00 AM";
					Date filterFromDate = myMonthYearPathFormat.parse(filterFromDateString);
					reportsForm.setFromDate(filterFromDate);					
				}
				
				
				if(reportsForm.getToDateStringMonthYear() != null && 
						!reportsForm.getToDateStringMonthYear().equalsIgnoreCase("")) {
					String filterToDateString = reportsForm.getToDateStringMonthYear()+"_15 23:59:59 PM";
					Date filterToDate = myMonthYearPathFormat.parse(filterToDateString);					
					Calendar calendar = Calendar.getInstance();  					
					calendar.setTime(filterToDate);  

				    calendar.add(Calendar.MONTH, 1);  
				    calendar.set(Calendar.DAY_OF_MONTH, 1);  
				    calendar.add(Calendar.DATE, -1);  
				    Date lastDayOfMonth = calendar.getTime();  
				    reportsForm.setToDate(lastDayOfMonth);			
				}
				
			} else {
				if(reportsForm.getFromDateString() != null && 
						!reportsForm.getFromDateString().equalsIgnoreCase("")) {
					String filterFromDateString = reportsForm.getFromDateString() + " 00:00:00 AM";
					Date filterFromDate = utilityFunctions.convertToDateFromString(filterFromDateString, UIGenericConstants.DATE_TYPE_FULL);
					reportsForm.setFromDate(filterFromDate);			
				}
				
				
				if(reportsForm.getToDateString() != null && 
						!reportsForm.getToDateString().equalsIgnoreCase("")) {
					String filterFromDateString = reportsForm.getToDateString() + " 11:59:59 PM";
					Date filterToDate = utilityFunctions.convertToDateFromString(filterFromDateString, UIGenericConstants.DATE_TYPE_FULL);
					reportsForm.setToDate(filterToDate);
				}
			}
			
			
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				User userFormSession = (User) session.getAttribute("LoggedInUserForm");
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				
				
				reportsForm.setMacId(loggedInUserMacId.toString());
				String[] jurisIds = reportsForm.getJurisdictionIds();
				
				String jurIds = reportsForm.getJurisdictionIds().toString();
				
				ArrayList<Integer> jurIdArrayList = new ArrayList<Integer>();
				if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
					String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getJurisId()));
					reportsForm.setJurisdictionNameValues(jurisdictionTempName);
					reportsForm.setJurisdictionName(jurisdictionTempName);
					
				} else {
					if(jurisIds[0].equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
						reportsForm.setJurisdictionName(UIGenericConstants.ALL_STRING);
						
						HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
						
						for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
							jurIdArrayList.add(jurisIdSingle);
							
							if(reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
								reportsForm.setProgramName(reportsForm.getProgramId());
								HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
								if (programTempMap == null) continue;
								
								programMap.putAll(programTempMap);
								if(reportsForm.getPccLocationId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
									for(Integer programIdSingle: programTempMap.keySet()) {
										HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
										if (locationTempMap == null) continue;
										locationMap.putAll(locationTempMap);
										locationTempMap = null;
									}
								}
								
								programTempMap = null;
							}						
						}
						reportsForm.setJurIdList(jurIdArrayList);			
						
					} else {
						
							String jurisdictionNamesValues = "";
							for (String jurisIdSingleValue: jurisIds) {
								jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));
								
								String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));
								
								jurisdictionNamesValues += jurisdictionTempName+ " ";
							}
							
							reportsForm.setJurIdList(jurIdArrayList);		
							reportsForm.setJurisdictionNameValues(jurisdictionNamesValues);
							reportsForm.setJurisdictionName(jurisdictionNamesValues);
						}
						
						
						
						if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
							String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
							reportsForm.setMacName(macName);
						} else {
							reportsForm.setMacName(UIGenericConstants.ALL_STRING);
						}
						
						if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
							String programName = HomeController.ALL_PROGRAM_MAP.get(Integer.valueOf(reportsForm.getProgramId()));				
							reportsForm.setProgramName(programName);
						}  else {
							reportsForm.setProgramName(UIGenericConstants.ALL_STRING);
						}
						
						if (!reportsForm.getPccLocationId().equalsIgnoreCase("") && !reportsForm.getPccLocationId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
							String pccLocationName = HomeController.PCC_LOC_MAP.get(Integer.valueOf(reportsForm.getPccLocationId()));				
							reportsForm.setPccLocationName(pccLocationName);
						} else {
							reportsForm.setPccLocationName(UIGenericConstants.ALL_STRING);
						}
					}				
				
				
				
			} else {
				
				if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
					String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getJurisId()));
					reportsForm.setJurisdictionNameValues(jurisdictionTempName);
					reportsForm.setJurisdictionName(jurisdictionTempName);
					
				} else {
					if (reportsForm.getJurisdictionIds() != null ) {
						
						String[] jurisIds = reportsForm.getJurisdictionIds();
						
						ArrayList<Integer> jurisIdArrayList = new ArrayList<Integer> ();
						ArrayList<String> jurisdictionNameArrayList = new ArrayList<String> ();
						String jurisdictionNamesValues = "";
						for (String jurisIdSingleValue: jurisIds) {
							if(jurisIdSingleValue.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
								jurisdictionNamesValues = UIGenericConstants.ALL_STRING;
								break;
							}
							jurisIdArrayList.add(Integer.valueOf(jurisIdSingleValue));
							String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));
							jurisdictionNameArrayList.add(jurisdictionTempName);
							jurisdictionNamesValues += jurisdictionTempName+ " ";
						}
						reportsForm.setJurIdList(jurisIdArrayList);			
						reportsForm.setJurisdictionNameValues(jurisdictionNamesValues);
						reportsForm.setJurisdictionNameList(jurisdictionNameArrayList);
						reportsForm.setJurisdictionName(jurisdictionNamesValues);
						reportsForm.setJurisId("");
					} 
					
				}
				
				
				if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
					String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
					reportsForm.setMacName(macName);
				} else {
					reportsForm.setMacName(UIGenericConstants.ALL_STRING);
				}
				
				if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
					String programName = HomeController.ALL_PROGRAM_MAP.get(Integer.valueOf(reportsForm.getProgramId()));				
					reportsForm.setProgramName(programName);
				} else {
					reportsForm.setProgramName(UIGenericConstants.ALL_STRING);
				}
				
				if (!reportsForm.getPccLocationId().equalsIgnoreCase("") && !reportsForm.getPccLocationId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
					String pccLocationName = HomeController.PCC_LOC_MAP.get(Integer.valueOf(reportsForm.getPccLocationId()));				
					reportsForm.setPccLocationName(pccLocationName);
				} else {
					reportsForm.setPccLocationName(UIGenericConstants.ALL_STRING);
				}
				
			}
			////
			
			Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = new TreeMap<String, QamMacByJurisdictionReviewReport>(
					new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							return o1.compareTo(o2);
						}

					});
			
			ObjectMapper mapper = new ObjectMapper();
			if(reportsForm.getMainReportSelect()==null || reportsForm.getMainReportSelect().equalsIgnoreCase("ScoreCard")) {
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getMacJurisReport");
				
				if(reportsForm.isReasonReportFlag()) {
					reportsForm.setJurIdList(null);
					reportsForm.setProgramId(null);
				}
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				
				if(reportsForm.isReasonReportFlag()) {
					finalResultsMap = generateNonScoreableReasonReport(scoreCardList);
					returnView = "nonscoreable_reasonreport";
				} else {
					finalResultsMap = generateScoreCardReport(scoreCardList,session);
					returnView = "scorecardreports";
					finalResultsMap = generateScoreCardReportSummary(finalResultsMap);
				}				
				
				finalSortedMap.putAll(finalResultsMap);
				
				if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
					model.addAttribute("AllScoreCardReport_All",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (All Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("AllScoreCardReport_Pass",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allPassScoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Pass Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase("Fail")) {
					model.addAttribute("AllScoreCardReport_Fail",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allFailScorecardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Fail Records)");
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Both Pass and Fail Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("ScoreablePassReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreablePassReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Pass Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Fail") ) {
					
					model.addAttribute("ScoreableFailReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableFailReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Fail Records)");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Non-Scoreable")) {
					model.addAttribute("NonScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("nonScoreableList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					if(reportsForm.isReasonReportFlag()) {
						model.addAttribute("ReportTitle","CRAD Non-Scoreable Reason Report");
					} else {
						model.addAttribute("ReportTitle","Scorecard Report - Non-Scoreable Records");
					}
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Does Not Count")) {
					model.addAttribute("DoesNotCountReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("doesNotCountList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					
					model.addAttribute("ReportTitle","Scorecard Report - Does Not Count Records");
				}
				session.setAttribute("SS_MAC_JURIS_REPORT", finalSortedMap);
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Summary")) {
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getMacJurisReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				reportsForm.setScoreCardType("Scoreable");
				finalResultsMap = generateQamSummaryReport(reportsForm, scoreCardList,session);
				
				returnView = "summaryreports";
				
				reportsForm.setCallResult(UIGenericConstants.ALL_STRING);
				finalSortedMap.putAll(finalResultsMap);
				
				if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") ) {
					model.addAttribute("SummaryScoreableReport",true);
					model.addAttribute("QAM_SUMMARY_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","QAM Summary Report");
					
				}  
				
				session.setAttribute("SS_QAM_SUMMARY_REPORT", finalSortedMap);			
				
			}
			else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Review")) {
							
				finalResultsMap = generateReviewReport(reportsForm);
				returnView = "review_reports";
				
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("ReportTitle","CRAD MAC Review Report");
				if(reportsForm.getMacId() != null && !reportsForm.getMacId().equalsIgnoreCase("All") && reportsForm.getJurIdList() == null) {
					model.addAttribute("MacReviewReport",true);
					
					model.addAttribute("macReviewReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
				} else if(reportsForm.getMacId() != null && reportsForm.getMacId().equalsIgnoreCase("All") && reportsForm.getJurIdList() == null) {
					finalResultsMap = generateScoreCardReportSummary(finalResultsMap);
					model.addAttribute("MacReviewReport",true);
					model.addAttribute("macReviewReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
				} else if(reportsForm.getJurIdList() != null) {
					finalResultsMap = generateScoreCardReportSummary(finalResultsMap);
					model.addAttribute("MacJurisdictionReviewReport",true);					
					model.addAttribute("macJurisdictionReviewReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				}
				
				
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Compliance")) {
				
				returnView = "compliancereports";
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getComplianceReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<CsrLog> complianceList = mapper.convertValue(resultsMap.values(), new TypeReference<List<CsrLog>>() { });
				
				finalResultsMap = generateComplianceReport(complianceList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("COMPLIANCE_REPORT",finalSortedMap);
				model.addAttribute("ComplianceReport",true);
				model.addAttribute("complianceReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				session.setAttribute("SS_COMPLIANCE_REPORT", finalSortedMap);
				
				if(reportsForm.getComplianceReportType().equalsIgnoreCase("ALL")) {
					model.addAttribute("ReportTitle","Compliance Report (ALL)");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Compliant")) {
					model.addAttribute("ReportTitle","Compliance Report");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Non-Compliant")) {
					model.addAttribute("ReportTitle","Non-Compliance Report");
				}
				
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				
				returnView = "rebuttalreports";
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getRebuttalReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<Rebuttal> rebuttalList = mapper.convertValue(resultsMap.values(), new TypeReference<List<Rebuttal>>() { });
				
				finalResultsMap = generateRebuttalReport(rebuttalList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("REBUTTAL_REPORT",finalSortedMap);
				model.addAttribute("RebuttalReport",true);
				
				model.addAttribute("rebuttalReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				session.setAttribute("SS_REBUTTAL_REPORT", finalSortedMap);
				
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
				
				returnView = "qaspreports";
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getQaspReport");
				//reportsForm.setScoreCardType("Scoreable");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<ScoreCard> qaspList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				
				finalResultsMap = generateQaspReport(qaspList);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("QASP_REPORT",finalSortedMap);
				model.addAttribute("QaspReport",true);				
				model.addAttribute("qaspReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				session.setAttribute("SS_QASP_REPORT", finalSortedMap);
				
				model.addAttribute("ReportTitle","QASP Report");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("ReportsFormSession", reportsForm);
		model.addAttribute("reportsForm", reportsForm);
		return returnView;
	}
	
	
	@RequestMapping(value ={"/admin/getMacAdminReport", "/quality_manager/getMacAdminReport", "/cms_user/getMacAdminReport",
			 "/mac_admin/getMacAdminReport","/mac_user/getMacAdminReport","/quality_monitor/getMacAdminReport"})			
	public String getReportForMacAdmin(@ModelAttribute("reportsForm") ReportsForm reportsForm,  final BindingResult result,
			final Model model, HttpServletRequest request, HttpSession session, Authentication authentication) {

		String returnView = "";
		log.debug("--> getMacAdminReport <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		
		HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
		HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();
		String roles = authentication.getAuthorities().toString();
			
		try {
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			if(reportsForm.getFromDateString() != null && 
					!reportsForm.getFromDateString().equalsIgnoreCase("")) {
				String filterFromDateString = reportsForm.getFromDateString() + " 00:00:00 AM";
				Date filterFromDate = utilityFunctions.convertToDateFromString(filterFromDateString, UIGenericConstants.DATE_TYPE_FULL);
				reportsForm.setFromDate(filterFromDate);			
			}
			
			
			if(reportsForm.getToDateString() != null && 
					!reportsForm.getToDateString().equalsIgnoreCase("")) {
				String filterFromDateString = reportsForm.getToDateString() + " 11:59:59 PM";
				Date filterToDate = utilityFunctions.convertToDateFromString(filterFromDateString, UIGenericConstants.DATE_TYPE_FULL);
				reportsForm.setToDate(filterToDate);
			}
			
			reportsForm.setCallResult(UIGenericConstants.ALL_STRING);
			reportsForm.setScoreCardType("Scoreable");
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				User userFormSession = (User) session.getAttribute("LoggedInUserForm");
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
				
				reportsForm.setMacId(loggedInUserMacId.toString());
				String[] jurisIds = reportsForm.getJurisdictionIds();
				
				String jurIds = reportsForm.getJurisdictionIds().toString();
				
				ArrayList<Integer> jurIdArrayList = new ArrayList<Integer>();
				if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
					String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getJurisId()));
					reportsForm.setJurisdictionNameValues(jurisdictionTempName);
					reportsForm.setJurisdictionName(jurisdictionTempName);
					
				} else {
					if(jurisIds !=null && jurisIds.length>0 && jurisIds[0].equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
						reportsForm.setJurisdictionName(UIGenericConstants.ALL_STRING);
						
						for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
							jurIdArrayList.add(jurisIdSingle);
							
							if(reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
								reportsForm.setProgramName(reportsForm.getProgramId());
								HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
								if (programTempMap == null) continue;								
								programMap.putAll(programTempMap);								
								programTempMap = null;
							}						
						}
						reportsForm.setJurIdList(jurIdArrayList);			
						
					} else {
						
							String jurisdictionNamesValues = "";
							for (String jurisIdSingleValue: jurisIds) {
								jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));								
								String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));								
								jurisdictionNamesValues += jurisdictionTempName+ " ";
							}
							
							reportsForm.setJurIdList(jurIdArrayList);		
							reportsForm.setJurisdictionNameValues(jurisdictionNamesValues);
							reportsForm.setJurisdictionName(jurisdictionNamesValues);
						}					
						
						
						if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
							String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
							reportsForm.setMacName(macName);
						} else {
							reportsForm.setMacName(UIGenericConstants.ALL_STRING);
						}
						
						if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase(UIGenericConstants.ALL_STRING) ) {
							String programName = HomeController.ALL_PROGRAM_MAP.get(Integer.valueOf(reportsForm.getProgramId()));				
							reportsForm.setProgramName(programName);
						}  else {
							reportsForm.setProgramName(UIGenericConstants.ALL_STRING);
						}
					}
			} 
			////
			
			Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = new TreeMap<String, QamMacByJurisdictionReviewReport>(
					new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							return o1.compareTo(o2);
						}

					});
			
			if(reportsForm.getMainReportSelect()==null || reportsForm.getMainReportSelect().equalsIgnoreCase("ScoreCard")) {
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getMacJurisReport");
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				finalResultsMap = generateMacAdminScoreCardReport(scoreCardList,session);				
				returnView = "reports_macadmin_results";				
				finalSortedMap.putAll(finalResultsMap);
				
				model.addAttribute("AllScoreCardReport_All",true);
				model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);					
				model.addAttribute("scoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				model.addAttribute("ReportTitle","Scorecard Report");
				  
				session.setAttribute("SS_MAC_JURIS_REPORT", finalSortedMap);
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				
				returnView = "rebuttalreports";				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getRebuttalReport");				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<Rebuttal> rebuttalList = mapper.convertValue(resultsMap.values(), new TypeReference<List<Rebuttal>>() { });
				
				finalResultsMap = generateRebuttalReport(rebuttalList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("REBUTTAL_REPORT",finalSortedMap);
				model.addAttribute("RebuttalReport",true);
				
				model.addAttribute("rebuttalReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				session.setAttribute("SS_REBUTTAL_REPORT", finalSortedMap);
				
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Summary")) {
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getMacJurisReport");
				ObjectMapper mapper = new ObjectMapper();
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntity.getBody();
				List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				reportsForm.setScoreCardType("Scoreable");
				finalResultsMap = generateQamSummaryReport(reportsForm, scoreCardList,session);
				
				returnView = "reports_macadmin_results";
				
				reportsForm.setCallResult(UIGenericConstants.ALL_STRING);
				finalSortedMap.putAll(finalResultsMap);
				
				if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") ) {
					model.addAttribute("SummaryScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","QAM Summary Report");
					
				} session.setAttribute("SS_MAC_JURIS_REPORT", finalSortedMap);			
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("ReportsFormSession", reportsForm);
		model.addAttribute("reportsForm", reportsForm);
		return returnView;
	}


	@RequestMapping(value ={"/admin/getMacJurisReportFromSession", "/quality_manager/getMacJurisReportFromSession", "/cms_user/getMacJurisReportFromSession",
			 "/mac_admin/getMacJurisReportFromSession","/mac_user/getMacJurisReportFromSession","/quality_monitor/getMacJurisReportFromSession"})			
	public String getMacJurisReportFromSession(HttpServletRequest request, final Model model, HttpSession session) {
		
		ReportsForm reportsForm= (ReportsForm) session.getAttribute("ReportsFormSession");
		
		String returnView = "";
		log.debug("--> getMacJurisReportFromSession <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();	
		HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
		HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
			
		try {
			
			Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = null;
			
			if(reportsForm.getMainReportSelect()==null || reportsForm.getMainReportSelect().equalsIgnoreCase("ScoreCard")) {
				returnView = "scorecardreports";
				ObjectMapper mapper = new ObjectMapper();
				finalSortedMap = (Map<String, QamMacByJurisdictionReviewReport>) session.getAttribute("SS_MAC_JURIS_REPORT");
				
				if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
					model.addAttribute("AllScoreCardReport_All",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (All Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("AllScoreCardReport_Pass",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allPassScoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Pass Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase(UIGenericConstants.ALL_STRING) && reportsForm.getCallResult().equalsIgnoreCase("Fail")) {
					model.addAttribute("AllScoreCardReport_Fail",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allFailScorecardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Fail Records)");
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Both Pass and Fail Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("ScoreablePassReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreablePassReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Pass Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Fail") ) {
					
					model.addAttribute("ScoreableFailReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreableFailReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Fail Records)");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Non-Scoreable")) {
					model.addAttribute("NonScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("nonScoreableList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Non-Scoreable Records");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Does Not Count")) {
					model.addAttribute("DoesNotCountReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("doesNotCountList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					
					model.addAttribute("ReportTitle","Scorecard Report - Does Not Count Records");
				}
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Compliance")) {
				
				returnView = "compliancereports";
				ObjectMapper mapper = new ObjectMapper();
				finalSortedMap = (Map<String, QamMacByJurisdictionReviewReport>) session.getAttribute("SS_COMPLIANCE_REPORT");
				
				model.addAttribute("COMPLIANCE_REPORT",finalSortedMap);
				model.addAttribute("ComplianceReport",true);
				model.addAttribute("complianceReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				
				
				if(reportsForm.getComplianceReportType().equalsIgnoreCase("ALL")) {
					model.addAttribute("ReportTitle","Compliance Report (ALL)");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Compliant")) {
					model.addAttribute("ReportTitle","Compliance Report");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Non-Compliant")) {
					model.addAttribute("ReportTitle","Non-Compliance Report");
				}
				
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				returnView = "rebuttalreports";
				
				ObjectMapper mapper = new ObjectMapper();
				finalSortedMap = (Map<String, QamMacByJurisdictionReviewReport>) session.getAttribute("SS_REBUTTAL_REPORT");
				model.addAttribute("REBUTTAL_REPORT",finalSortedMap);
				model.addAttribute("RebuttalReport",true);
				
				model.addAttribute("rebuttalReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
				returnView = "qaspreports";
				ObjectMapper mapper = new ObjectMapper();
				finalSortedMap = (Map<String, QamMacByJurisdictionReviewReport>) session.getAttribute("SS_QASP_REPORT");
				
				model.addAttribute("QASP_REPORT",finalSortedMap);
				model.addAttribute("QaspReport",true);
				model.addAttribute("qaspReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));				
				
				model.addAttribute("ReportTitle","QASP Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Summary")) {
				
				//
				returnView = "summaryreports";
				ObjectMapper mapper = new ObjectMapper();
				finalSortedMap = (Map<String, QamMacByJurisdictionReviewReport>) session.getAttribute("SS_QAM_SUMMARY_REPORT");
				
				model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
				model.addAttribute("SummaryScoreableReport",true);
				model.addAttribute("scoreableReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));				
				
				model.addAttribute("ReportTitle","QAM Summary Report");						
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("reportsForm", reportsForm);
		return returnView;
	}
	
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateMacAdminScoreCardReport(List<ScoreCard> scoreCardList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		HashMap<String,ArrayList<ScoreCard>> qamMacByJurisReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		int count = 1;
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				String scoreCardType = scoreCard.getScorecardType();
				
				qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
				qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
				qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
				qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
				qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
				qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
				qamMacByJurisdictionReviewReport.setCallCategoryName(HomeController.CALL_CATEGORY_MAP.get(scoreCard.getCallCategoryId()));
				qamMacByJurisdictionReviewReport.setCallDuration(scoreCard.getCallDuration());
								
				qamMacByJurisdictionReviewReport.setMacCallReferenceNumber(scoreCard.getMacCallReferenceNumber());
				qamMacByJurisdictionReviewReport.setFinalCallResult(scoreCard.getFinalScoreCardStatus());
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp+"_"+count, qamMacByJurisdictionReviewReport);
				count++;
				
			}
		}
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateScoreCardReport(List<ScoreCard> scoreCardList,HttpSession session) {
		
		//MAC Juris Program Data
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		
		HashMap<String,ArrayList<ScoreCard>> macJurisProgramScorecardReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				String programNameTemp = HomeController.ALL_PROGRAM_MAP.get(scoreCard.getProgramId());
				
				ArrayList<ScoreCard> scoreCardListTemp = macJurisProgramScorecardReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp);
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportSubTotal = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
				
				String scoreCardType = scoreCard.getScorecardType();
				
				if(scoreCardListTemp == null) {
					scoreCardListTemp = new ArrayList<ScoreCard>();
				} 
				
				scoreCardListTemp.add(scoreCard);
				macJurisProgramScorecardReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp, scoreCardListTemp);			
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
					qamMacByJurisdictionReviewReport.setTotalCount(1);
					qamMacByJurisdictionReviewReport.setProgram(programNameTemp);
					
					qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
					
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						
						qamMacByJurisdictionReviewReport.setScorableCount(1);
						if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
							qamMacByJurisdictionReviewReport.setScorablePass(1);
						} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
							qamMacByJurisdictionReviewReport.setScorableFail(1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(1);						
					} else if(scoreCardType.equalsIgnoreCase("Does Not Count")) {
						qamMacByJurisdictionReviewReport.setDoesNotCount_Number(1);						
					}
					
					
				} else {
					qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
						qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
						if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
							qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);							
						} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
							qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
						qamMacByJurisdictionReviewReport.setNonScorableCount(qamMacByJurisdictionReviewReport.getNonScorableCount()+1);	
					} else if(scoreCardType.equalsIgnoreCase("Does Not Count")) {
						qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
						qamMacByJurisdictionReviewReport.setDoesNotCount_Number(qamMacByJurisdictionReviewReport.getDoesNotCount_Number()+1);	
					}
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp, qamMacByJurisdictionReviewReport);
				
				
				if(qamMacByJurisdictionReviewReportSubTotal == null) {
					qamMacByJurisdictionReviewReportSubTotal = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReportSubTotal.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReportSubTotal.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReportSubTotal.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReportSubTotal.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReportSubTotal.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(scoreCardType);
					qamMacByJurisdictionReviewReportSubTotal.setTotalCount(1);					
					
					qamMacByJurisdictionReviewReportSubTotal.setScoreCardType("::");
					
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setScorableCount(1);
						if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
							qamMacByJurisdictionReviewReportSubTotal.setScorablePass(1);
						} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
							qamMacByJurisdictionReviewReportSubTotal.setScorableFail(1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setNonScorableCount(1);						
					} else if(scoreCardType.equalsIgnoreCase("Does Not Count")) {
						qamMacByJurisdictionReviewReportSubTotal.setDoesNotCount_Number(1);						
					}
					
					
				} else {
					qamMacByJurisdictionReviewReportSubTotal.setTotalCount(qamMacByJurisdictionReviewReportSubTotal.getTotalCount()+1);
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setScorableCount(qamMacByJurisdictionReviewReportSubTotal.getScorableCount()+1);
						qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(qamMacByJurisdictionReviewReportSubTotal.getScoreCardType()+"::"+scoreCardType);
						if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
							qamMacByJurisdictionReviewReportSubTotal.setScorablePass(qamMacByJurisdictionReviewReportSubTotal.getScorablePass()+1);							
						} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
							qamMacByJurisdictionReviewReportSubTotal.setScorableFail(qamMacByJurisdictionReviewReportSubTotal.getScorableFail()+1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(qamMacByJurisdictionReviewReportSubTotal.getScoreCardType()+"::"+scoreCardType);
						qamMacByJurisdictionReviewReportSubTotal.setNonScorableCount(qamMacByJurisdictionReviewReportSubTotal.getNonScorableCount()+1);	
					} else if(scoreCardType.equalsIgnoreCase("Does Not Count")) {
						qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(qamMacByJurisdictionReviewReportSubTotal.getScoreCardType()+"::"+scoreCardType);
						qamMacByJurisdictionReviewReportSubTotal.setDoesNotCount_Number(qamMacByJurisdictionReviewReportSubTotal.getDoesNotCount_Number()+1);	
					}
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReportSubTotal);
				
			}
		}
		
		for(String macJurisKey: finalResultsMap.keySet()) {
			
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
			
		
			
			if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Scoreable")) {
				Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
				Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
				qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
				qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);				
			} 
			if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Non-Scoreable")) {
				Float scNsPercent =  ((float)qamMacByJurisdictionReviewReport.getNonScorableCount()*100/(qamMacByJurisdictionReviewReport.getTotalCount()));
				scNsPercent =  Float.valueOf((twoDForm.format(scNsPercent)));
				qamMacByJurisdictionReviewReport.setNonScorablePercent(scNsPercent);		
			}  
			if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Does Not Count")) {
				Float dncPercent =  ((float)qamMacByJurisdictionReviewReport.getDoesNotCount_Number()*100/(qamMacByJurisdictionReviewReport.getTotalCount()));
				dncPercent =  Float.valueOf((twoDForm.format(dncPercent)));
				qamMacByJurisdictionReviewReport.setDoesNotCount_Percent(dncPercent);		
			}
			
			finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
		}
		
		session.setAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT", macJurisProgramScorecardReportSessionObject);		
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateScoreCardReportSummary(HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap) {
		
		
		Integer rowCount = finalResultsMap.size();
		
		Integer totalCount = 0,
				scoreableCount = 0,
				scoreablePassCount = 0,
				scoreableFailCount = 0,
				nonScoreableCount = 0,
				doesNotCount = 0;
		Float scoreablePassPercent = 0.0f,
				scoreableFailPercent = 0.0f,
				nonScoreablePercent = 0.0f,
				doesNotCountPercent = 0.0f;
		
		Integer totalRowCount = 0;
		
		if(rowCount > 0) {
			QamMacByJurisdictionReviewReport finalSummaryQamJurisReport = new QamMacByJurisdictionReviewReport();
			
			for (QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport: finalResultsMap.values()) {
				
				
				if(qamMacByJurisdictionReviewReport.getProgram() !=null && !qamMacByJurisdictionReviewReport.getProgram().equalsIgnoreCase("")) {
					totalCount += qamMacByJurisdictionReviewReport.getTotalCount();
					scoreableCount += qamMacByJurisdictionReviewReport.getScorableCount();
					scoreablePassCount += qamMacByJurisdictionReviewReport.getScorablePass();
					scoreableFailCount += qamMacByJurisdictionReviewReport.getScorableFail();
					nonScoreableCount += qamMacByJurisdictionReviewReport.getNonScorableCount();
					doesNotCount += qamMacByJurisdictionReviewReport.getDoesNotCount_Number();
					
					totalRowCount++;
				}
			
				
				
			}
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			
			finalSummaryQamJurisReport.setMacName("zTotals");
			
			finalSummaryQamJurisReport.setTotalCount(totalCount);
			finalSummaryQamJurisReport.setScorableCount(scoreableCount);
			finalSummaryQamJurisReport.setScorablePass(scoreablePassCount);
			finalSummaryQamJurisReport.setScorableFail(scoreableFailCount);
			finalSummaryQamJurisReport.setNonScorableCount(nonScoreableCount);
			finalSummaryQamJurisReport.setDoesNotCount_Number(doesNotCount);
			
			if (scoreableCount > 0) {
				scoreablePassPercent =  ((float)(scoreablePassCount * 100) / scoreableCount);
				finalSummaryQamJurisReport.setScorablePassPercent(Float.valueOf(twoDForm.format(scoreablePassPercent)));
				
				scoreableFailPercent =  ((float)(scoreableFailCount  * 100)/ scoreableCount);
				finalSummaryQamJurisReport.setScorableFailPercent(Float.valueOf(twoDForm.format(scoreableFailPercent)));
			}
			
			if (totalCount > 0) {
				nonScoreablePercent =  ((float)(nonScoreableCount * 100)/ totalCount);
				finalSummaryQamJurisReport.setNonScorablePercent(Float.valueOf(twoDForm.format(nonScoreablePercent)));
				
				doesNotCountPercent =  ((float)(doesNotCount * 100)/ totalCount);
				finalSummaryQamJurisReport.setDoesNotCount_Percent(Float.valueOf(twoDForm.format(doesNotCountPercent)));
			}
			
			finalResultsMap.put("zTotals", finalSummaryQamJurisReport);
		}
		
		
		return finalResultsMap;
	}
	
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateComplianceReport(List<CsrLog> csrLogList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		//HashMap<String,ArrayList<CsrLog>> qamMacByJurisReportSessionObject = new HashMap<String,ArrayList<CsrLog>>();
		
		for(CsrLog csrLog: csrLogList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(csrLog.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = csrLog.getJurisdiction();
				
				Calendar calObject = Calendar.getInstance();
				calObject.setTime(csrLog.getCreatedDate());
				Integer year = calObject.get(Calendar.YEAR); 
				String monthName = calObject.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				
				//ArrayList<CsrLog> csrLogListTemp = qamMacByJurisReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp+"_"+year+"_"+month);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp+"_"+monthName+"_"+year);
				
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMonthYear(monthName+", "+year);					
				} 
				
				if(csrLog.getComplianceStatus() == 1) {
					qamMacByJurisdictionReviewReport.setComplianceStatus("Compliant");
				} else {
					qamMacByJurisdictionReviewReport.setComplianceStatus("Non-Compliant");
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp+"_"+monthName+"_"+year, qamMacByJurisdictionReviewReport);
				
			}
		}		
		
		//session.setAttribute("COMPLIANCE_REPORT_SESSION_OBJECT", qamMacByJurisReportSessionObject);
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateQaspReport(List<ScoreCard> scoreCardList) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		//HashMap<String,ArrayList<ScoreCard>> qaspReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				
				Calendar calObject = Calendar.getInstance();
				calObject.setTime(scoreCard.getCallMonitoringDate());				
				
				
				/*  Commented the code to remove < 1 Functionality
				 * 	Integer dayOfMonth = calObject.get(Calendar.DAY_OF_MONTH);
				
					if(dayOfMonth <= 15) {					
						calObject.add(Calendar.MONTH, -1);					
					} */
				
				Integer year = calObject.get(Calendar.YEAR); 
				String monthName = calObject.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				
				//ArrayList<ScoreCard> scoreCardListTemp = qaspReportSessionObject.get(monthName+"_"+year);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(monthName+"_"+year);
				String scoreCardType = scoreCard.getScorecardType();
				
				/*if(scoreCardListTemp == null) {
					scoreCardListTemp = new ArrayList<ScoreCard>();
				} 
				
				scoreCardListTemp.add(scoreCard);
				qaspReportSessionObject.put(monthName+"_"+year, scoreCardListTemp);		*/
				
				Integer scoreCardProgramId = scoreCard.getProgramId();
				boolean hhhScoreCard = false;
				
				String scoreCardProgramName = HomeController.ALL_PROGRAM_MAP.get(scoreCardProgramId);
				if(scoreCardProgramName.contains("HHH")) {
					hhhScoreCard = true;
				}
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
					qamMacByJurisdictionReviewReport.setMonthYear(monthName+"_"+year);
					
					if(hhhScoreCard) {
						qamMacByJurisdictionReviewReport.setHhhTotalCount(1);
						
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setHhhScorableCount(1);
							if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setHhhScorablePass(1);
							} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase()))
								//scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) 
								{							
								qamMacByJurisdictionReviewReport.setHhhScorableFail(1);
							}
						} 
					} else {
						qamMacByJurisdictionReviewReport.setTotalCount(1);
						
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setScorableCount(1);
							if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setScorablePass(1);
							} else if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setScorableFail(1);
							}
						} 
					}
					
					
					
				} else {
					if(hhhScoreCard) {
						qamMacByJurisdictionReviewReport.setHhhTotalCount(qamMacByJurisdictionReviewReport.getHhhTotalCount()+1);
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setHhhScorableCount(qamMacByJurisdictionReviewReport.getHhhScorableCount()+1);
							if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setHhhScorablePass(qamMacByJurisdictionReviewReport.getHhhScorablePass()+1);
							} else if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setHhhScorableFail(qamMacByJurisdictionReviewReport.getHhhScorableFail()+1);
							}
						} 
					} else {
						qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
							if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);
							} else if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {
								qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
							}
						} 
					}
					
					
				}
				
				finalResultsMap.put(monthName+"_"+year, qamMacByJurisdictionReviewReport);
				
			}
		}
		
		for(String macJurisKey: finalResultsMap.keySet()) {
			
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
			
			if(qamMacByJurisdictionReviewReport.getScoreCardType().equalsIgnoreCase("Scoreable")) {
				if(qamMacByJurisdictionReviewReport.getScorableCount() != 0) {
					Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
					scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
					Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
					scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
					qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
					qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);
				} else {
					qamMacByJurisdictionReviewReport.setScorablePassPercent(0.0f);			
					qamMacByJurisdictionReviewReport.setScorableFailPercent(0.0f);
				}
				
				
				
				
			} 
			
			finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
		}
		
		//session.setAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT", qamMacByJurisReportSessionObject);
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateRebuttalReport(List<Rebuttal> rebuttalList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		HashMap<String,ArrayList<Rebuttal>> rebuttalMacJurisReportSessionObject = new HashMap<String,ArrayList<Rebuttal>>();
			
		for(Rebuttal rebuttal: rebuttalList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(rebuttal.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionNameTemp = HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId());
				
				
				ArrayList<Rebuttal> rebuttalListTemp = rebuttalMacJurisReportSessionObject.get(macNameTemp+"_"+jurisdictionNameTemp);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionNameTemp);			
				
				if(rebuttalListTemp == null) {
					rebuttalListTemp = new ArrayList<Rebuttal>();
				} 
				
				rebuttalListTemp.add(rebuttal);
				rebuttalMacJurisReportSessionObject.put(macNameTemp+"_"+jurisdictionNameTemp, rebuttalListTemp);		
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionNameTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					
				} 
				qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionNameTemp, qamMacByJurisdictionReviewReport);
				
			}
		}	
		
		session.setAttribute("REBUTTAL_MAC_JURIS_REPORT_SESSION_OBJECT", rebuttalMacJurisReportSessionObject);
		
		return finalResultsMap;
	}
	
	//Method to Generate Non-Scoreable Reason Report
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateNonScoreableReasonReport(List<ScoreCard> scoreCardList) {
		
		//MAC Juris Program Data
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		
		HashMap<String,ArrayList<ScoreCard>> macJurisProgramScorecardReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		Integer totalListSize = scoreCardList.size();
		Integer index = 1;
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				
				//String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				//String programNameTemp = HomeController.ALL_PROGRAM_MAP.get(scoreCard.getProgramId());
				
				
				//QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportSubTotal = finalResultsMap.get(macNameTemp+"_SubTotal");
				
				String scoreCardType = scoreCard.getScorecardType();
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				//qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
				qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
				qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
				qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
				qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
				qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
				qamMacByJurisdictionReviewReport.setMacCallReferenceNumber(scoreCard.getMacCallReferenceNumber());
				qamMacByJurisdictionReviewReport.setNonScoreableReason(scoreCard.getNonScoreableReason());
				//qamMacByJurisdictionReviewReport.setProgram(programNameTemp);
				
				qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
				
				if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {					
					qamMacByJurisdictionReviewReport.setNonScoreableReason(scoreCard.getNonScoreableReason());						
				}				
				
				finalResultsMap.put(macNameTemp+"_"+index, qamMacByJurisdictionReviewReport);
				index++;
				
				if(qamMacByJurisdictionReviewReportSubTotal == null) {
					qamMacByJurisdictionReviewReportSubTotal = new QamMacByJurisdictionReviewReport();
					//qamMacByJurisdictionReviewReportSubTotal.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReportSubTotal.setMacName(macNameTemp+" (SubTotal)");
					qamMacByJurisdictionReviewReportSubTotal.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReportSubTotal.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReportSubTotal.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(scoreCardType);
					qamMacByJurisdictionReviewReportSubTotal.setTotalCount(1);					
					
					qamMacByJurisdictionReviewReportSubTotal.setScoreCardType("::");
					
					if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setNonScorableCount(1);						
					} 
					
					
				} else {
					if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReportSubTotal.setTotalCount(qamMacByJurisdictionReviewReportSubTotal.getTotalCount()+1);	
					}
				}
				
				finalResultsMap.put(macNameTemp+"_SubTotal", qamMacByJurisdictionReviewReportSubTotal);
				
			}
		}
		if(totalListSize > 0) {
			QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportFinalTotal = new QamMacByJurisdictionReviewReport();
			qamMacByJurisdictionReviewReportFinalTotal.setMacName("Grand Total");
			qamMacByJurisdictionReviewReportFinalTotal.setTotalCount(totalListSize);
			finalResultsMap.put("Grand Total",qamMacByJurisdictionReviewReportFinalTotal);
		}
		return finalResultsMap;
	}
	
	
	//Method to Generate Review Report
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateReviewReport(ReportsForm reportsForm) {
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		ResponseEntity<HashMap> responseEntityObject = null;
		
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		ObjectMapper mapper = new ObjectMapper();
		
		Integer scoreCardPassCount = 0,scoreCardFailCount = 0,totalScoreCardCount = 0, nonScoreableCount=0;
		
		List<ScoreCard> scoreCardList = null;
		List<ScoreCard> scoreCardPassList = null;
		List<ScoreCard> scoreCardFailList = null;
		List<ScoreCard> nonScoreableList = null;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		Float scPassPercent = null,scFailPercent = null, nonScoreablePercent = null;
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();
		
		
		try {
			//MAC Data
			
			ROOT_URI = new String(HomeController.RAD_WS_URI + "getMacJurisReport");
			/*
			if(reportsForm.getMacId() != null && reportsForm.getJurisId() == null 
					&& reportsForm.getProgramId() == null && reportsForm.getProgramId() == "") {*/
			if(reportsForm.getMacId() != null && !reportsForm.getMacId().equalsIgnoreCase("All")
					&& reportsForm.getJurIdList() != null && reportsForm.getProgramId() != null & !reportsForm.getProgramId().equalsIgnoreCase("")) {
				
				reportsForm.setScoreCardType("Scoreable");
				reportsForm.setCallCategoryType("Pass");
				responseEntityObject = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntityObject.getBody();
				scoreCardPassList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				scoreCardPassCount = scoreCardPassList.size();
				
				reportsForm.setCallCategoryType("Fail");
				responseEntityObject = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntityObject.getBody();
				scoreCardFailList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				scoreCardFailCount = scoreCardFailList.size();
				
				reportsForm.setScoreCardType("Non-Scoreable");
				responseEntityObject = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntityObject.getBody();
				nonScoreableList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				nonScoreableCount = nonScoreableList.size();
				
				MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(Integer.valueOf(reportsForm.getMacId()));
				if(macInfo != null) {
					totalScoreCardCount = scoreCardPassCount + scoreCardFailCount;
					String macNameTemp = macInfo.getMacName();
					QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					//qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReport.setTotalCount(totalScoreCardCount);
					qamMacByJurisdictionReviewReport.setScorablePass(scoreCardPassCount);
					qamMacByJurisdictionReviewReport.setScorableFail(scoreCardFailCount);
					
					scPassPercent =  (float)scoreCardPassCount*100/(totalScoreCardCount);
					scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
					scFailPercent =  (float)scoreCardFailCount*100/(totalScoreCardCount);
					scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
					
					
					nonScoreablePercent = (float) nonScoreableCount*100/(totalScoreCardCount+nonScoreableCount);
					nonScoreablePercent =  Float.valueOf((twoDForm.format(nonScoreablePercent)));
					
					qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
					qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);	
					
					qamMacByJurisdictionReviewReport.setNonScorableCount(nonScoreableCount);
					qamMacByJurisdictionReviewReport.setNonScorablePercent(nonScoreablePercent);
					
					finalResultsMap.put(macNameTemp, qamMacByJurisdictionReviewReport);
				}
				
			} else if(reportsForm.getMacId() != null && reportsForm.getMacId().equalsIgnoreCase("All") && 
					reportsForm.getJurIdList() != null && reportsForm.getProgramId() != null & !reportsForm.getProgramId().equalsIgnoreCase("")) {
				
				reportsForm.setScoreCardType("Scoreable");
				
				responseEntityObject = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				
				resultsMap = responseEntityObject.getBody();
				scoreCardList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
								
				for(ScoreCard scoreCard: scoreCardList) {
					MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
					if(macInfo != null) {
						String macNameTemp = macInfo.getMacName();
						String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
						String programNameTemp = HomeController.ALL_PROGRAM_MAP.get(scoreCard.getProgramId());
						
						
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
						
						String scoreCardType = scoreCard.getScorecardType();
						
						if(qamMacByJurisdictionReviewReport == null) {
							qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
							qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReport.setTotalCount(1);
							
							qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								
								qamMacByJurisdictionReviewReport.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(1);
								}
							} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
								qamMacByJurisdictionReviewReport.setNonScorableCount(1);						
							} 
							
							
						} else {
							qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
								qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
								}
							} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
								qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
								qamMacByJurisdictionReviewReport.setNonScorableCount(qamMacByJurisdictionReviewReport.getNonScorableCount()+1);	
							} 
						}
						
						finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReport);
					}
				}
				
				for(String macJurisKey: finalResultsMap.keySet()) {
					
					
					QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
					
				
					
					if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Scoreable")) {
						scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
						scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
						qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
						qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);				
					} 
					if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Non-Scoreable")) {
						Float scNsPercent =  ((float)qamMacByJurisdictionReviewReport.getNonScorableCount()*100/(qamMacByJurisdictionReviewReport.getTotalCount()));
						scNsPercent =  Float.valueOf((twoDForm.format(scNsPercent)));
						qamMacByJurisdictionReviewReport.setNonScorablePercent(scNsPercent);		
					}  
					
					
					finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
				}
				
				
				
			} else if(reportsForm.getMacId() != null && reportsForm.getMacId().equalsIgnoreCase("All") && 
					reportsForm.getJurIdList() != null && reportsForm.getProgramId() != null & !reportsForm.getProgramId().equalsIgnoreCase("")) {
				
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return finalResultsMap;
	}
	
	
	//Method to Generate Review Report
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateQamSummaryReport(ReportsForm reportsForm, List<ScoreCard> scoreCardList,HttpSession session) {
		
		//MAC Juris Program Data
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		
		HashMap<String,ArrayList<ScoreCard>> macJurisProgramScorecardReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		
		Integer timeDurationSecondsPerRecord = 0;	
		
		
		try {
			if( (reportsForm.getMacId() != null || reportsForm.getMacId().equalsIgnoreCase("") || reportsForm.getMacId().equalsIgnoreCase("ALL"))
					&& (reportsForm.getJurIdList() == null || reportsForm.getJurIdList().size() == 0) && !reportsForm.getJurisdictionNameValues().equalsIgnoreCase("ALL")
					&& (reportsForm.getProgramId() == null || reportsForm.getProgramId().equalsIgnoreCase(""))) {
				
				for(ScoreCard scoreCard: scoreCardList) {
					timeDurationSecondsPerRecord = 0;
					MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
					if(macInfo != null) {
						String macNameTemp = macInfo.getMacName();
						
						ArrayList<ScoreCard> scoreCardListTemp = macJurisProgramScorecardReportSessionObject.get(macNameTemp);
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp);
						
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportSubTotal = finalResultsMap.get(macNameTemp);
						
						String scoreCardType = scoreCard.getScorecardType();
						
						if(scoreCardListTemp == null) {
							scoreCardListTemp = new ArrayList<ScoreCard>();
						} 
						
						scoreCardListTemp.add(scoreCard);
						macJurisProgramScorecardReportSessionObject.put(macNameTemp, scoreCardListTemp);			
										
						if(qamMacByJurisdictionReviewReport == null) {
							qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReport.setTotalCount(1);
							
							qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
							
							if(scoreCard.getCallDuration() != null) {
								
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
								
							} 						
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReport.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(1);
								}
							}						
							
						} else {
							qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
							if(scoreCard.getCallDuration() != null) {
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
							} 
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(qamMacByJurisdictionReviewReport.getCallDurationInSeconds() + timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
								qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
								}							
							} 
						}						
						finalResultsMap.put(macNameTemp, qamMacByJurisdictionReviewReport);	
						
					}
				}
				
				for(String macKey: finalResultsMap.keySet()) {
					
					DecimalFormat twoDForm = new DecimalFormat("#.##");
					QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macKey);				
					
					if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Scoreable")) {
						Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
						Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
						qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
						qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);		
						
						Integer averageDurationSeconds = qamMacByJurisdictionReviewReport.getCallDurationInSeconds() / qamMacByJurisdictionReviewReport.getScorableCount();
						
						Integer seconds = averageDurationSeconds % 60;
						Integer hours = averageDurationSeconds / 60;
						
						Integer minutes = hours % 60;
						
						String averageDurationString = minutes +":" + seconds;
						
						qamMacByJurisdictionReviewReport.setCallDuration(averageDurationString);
					} 			
					finalResultsMap.put(macKey, qamMacByJurisdictionReviewReport);
				}
				
					finalResultsMap = generateQamSummaryFinalRow(finalResultsMap,"Mac");
								
				
			} else if( (reportsForm.getMacId() != null || reportsForm.getMacId().equalsIgnoreCase("") || reportsForm.getMacId().equalsIgnoreCase("ALL"))
					&& (reportsForm.getJurIdList() != null || reportsForm.getJurIdList().size() > 0 || reportsForm.getJurisdictionNameValues().equalsIgnoreCase("ALL"))
					&& (reportsForm.getProgramId() == null || reportsForm.getProgramId().equalsIgnoreCase(""))) {
				
				for(ScoreCard scoreCard: scoreCardList) {
					timeDurationSecondsPerRecord = 0;
					MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
					if(macInfo != null) {
						String macNameTemp = macInfo.getMacName();
						String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
						//String programNameTemp = HomeController.ALL_PROGRAM_MAP.get(scoreCard.getProgramId());
						
						ArrayList<ScoreCard> scoreCardListTemp = macJurisProgramScorecardReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp);
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
						
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportSubTotal = finalResultsMap.get(macNameTemp);
						
						String scoreCardType = scoreCard.getScorecardType();
						
						if(scoreCardListTemp == null) {
							scoreCardListTemp = new ArrayList<ScoreCard>();
						} 
						
						scoreCardListTemp.add(scoreCard);
						macJurisProgramScorecardReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp, scoreCardListTemp);			
										
						if(qamMacByJurisdictionReviewReport == null) {
							qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
							qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReport.setTotalCount(1);
							//qamMacByJurisdictionReviewReport.setProgram(programNameTemp);
							
							qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
							
							if(scoreCard.getCallDuration() != null) {
								
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
								
							} else {
								timeDurationSecondsPerRecord = 0;
							}					
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReport.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(1);
								}
							} 
							
							
						} else {
							qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
							if(scoreCard.getCallDuration() != null) {
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
							} 	
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(qamMacByJurisdictionReviewReport.getCallDurationInSeconds() + timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
								qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
								}
							} 
						}
						
						finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReport);
						
						timeDurationSecondsPerRecord = 0;
						
						if(qamMacByJurisdictionReviewReportSubTotal == null) {
							qamMacByJurisdictionReviewReportSubTotal = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReportSubTotal.setJurisdictionName("Sub Total");
							qamMacByJurisdictionReviewReportSubTotal.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReportSubTotal.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReportSubTotal.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReportSubTotal.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReportSubTotal.setTotalCount(1);					
							
							qamMacByJurisdictionReviewReportSubTotal.setScoreCardType("::");
							
							if(scoreCard.getCallDuration() != null) {
								
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
								
							} 
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReportSubTotal.setCallDurationInSeconds(timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReportSubTotal.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReportSubTotal.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReportSubTotal.setScorableFail(1);
								}
							} 
							
							
						} else {
							qamMacByJurisdictionReviewReportSubTotal.setTotalCount(qamMacByJurisdictionReviewReportSubTotal.getTotalCount()+1);
							if(scoreCard.getCallDuration() != null) {
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
							} 
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReportSubTotal.setCallDurationInSeconds(qamMacByJurisdictionReviewReportSubTotal.getCallDurationInSeconds() + timeDurationSecondsPerRecord);
								
								qamMacByJurisdictionReviewReportSubTotal.setScorableCount(qamMacByJurisdictionReviewReportSubTotal.getScorableCount()+1);
								qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(qamMacByJurisdictionReviewReportSubTotal.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReportSubTotal.setScorablePass(qamMacByJurisdictionReviewReportSubTotal.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReportSubTotal.setScorableFail(qamMacByJurisdictionReviewReportSubTotal.getScorableFail()+1);
								}
							} 
						}
						
						finalResultsMap.put(macNameTemp, qamMacByJurisdictionReviewReportSubTotal);
						
					}
				}
				
				for(String macJurisKey: finalResultsMap.keySet()) {
					
					DecimalFormat twoDForm = new DecimalFormat("#.##");
					QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);			
					
					if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Scoreable")) {
						Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
						Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
						qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
						qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);	
						
						Integer averageDurationSeconds = qamMacByJurisdictionReviewReport.getCallDurationInSeconds() / qamMacByJurisdictionReviewReport.getScorableCount();
						
						Integer seconds = averageDurationSeconds % 60;
						Integer hours = averageDurationSeconds / 60;
						
						Integer minutes = hours % 60;
						
						String averageDurationString = minutes +":" + seconds;
						
						qamMacByJurisdictionReviewReport.setCallDuration(averageDurationString);
					} 
									
					finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
				}
				
					finalResultsMap = generateQamSummaryFinalRow(finalResultsMap,"Jurisdiction");
				
							
				
			} else if( (reportsForm.getMacId() != null || reportsForm.getMacId().equalsIgnoreCase("") || reportsForm.getMacId().equalsIgnoreCase("ALL"))
					&& (reportsForm.getJurIdList() != null || reportsForm.getJurIdList().size() > 0 || reportsForm.getJurisdictionNameValues().equalsIgnoreCase("ALL"))
					&& (reportsForm.getProgramId() != null || !reportsForm.getProgramId().equalsIgnoreCase(""))) {
				
				for(ScoreCard scoreCard: scoreCardList) {
					timeDurationSecondsPerRecord = 0;
					MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
					if(macInfo != null) {
						String macNameTemp = macInfo.getMacName();
						String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
						String programNameTemp = HomeController.ALL_PROGRAM_MAP.get(scoreCard.getProgramId());
						
						ArrayList<ScoreCard> scoreCardListTemp = macJurisProgramScorecardReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp);
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp);
						
						QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReportSubTotal = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
						
						String scoreCardType = scoreCard.getScorecardType();
						
						if(scoreCardListTemp == null) {
							scoreCardListTemp = new ArrayList<ScoreCard>();
						} 
						
						scoreCardListTemp.add(scoreCard);
						macJurisProgramScorecardReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp, scoreCardListTemp);			
										
						if(qamMacByJurisdictionReviewReport == null) {
							qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
							qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReport.setTotalCount(1);
							qamMacByJurisdictionReviewReport.setProgram(programNameTemp);
							
							qamMacByJurisdictionReviewReport.setScoreCardType("::"+scoreCardType);
							if(scoreCard.getCallDuration() != null) {
								
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
								
							} else {
								timeDurationSecondsPerRecord = 0;
							}					
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(timeDurationSecondsPerRecord);
								
								qamMacByJurisdictionReviewReport.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(1);
								}
							} 							
							
						} else {
							qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
							if(scoreCard.getCallDuration() != null) {
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
							} 	
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReport.setCallDurationInSeconds(qamMacByJurisdictionReviewReport.getCallDurationInSeconds() + timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
								qamMacByJurisdictionReviewReport.setScoreCardType(qamMacByJurisdictionReviewReport.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
								}
							} 
						}
						
						finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp+"_"+programNameTemp, qamMacByJurisdictionReviewReport);
						
						timeDurationSecondsPerRecord = 0;
						
						if(qamMacByJurisdictionReviewReportSubTotal == null) {
							qamMacByJurisdictionReviewReportSubTotal = new QamMacByJurisdictionReviewReport();
							qamMacByJurisdictionReviewReportSubTotal.setJurisdictionName(jurisdictionTemp);
							qamMacByJurisdictionReviewReportSubTotal.setMacName(macNameTemp);
							qamMacByJurisdictionReviewReportSubTotal.setQamStartDate(macInfo.getQamStartDate());
							qamMacByJurisdictionReviewReportSubTotal.setQamEndDate(macInfo.getQamEndDate());
							qamMacByJurisdictionReviewReportSubTotal.setMacId(macInfo.getId().intValue());
							qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(scoreCardType);
							qamMacByJurisdictionReviewReportSubTotal.setTotalCount(1);		
							qamMacByJurisdictionReviewReportSubTotal.setProgram("Sub Total");
							
							qamMacByJurisdictionReviewReportSubTotal.setScoreCardType("::");
							
							if(scoreCard.getCallDuration() != null) {
								
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
								
							} 
							
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReportSubTotal.setCallDurationInSeconds(timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReportSubTotal.setScorableCount(1);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReportSubTotal.setScorablePass(1);
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReportSubTotal.setScorableFail(1);
								}
							} 
							
							
						} else {
							qamMacByJurisdictionReviewReportSubTotal.setTotalCount(qamMacByJurisdictionReviewReportSubTotal.getTotalCount()+1);
							if(scoreCard.getCallDuration() != null) {
								String durationLiterals[]=scoreCard.getCallDuration().split(":");
								Integer hourSeconds = Integer.valueOf(durationLiterals[0]) * 3600;
								Integer minuteSeconds = Integer.valueOf(durationLiterals[1]) * 60;
								Integer seconds = Integer.valueOf(durationLiterals[2]);
								
								timeDurationSecondsPerRecord = hourSeconds + minuteSeconds + seconds;
							} 	
							if(scoreCardType.equalsIgnoreCase("Scoreable")) {
								qamMacByJurisdictionReviewReportSubTotal.setCallDurationInSeconds(qamMacByJurisdictionReviewReportSubTotal.getCallDurationInSeconds() + timeDurationSecondsPerRecord);
								qamMacByJurisdictionReviewReportSubTotal.setScorableCount(qamMacByJurisdictionReviewReportSubTotal.getScorableCount()+1);
								qamMacByJurisdictionReviewReportSubTotal.setScoreCardType(qamMacByJurisdictionReviewReportSubTotal.getScoreCardType()+"::"+scoreCardType);
								if(scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Pass".toLowerCase())) {
									qamMacByJurisdictionReviewReportSubTotal.setScorablePass(qamMacByJurisdictionReviewReportSubTotal.getScorablePass()+1);							
								} else if(scoreCard.getFinalScoreCardStatus() != null && scoreCard.getFinalScoreCardStatus().toLowerCase().contains("Fail".toLowerCase())) {							
									qamMacByJurisdictionReviewReportSubTotal.setScorableFail(qamMacByJurisdictionReviewReportSubTotal.getScorableFail()+1);
								}
							} 
						}
						
						finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReportSubTotal);
						
					}
				}
				
				for(String macJurisKey: finalResultsMap.keySet()) {
					
					DecimalFormat twoDForm = new DecimalFormat("#.##");
					QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
					
				
					
					if(qamMacByJurisdictionReviewReport.getScoreCardType().contains("::Scoreable")) {
						Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
						Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
						scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
						qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
						qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);		
						
						Integer averageDurationSeconds = qamMacByJurisdictionReviewReport.getCallDurationInSeconds() / qamMacByJurisdictionReviewReport.getScorableCount();
						
						Integer seconds = averageDurationSeconds % 60;
						Integer hours = averageDurationSeconds / 60;
						
						Integer minutes = hours % 60;
						
						String averageDurationString = minutes +":" + seconds;
						
						qamMacByJurisdictionReviewReport.setCallDuration(averageDurationString);
					} 					
					
					finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
				}
				
				
					finalResultsMap = generateQamSummaryFinalRow(finalResultsMap,"Program");
					
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		session.setAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT", macJurisProgramScorecardReportSessionObject);		
		
		return finalResultsMap;
	}
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateQamSummaryFinalRow(HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap, String reportOption) {
		
		
		Integer rowCount = finalResultsMap.size();
		
		Integer totalCount = 0,
				scoreableCount = 0,
				scoreablePassCount = 0,
				scoreableFailCount = 0,
				averageDurationSeconds = 0;
		Float scoreablePassPercent = 0.0f,
				scoreableFailPercent = 0.0f;
		
		Integer totalRowCount = 0;
		
		if(rowCount > 0) {
			QamMacByJurisdictionReviewReport finalSummaryQamJurisReport = new QamMacByJurisdictionReviewReport();
			
			for (QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport: finalResultsMap.values()) {
				
				
				if(reportOption.equalsIgnoreCase("Program") && qamMacByJurisdictionReviewReport.getProgram() !=null && !qamMacByJurisdictionReviewReport.getProgram().equalsIgnoreCase("Sub Total")) {
					totalCount += qamMacByJurisdictionReviewReport.getTotalCount();
					scoreableCount += qamMacByJurisdictionReviewReport.getScorableCount();
					scoreablePassCount += qamMacByJurisdictionReviewReport.getScorablePass();
					scoreableFailCount += qamMacByJurisdictionReviewReport.getScorableFail();					
					
					totalRowCount++;
					
					averageDurationSeconds += qamMacByJurisdictionReviewReport.getCallDurationInSeconds();					
				} else if(reportOption.equalsIgnoreCase("Jurisdiction") && qamMacByJurisdictionReviewReport.getJurisdictionName() !=null && !qamMacByJurisdictionReviewReport.getJurisdictionName().equalsIgnoreCase("Sub Total")) {
					totalCount += qamMacByJurisdictionReviewReport.getTotalCount();
					scoreableCount += qamMacByJurisdictionReviewReport.getScorableCount();
					scoreablePassCount += qamMacByJurisdictionReviewReport.getScorablePass();
					scoreableFailCount += qamMacByJurisdictionReviewReport.getScorableFail();					
					
					totalRowCount++;
					
					averageDurationSeconds += qamMacByJurisdictionReviewReport.getCallDurationInSeconds();					
				} else if(reportOption.equalsIgnoreCase("Mac") && qamMacByJurisdictionReviewReport.getJurisdictionName() !=null && !qamMacByJurisdictionReviewReport.getJurisdictionName().equalsIgnoreCase("")) {
					totalCount += qamMacByJurisdictionReviewReport.getTotalCount();
					scoreableCount += qamMacByJurisdictionReviewReport.getScorableCount();
					scoreablePassCount += qamMacByJurisdictionReviewReport.getScorablePass();
					scoreableFailCount += qamMacByJurisdictionReviewReport.getScorableFail();					
					
					totalRowCount++;
					
					averageDurationSeconds += qamMacByJurisdictionReviewReport.getCallDurationInSeconds();					
				}
			
				
				
			}
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			
			finalSummaryQamJurisReport.setMacName("zTotals");
			
			finalSummaryQamJurisReport.setTotalCount(totalCount);
			finalSummaryQamJurisReport.setScorableCount(scoreableCount);
			finalSummaryQamJurisReport.setScorablePass(scoreablePassCount);
			finalSummaryQamJurisReport.setScorableFail(scoreableFailCount);
			
			
			if (scoreableCount > 0) {
				scoreablePassPercent =  ((float)(scoreablePassCount * 100) / scoreableCount);
				finalSummaryQamJurisReport.setScorablePassPercent(Float.valueOf(twoDForm.format(scoreablePassPercent)));
				
				scoreableFailPercent =  ((float)(scoreableFailCount  * 100)/ scoreableCount);
				finalSummaryQamJurisReport.setScorableFailPercent(Float.valueOf(twoDForm.format(scoreableFailPercent)));
				
				Integer finalAverageDurationSeconds = averageDurationSeconds / scoreableCount;
				
				Integer seconds = finalAverageDurationSeconds % 60;
				Integer hours = finalAverageDurationSeconds / 60;
				
				Integer minutes = hours % 60;
				
				String averageDurationString = minutes +":" + seconds;
				
				finalSummaryQamJurisReport.setCallDuration(averageDurationString);
			}
			
			finalResultsMap.put("zTotals", finalSummaryQamJurisReport);
		}
		
		
		return finalResultsMap;
	}
}