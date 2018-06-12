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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrLog;
import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ReportsForm;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportsController {
	private static final Logger log = Logger.getLogger(ReportsController.class);

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
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
			//String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			programMap = new HashMap<Integer, String> ();
			locationMap = new HashMap<Integer, String> ();
			for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
				jurIdArrayList.add(jurisIdSingle);
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			reportsForm.setJurIdList(jurIdArrayList);
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(HomeController.LOGGED_IN_USER_MAC_ID.toString());
			
		} else {
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL")) {
				jurisMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(reportsForm.getMacId()));
				
			} else {
				jurisMap = HomeController.JURISDICTION_MAP;
				
			}
			
			model.addAttribute("jurisMapEdit", jurisMap);
			
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL")
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase("ALL")) {
				programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(reportsForm.getMacId()+"_"+reportsForm.getJurisId());
				
			} else {
				programMap = HomeController.ALL_PROGRAM_MAP;
				
			}
			
			model.addAttribute("programMapEdit", programMap);
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL")
					&& !reportsForm.getJurisId().equalsIgnoreCase("") && !reportsForm.getJurisId().equalsIgnoreCase("ALL")
							&& !reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase("ALL")) {
				locationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(reportsForm.getMacId())+"_"+Integer.valueOf(reportsForm.getJurisId())+"_"+Integer.valueOf(reportsForm.getProgramId()));			
				
			} else {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			}
			
			model.addAttribute("locationMapEdit", locationMap);
		}
		
		
		return "reports";
	}
	
	
	 @RequestMapping(value ={"/admin/reports", "/quality_manager/reports", "/cms_user/reports",
			 "/mac_admin/reports","/mac_user/reports","/quality_monitor/reports"})		
	public String viewReports(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewReports <--");
		
		ReportsForm reportsForm = new ReportsForm();
		reportsForm.setMainReportSelect("ScoreCard");
		model.addAttribute("reportsForm", reportsForm);
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			User userFormSession = (User) session.getAttribute("LoggedInUserForm");
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
			String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			HashMap<Integer, String> programMap = new HashMap<Integer, String> ();
			HashMap<Integer, String> locationMap = new HashMap<Integer, String> ();
			for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
				for(Integer programIdSingle: programTempMap.keySet()) {
					HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle+"_"+programIdSingle);
					if (locationTempMap == null) continue;
					locationMap.putAll(locationTempMap);
					locationTempMap = null;
				}
				
				programTempMap = null;
			}
			model.addAttribute("programMapEdit", programMap);	
			model.addAttribute("locationMapEdit", locationMap);	
			
			reportsForm.setMacId(HomeController.LOGGED_IN_USER_MAC_ID.toString());
			
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);		
			
		}	
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		
		return "reports";
	}
	
	 @RequestMapping(value ={"/admin/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}", "/quality_manager/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}", 
			 "/cms_user/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}", "/mac_admin/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}",
			 "/mac_user/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}","/quality_monitor/mac-jur-report-drilldown/{macId}/{jurisId}/{searchString}"})	
	public String macJurReportDrillDown(@PathVariable("macId") final String macIdString, @PathVariable("jurisId") final String jurIdString, @PathVariable("searchString") final String searchString, final Model model,HttpSession session) {
		
		try {
			HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
			HashMap<String,ArrayList<ScoreCard>> scoreCardSessionMap = (HashMap<String,ArrayList<ScoreCard>>) session.getAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT");
			ArrayList<ScoreCard> scoreCardList = scoreCardSessionMap.get(macIdString+"_"+jurIdString);
			
			for(ScoreCard scoreCard: scoreCardList) {
				scoreCard.setMacName(macIdString);
				scoreCard.setJurisdictionName(jurIdString);
				if(searchString.equalsIgnoreCase("ALL")) {
					resultsMap.put(scoreCard.getId(), scoreCard);
				} else if(searchString.equalsIgnoreCase("ScoreableOnly")) {
					if(scoreCard.getScorecardType().equalsIgnoreCase("Scoreable"))
						resultsMap.put(scoreCard.getId(), scoreCard);
				}else if(searchString.equalsIgnoreCase("ScoreablePass")) {
					if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) 
					 resultsMap.put(scoreCard.getId(), scoreCard);
				}else if(searchString.equalsIgnoreCase("ScoreableFail")) {
					if(scoreCard.getCallResult().equalsIgnoreCase("Fail"))
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
			if(callCategoryId.equalsIgnoreCase("ALL") && rebuttalStatus.equalsIgnoreCase("ALL")) {
				resultsMap.put(rebuttal.getId(), rebuttal);
			} else if(callCategoryId.equalsIgnoreCase("ALL")) {
				if(rebuttal.getRebuttalStatus().equalsIgnoreCase(rebuttalStatus))
					resultsMap.put(rebuttal.getId(), rebuttal);
			}else if(rebuttalStatus.equalsIgnoreCase("ALL")) {
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
	 
	 private static final SimpleDateFormat usEstDateFormat = new SimpleDateFormat("MM/dd/yyyy");
	 
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
			reportsForm.setFromDate(mdyFormat.parse(reportsForm.getFromDateString()));
			reportsForm.setToDate(mdyFormat.parse(reportsForm.getToDateString()));
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				User userFormSession = (User) session.getAttribute("LoggedInUserForm");
				
				reportsForm.setMacId(HomeController.LOGGED_IN_USER_MAC_ID.toString());
				String[] jurisIds = reportsForm.getJurisdictionIds();
				
				String jurIds = reportsForm.getJurisdictionIds().toString();
				
				ArrayList<Integer> jurIdArrayList = new ArrayList<Integer>();
				
				if(jurisIds[0].equalsIgnoreCase("ALL")) {
					reportsForm.setJurisdictionName("ALL");
					
					for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
						jurIdArrayList.add(jurisIdSingle);
						
						if(reportsForm.getProgramId().equalsIgnoreCase("ALL")) {
							reportsForm.setProgramName(reportsForm.getProgramId());
							HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
							if (programTempMap == null) continue;
							
							programMap.putAll(programTempMap);
							if(reportsForm.getLoc().equalsIgnoreCase("ALL")) {
								for(Integer programIdSingle: programTempMap.keySet()) {
									HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle+"_"+programIdSingle);
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
					
					if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL") ) {
						String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
						reportsForm.setMacName(macName);
					}
					
					if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase("ALL") ) {
						String programName = HomeController.ALL_PROGRAM_MAP.get(Integer.valueOf(reportsForm.getProgramId()));				
						reportsForm.setProgramName(programName);
					}
				}				
				
			} else {
				
				if (reportsForm.getJurisdictionIds() != null ) {
					
					String[] jurisIds = reportsForm.getJurisdictionIds();
					
					ArrayList<Integer> jurisIdArrayList = new ArrayList<Integer> ();
					ArrayList<String> jurisdictionNameArrayList = new ArrayList<String> ();
					String jurisdictionNamesValues = "";
					for (String jurisIdSingleValue: jurisIds) {
						if(jurisIdSingleValue.equalsIgnoreCase("ALL")) {
							jurisdictionNamesValues = "ALL";
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
				
				if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL") ) {
					String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
					reportsForm.setMacName(macName);
				} else {
					reportsForm.setMacName("ALL");
				}
				
				if (!reportsForm.getProgramId().equalsIgnoreCase("") && !reportsForm.getProgramId().equalsIgnoreCase("ALL") ) {
					String programName = HomeController.ALL_PROGRAM_MAP.get(Integer.valueOf(reportsForm.getProgramId()));				
					reportsForm.setProgramName(programName);
				} else {
					reportsForm.setProgramName(reportsForm.getProgramId());
				}
				
				if (!reportsForm.getLoc().equalsIgnoreCase("") && !reportsForm.getLoc().equalsIgnoreCase("ALL") ) {
					/*String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
					reportsForm.setMacName(macName);*/
				} else {
					/**/
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
				finalResultsMap = generateScoreCardReport(scoreCardList,session);
				
				finalSortedMap.putAll(finalResultsMap);
				
				if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("All")) {
					model.addAttribute("AllScoreCardReport_All",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("scoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (All Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("AllScoreCardReport_Pass",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allPassScoreCardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Pass Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("Fail")) {
					model.addAttribute("AllScoreCardReport_Fail",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("allFailScorecardList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Fail Records)");
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("All")) {
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
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getComplianceReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<CsrLog> complianceList = mapper.convertValue(resultsMap.values(), new TypeReference<List<CsrLog>>() { });
				
				finalResultsMap = generateComplianceReport(complianceList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("COMPLIANCE_REPORT",finalSortedMap);
				model.addAttribute("ComplianceReport",true);
				model.addAttribute("complianceReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				
				if(reportsForm.getComplianceReportType().equalsIgnoreCase("")) {
					model.addAttribute("ReportTitle","Compliance Report (ALL)");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Compliant")) {
					model.addAttribute("ReportTitle","Compliance Report");
				} else if(reportsForm.getComplianceReportType().equalsIgnoreCase("Non-Compliant")) {
					model.addAttribute("ReportTitle","Non-Compliance Report");
				}
				
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				
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
				
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Qasp")) {
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getQaspReport");
				reportsForm.setScoreCardType("Scoreable");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<ScoreCard> qaspList = mapper.convertValue(resultsMap.values(), new TypeReference<List<ScoreCard>>() { });
				
				finalResultsMap = generateQaspReport(qaspList);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("QASP_REPORT",finalSortedMap);
				model.addAttribute("QaspReport",true);
				model.addAttribute("qaspReportList",mapper.writeValueAsString(finalSortedMap.values()).replaceAll("'", " "));
				
				model.addAttribute("ReportTitle","QASP Report");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("ReportsFormSession", reportsForm);
		model.addAttribute("reportsForm", reportsForm);
		return "macjurisreviewreports";
	}


	@RequestMapping(value ={"/admin/getMacJurisReportFromSession", "/quality_manager/getMacJurisReportFromSession", "/cms_user/getMacJurisReportFromSession",
			 "/mac_admin/getMacJurisReportFromSession","/mac_user/getMacJurisReportFromSession","/quality_monitor/getMacJurisReportFromSession"})			
	public String getMacJurisReportFromSession(HttpServletRequest request, final Model model, HttpSession session) {
		ReportsForm reportsForm= (ReportsForm) session.getAttribute("ReportsFormSession");
		
		String returnView = "";
		log.debug("--> getMacJurisReport <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI;
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();	
			
		try {
			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			reportsForm.setFromDate(mdyFormat.parse(reportsForm.getFromDateString()));
			reportsForm.setToDate(mdyFormat.parse(reportsForm.getToDateString()));
			
			if (reportsForm.getJurisdictionIds() != null && !reportsForm.getJurisdictionIds().toString().contains(("ALL"))) {
				String[] jurisIds = reportsForm.getJurisdictionIds();
				
				ArrayList<Integer> jurisIdArrayList = new ArrayList<Integer> ();
				ArrayList<String> jurisdictionNameArrayList = new ArrayList<String> ();
				String jurisdictionNamesValues = "";
				for (String jurisIdSingleValue: jurisIds) {
					jurisIdArrayList.add(Integer.valueOf(jurisIdSingleValue));
					String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));
					jurisdictionNameArrayList.add(jurisdictionTempName);
					jurisdictionNamesValues += jurisdictionTempName+ " ";
				}
				
				reportsForm.setJurIdList(jurisIdArrayList);			
				reportsForm.setJurisdictionNameValues(jurisdictionNamesValues);
				reportsForm.setJurisdictionNameList(jurisdictionNameArrayList);
				reportsForm.setJurisId("");
				
			} else {
				reportsForm.setJurisdictionName("ALL");
			}
			
			if (!reportsForm.getMacId().equalsIgnoreCase("") && !reportsForm.getMacId().equalsIgnoreCase("ALL") ) {
				String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(reportsForm.getMacId()));				
				reportsForm.setMacName(macName);
			} 
			
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
				finalResultsMap = generateScoreCardReport(scoreCardList,session);
				
				
				
				/*Map<Integer, String> treeMap2 = new TreeMap<>( (Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		        );*/
				
				finalSortedMap.putAll(finalResultsMap);
				
				if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("All")) {
					model.addAttribute("AllScoreCardReport_All",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (All Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("AllScoreCardReport_Pass",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Pass Records)");
					
					
				} else if(reportsForm.getScoreCardType().equalsIgnoreCase("") && reportsForm.getCallResult().equalsIgnoreCase("Fail")) {
					model.addAttribute("AllScoreCardReport_Fail",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable, Non-Scoreable, Does Not Count Records (Fail Records)");
					
					
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("All")) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Both Pass and Fail Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Pass")) {
					model.addAttribute("ScoreablePassReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Pass Records)");
					
				}  else if (reportsForm.getScoreCardType().equalsIgnoreCase("Scoreable") && reportsForm.getCallResult().equalsIgnoreCase("Fail") ) {
					
					model.addAttribute("ScoreableFailReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					
					model.addAttribute("ReportTitle","Scorecard Report - Scoreable (Only Fail Records)");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Non-Scoreable")) {
					model.addAttribute("NonScoreable",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Non-Scoreable Records");
				} else if (reportsForm.getScoreCardType().equalsIgnoreCase("Does Not Count")) {
					model.addAttribute("ScoreableReport",true);
					model.addAttribute("MAC_JURIS_REPORT",finalSortedMap);
					model.addAttribute("ReportTitle","Scorecard Report - Does Not Count Records");
				}
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Compliance")) {
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getComplianceReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<CsrLog> complianceList = mapper.convertValue(resultsMap.values(), new TypeReference<List<CsrLog>>() { });
				
				finalResultsMap = generateComplianceReport(complianceList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("COMPLIANCE_REPORT",finalSortedMap);
				model.addAttribute("ComplianceReport",true);
				model.addAttribute("ReportTitle","Compliance Report");
				
			} else if(reportsForm.getMainReportSelect().equalsIgnoreCase("Rebuttal")) {
				
				ROOT_URI = new String(HomeController.RAD_WS_URI + "getRebuttalReport");
				
				ResponseEntity<HashMap> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, reportsForm, HashMap.class);
				ObjectMapper mapper = new ObjectMapper();
				resultsMap = responseEntity.getBody();
				List<Rebuttal> rebuttalList = mapper.convertValue(resultsMap.values(), new TypeReference<List<Rebuttal>>() { });
				
				finalResultsMap = generateRebuttalReport(rebuttalList,session);
				finalSortedMap.putAll(finalResultsMap);
				model.addAttribute("REBUTTAL_REPORT",finalSortedMap);
				model.addAttribute("RebuttalReport",true);
				model.addAttribute("ReportTitle","Rebuttal Report");
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("reportsForm", reportsForm);
		return "macjurisreviewreports";
	}
	
	
	private HashMap<String,QamMacByJurisdictionReviewReport> generateScoreCardReport(List<ScoreCard> scoreCardList,HttpSession session) {
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport>();
		HashMap<String,ArrayList<ScoreCard>> qamMacByJurisReportSessionObject = new HashMap<String,ArrayList<ScoreCard>>();
		
		for(ScoreCard scoreCard: scoreCardList) {
			MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
			if(macInfo != null) {
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				
				ArrayList<ScoreCard> scoreCardListTemp = qamMacByJurisReportSessionObject.get(macNameTemp+"_"+jurisdictionTemp);
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
				String scoreCardType = scoreCard.getScorecardType();
				
				if(scoreCardListTemp == null) {
					scoreCardListTemp = new ArrayList<ScoreCard>();
				} 
				
				scoreCardListTemp.add(scoreCard);
				qamMacByJurisReportSessionObject.put(macNameTemp+"_"+jurisdictionTemp, scoreCardListTemp);			
								
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					qamMacByJurisdictionReviewReport.setQamStartDate(macInfo.getQamStartDate());
					qamMacByJurisdictionReviewReport.setQamEndDate(macInfo.getQamEndDate());
					qamMacByJurisdictionReviewReport.setMacId(macInfo.getId().intValue());
					qamMacByJurisdictionReviewReport.setScoreCardType(scoreCardType);
					qamMacByJurisdictionReviewReport.setTotalCount(1);
					
					if(scoreCardType.equalsIgnoreCase("Scoreable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(1);
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
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
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
							qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scoreable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(qamMacByJurisdictionReviewReport.getNonScorableCount()+1);	
					} else if(scoreCardType.equalsIgnoreCase("Does Not Count")) {
						qamMacByJurisdictionReviewReport.setDoesNotCount_Number(qamMacByJurisdictionReviewReport.getDoesNotCount_Number()+1);	
					}
				}
				
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReport);
				
			}
		}
		
		for(String macJurisKey: finalResultsMap.keySet()) {
			
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
			
			if(qamMacByJurisdictionReviewReport.getScoreCardType().equalsIgnoreCase("Scoreable")) {
				Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
				Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
				qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
				qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);
				
			} else if(qamMacByJurisdictionReviewReport.getScoreCardType().equalsIgnoreCase("Non-Scoreable")) {
				Float scNsPercent =  ((float)qamMacByJurisdictionReviewReport.getNonScorableCount()*100/(qamMacByJurisdictionReviewReport.getTotalCount()));
				scNsPercent =  Float.valueOf((twoDForm.format(scNsPercent)));
				qamMacByJurisdictionReviewReport.setNonScorablePercent(scNsPercent);		
			}  else if(qamMacByJurisdictionReviewReport.getScoreCardType().equalsIgnoreCase("Does Not Count")) {
				Float dncPercent =  ((float)qamMacByJurisdictionReviewReport.getDoesNotCount_Number()*100/(qamMacByJurisdictionReviewReport.getTotalCount()));
				dncPercent =  Float.valueOf((twoDForm.format(dncPercent)));
				qamMacByJurisdictionReviewReport.setDoesNotCount_Percent(dncPercent);		
			}
			
			finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
		}
		
		session.setAttribute("MAC_BY_JURIS_REPORT_SESSION_OBJECT", qamMacByJurisReportSessionObject);
		
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
				calObject.setTime(scoreCard.getQamEnddateTime());
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
							if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
								qamMacByJurisdictionReviewReport.setHhhScorablePass(1);
							} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
								qamMacByJurisdictionReviewReport.setHhhScorableFail(1);
							}
						} 
					} else {
						qamMacByJurisdictionReviewReport.setTotalCount(1);
						
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setScorableCount(1);
							if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
								qamMacByJurisdictionReviewReport.setScorablePass(1);
							} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
								qamMacByJurisdictionReviewReport.setScorableFail(1);
							}
						} 
					}
					
					
					
				} else {
					if(hhhScoreCard) {
						qamMacByJurisdictionReviewReport.setHhhTotalCount(qamMacByJurisdictionReviewReport.getHhhTotalCount()+1);
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setHhhScorableCount(qamMacByJurisdictionReviewReport.getHhhScorableCount()+1);
							if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
								qamMacByJurisdictionReviewReport.setHhhScorablePass(qamMacByJurisdictionReviewReport.getHhhScorablePass()+1);
							} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
								qamMacByJurisdictionReviewReport.setHhhScorableFail(qamMacByJurisdictionReviewReport.getHhhScorableFail()+1);
							}
						} 
					} else {
						qamMacByJurisdictionReviewReport.setTotalCount(qamMacByJurisdictionReviewReport.getTotalCount()+1);
						if(scoreCardType.equalsIgnoreCase("Scoreable")) {
							qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
							if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
								qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);
							} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
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
				Float scPassPercent =  ((float)qamMacByJurisdictionReviewReport.getScorablePass()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scPassPercent =  Float.valueOf((twoDForm.format(scPassPercent)));
				Float scFailPercent =  ((float)qamMacByJurisdictionReviewReport.getScorableFail()*100/qamMacByJurisdictionReviewReport.getScorableCount());
				scFailPercent =  Float.valueOf((twoDForm.format(scFailPercent)));
				qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
				qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);
				
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
}