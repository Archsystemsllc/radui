package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.impl.SecurityServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class ScoreCardController {
	private static final Logger log = Logger.getLogger(ScoreCardController.class);	
	 
	 SecurityServiceImpl securityServiceImpl; 
	 
	 @Autowired
	 UtilityFunctions utilityFunctions;
	 
 
    /**
     * This method provides the functionalities for getting the scorecard jsp.
     * 
     * @param 
     * @return scorecard
     */	 

	 
	@RequestMapping(value ={"/admin/scorecardlist/{sessionBack}", "/quality_manager/scorecardlist/{sessionBack}", "/cms_user/scorecardlist/{sessionBack}",
			 "/mac_admin/scorecardlist/{sessionBack}","/mac_user/scorecardlist/{sessionBack}","/quality_monitor/scorecardlist/{sessionBack}"})	
	public String getScorecardList(@ModelAttribute("scorecard") ScoreCard scoreCardFromModel, final BindingResult result,
			final Model model,HttpServletRequest request, Authentication authentication,@PathVariable("sessionBack") final String sessionBackObject, HttpSession session) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardNew = null;
		ScoreCard scoreCardFailObject = null;
		HashMap<Integer,String> locationMap = new HashMap<Integer, String> ();
		HashMap<Integer,String> jurisMap = null;
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		
		
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		ArrayList<Integer> programIdArrayList = new ArrayList<Integer> ();
		String roles = authentication.getAuthorities().toString();
		
		model.addAttribute("menu_highlight", "scorecard");
		String objectType = "";
		
		//SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");		
		
		String initialFromDateString = "", initialToDateString = "";
		Date today = new Date();			
		
		try {
			

			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			ScoreCard scoreCardFromSession = (ScoreCard) request.getSession().getAttribute("SESSION_SCOPE_SCORECARD_FILTER");
			if (scoreCardFromModel.getMacId() != null && scoreCardFromModel.getJurisIdReportSearchString() !=null ){
				if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					scoreCardFailObject = new ScoreCard();
				}
				scoreCardNew = scoreCardFromModel;				
				objectType = "Model";
				initialFromDateString = scoreCardNew.getFilterFromDateString();
				initialToDateString = scoreCardNew.getFilterToDateString();
				
				
			} else if(scoreCardFromSession != null && sessionBackObject.equalsIgnoreCase("true")) {
				if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					scoreCardFailObject = new ScoreCard();
				}
				//Back Button is Clicked				
				scoreCardNew = scoreCardFromSession;
				objectType = "Session";
				initialFromDateString = scoreCardNew.getFilterFromDateString();
				initialToDateString = scoreCardNew.getFilterToDateString();
			} else {
				//ScoreCard Menu Item Is Clicked
				scoreCardNew = new ScoreCard();
				objectType = "New";
				String[] tempValues = {UIGenericConstants.ALL_STRING};
				scoreCardNew.setJurisIdReportSearchString(tempValues);
				
				//Restricting From Date to 6 months from Current Date
				Calendar fromDateCalendar = Calendar.getInstance();
				fromDateCalendar.setTime(today);
				
				fromDateCalendar.add(Calendar.MONTH, -6);
				String fromDate = utilityFunctions.convertToStringFromDate(fromDateCalendar.getTime(), UIGenericConstants.DATE_TYPE_ONLY_DATE);
							
				scoreCardNew.setFilterFromDateString(fromDate);
				
				if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					scoreCardFailObject = new ScoreCard();
					// Restricting Mac User and Mac Admin to only see data until 15th of the Month, based on the day
					Calendar toDateCalendar = Calendar.getInstance();
					Integer dayOfMonth = toDateCalendar.get(Calendar.DAY_OF_MONTH);
									
					if(dayOfMonth < 15) {					
						toDateCalendar.add(Calendar.MONTH, -1);					
					} 
					
					toDateCalendar.set(Calendar.DATE, 14);		
					String toDate = utilityFunctions.convertToStringFromDate(toDateCalendar.getTime(), UIGenericConstants.DATE_TYPE_ONLY_DATE);
							
					
					scoreCardFailObject.setFilterFromDateString(fromDate);
					
					scoreCardNew.setFilterToDateString(toDate);
					scoreCardFailObject.setFilterToDateString(toDate);
					
				} else {
					// Restricting Mac User and Mac Admin to only see data until 15th of the Month, based on the day
					Calendar toDateCalendar = Calendar.getInstance();
					toDateCalendar.setTime(today);
					String toDate = utilityFunctions.convertToStringFromDate(toDateCalendar.getTime(), UIGenericConstants.DATE_TYPE_ONLY_DATE);
										
					scoreCardNew.setFilterToDateString(toDate);
					
				}
				initialFromDateString = scoreCardNew.getFilterFromDateString();
				initialToDateString = scoreCardNew.getFilterToDateString();
			}
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");		
				HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
				HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
				
				model.addAttribute("macIdMapEdit", loggedInUserMacMap);		
				model.addAttribute("jurisMapEdit", loggedInUserJurisdictionMaps);
					
					if(loggedInJurisdictionIdList !=null && !loggedInJurisdictionIdList.equalsIgnoreCase("") && objectType.equalsIgnoreCase("New")) {
						String[] jurisIdStrings = loggedInJurisdictionIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
						
						for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
							jurIdArrayList.add(jurisIdSingle);
							HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
							if (programTempMap == null) continue;
							
							programMap.putAll(programTempMap);
							for(Integer programIdSingle: programTempMap.keySet()) {
								programIdArrayList.add(programIdSingle);
								HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(loggedInUserMacId+"_"+jurisIdSingle+"_"+programIdSingle);
								if (locationTempMap == null) continue;
								locationMap.putAll(locationTempMap);
								locationTempMap = null;
							}
							
							programTempMap = null;
						}
						
						model.addAttribute("programMapEdit", programMap);	
						model.addAttribute("locationMapEdit", locationMap);	
											
						scoreCardNew.setJurIdList(jurIdArrayList);
						scoreCardFailObject.setJurIdList(jurIdArrayList);
						scoreCardNew.setProgramId(0);
						
						scoreCardNew.setProgramIdList(programIdArrayList);
						scoreCardFailObject.setProgramIdList(programIdArrayList);
						
											
					} else {						
						
						String[] jurisIds = scoreCardNew.getJurisIdReportSearchString();
						
						String jurIds = scoreCardNew.getJurisIdReportSearchString().toString();
						
						if(jurisIds[0].equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
							scoreCardNew.setJurisdictionName(UIGenericConstants.ALL_STRING);
							
							for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
								jurIdArrayList.add(jurisIdSingle);
								
								if(scoreCardNew.getProgramId()==0 ) {
									//scoreCardNew.setProgramName(reportsForm.getProgramId());
									HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
									if (programTempMap == null) continue;
									
									programMap.putAll(programTempMap);
									
									for(Integer programIdSingle: programTempMap.keySet()) {
										programIdArrayList.add(programIdSingle);
									}
									/*if(scoreCardNew.getPccLocationId()==0) {
										for(Integer programIdSingle: programTempMap.keySet()) {
											HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle+"_"+programIdSingle);
											if (locationTempMap == null) continue;
											locationMap.putAll(locationTempMap);
											locationTempMap = null;
										}
									}*/
									
									programTempMap = null;
								} else {
									scoreCardFailObject.setProgramId(scoreCardNew.getProgramId());
								}
							}
							
							scoreCardNew.setProgramIdList(programIdArrayList);
							scoreCardFailObject.setProgramIdList(programIdArrayList);
							
							scoreCardNew.setJurIdList(jurIdArrayList);			
							scoreCardFailObject.setJurIdList(jurIdArrayList);
						} else {
							
							for (String jurisIdSingleValue: jurisIds) {
								jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));								
								String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));							
							}
							
							scoreCardNew.setJurIdList(jurIdArrayList);		
							scoreCardFailObject.setJurIdList(jurIdArrayList);
							
							scoreCardFailObject.setProgramId(scoreCardNew.getProgramId());
						}
						
						
					}
					
					String[] jurisIdStrings = loggedInJurisdictionIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
					programMap = new HashMap<Integer, String> ();
				    locationMap = new HashMap<Integer, String> ();
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
					
					//scoreCardNew.setCallResult(UIGenericConstants.QUALITY_MONITOR_PASS_STRING);
					
					scoreCardNew.setMacId(loggedInUserMacId);		
					scoreCardNew.setFinalScoreCardStatus(UIGenericConstants.FINAL_STATUS_PASS_STRING);
					
					
					
					scoreCardFailObject.setMacId(loggedInUserMacId);
					
					scoreCardFailObject.setFinalScoreCardStatus(UIGenericConstants.FINAL_STATUS_FAIL_STRING);
					
			} else {
				model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);				
				
				if(scoreCardNew.getMacId() == null || scoreCardNew.getMacId() == 0) {
					model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);
					model.addAttribute("programMapEdit", HomeController.ALL_PROGRAM_MAP);
				} else {
					jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCardNew.getMacId());
					model.addAttribute("jurisMapEdit", jurisMap);
				}
				
				if(objectType.equalsIgnoreCase("New")) {
					scoreCardNew.setProgramId(0);
				} else {
					
					String[] jurisIds = scoreCardNew.getJurisIdReportSearchString();
					
					String jurIds = scoreCardNew.getJurisIdReportSearchString().toString();
					
					if(jurisIds[0].equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
						scoreCardNew.setJurisdictionName(UIGenericConstants.ALL_STRING);
						
						for(Integer jurisIdSingle: HomeController.JURISDICTION_MAP.keySet()) {
							jurIdArrayList.add(jurisIdSingle);
							
							if(scoreCardNew.getProgramId() == null || scoreCardNew.getProgramId()==0 ) {
								//scoreCardNew.setProgramName(reportsForm.getProgramId());
								HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCardNew.getMacId()+"_"+jurisIdSingle);
								if (programTempMap == null) continue;
								
								programMap.putAll(programTempMap);
								
								for(Integer programIdSingle: programTempMap.keySet()) {
									programIdArrayList.add(programIdSingle);
								}
								/*if(scoreCardNew.getPccLocationId()==0) {
									for(Integer programIdSingle: programTempMap.keySet()) {
										HashMap<Integer, String> locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle+"_"+programIdSingle);
										if (locationTempMap == null) continue;
										locationMap.putAll(locationTempMap);
										locationTempMap = null;
									}
								}*/
								
								programTempMap = null;
							}						
						}
						
						model.addAttribute("programMapEdit", programMap);
						scoreCardNew.setProgramIdList(programIdArrayList);
						
						
						scoreCardNew.setJurIdList(jurIdArrayList);			
						
					} else {
						
						for (String jurisIdSingleValue: jurisIds) {
							jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));								
							String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));		
							
							HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCardNew.getMacId()+"_"+jurisIdSingleValue);
							if (programTempMap == null) continue;
							
							programMap.putAll(programTempMap);
							
							for(Integer programIdSingle: programTempMap.keySet()) {
								programIdArrayList.add(programIdSingle);
							}
							
						}
						model.addAttribute("programMapEdit", programMap);
						scoreCardNew.setJurIdList(jurIdArrayList);		
						
					}
					
				}
				
				
			}			
			
			/*if(roles.contains("Quality Monitor")) {
				scoreCardNew.setUserId(userFormFromSession.getId().intValue());
			}*/
			
			HashMap<Integer, ScoreCard> resultsMap = retrieveScoreCardList(scoreCardNew, scoreCardFailObject);
			ObjectMapper mapper = new ObjectMapper();
			//Convert the result set to string and replace single character with spaces
			model.addAttribute("scoreCardList",mapper.writeValueAsString(resultsMap.values()).replaceAll("'", " "));		
			
			scoreCardNew.setFilterFromDateString(initialFromDateString);
			scoreCardNew.setFilterToDateString(initialToDateString);
			
			model.addAttribute("scorecard", scoreCardNew);
			model.addAttribute("ScoreCardFilter",true);
			
			request.getSession().setAttribute("SESSION_SCOPE_SCORECARD_FILTER", scoreCardNew);
			request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "scorecardlist";
	}
	
	
	private HashMap<Integer,ScoreCard> retrieveScoreCardList(ScoreCard scoreCardModelObject, ScoreCard scoreCardFailObject) {	
		log.debug("--> Enter retrieveScoreCardList <--");
		
		List<ScoreCard> resultsMap = new ArrayList<ScoreCard> ();
		HashMap<Integer, ScoreCard> finalResultsMap = new HashMap<Integer, ScoreCard> ();
		
		try {
		
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchScoreCard");
			
			if(scoreCardModelObject.getJurisIdReportSearchString() != null && scoreCardModelObject.getJurisIdReportSearchString().length > 0 && scoreCardModelObject.getJurIdList() == null) {
				ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
				
				String[] jurisIds = scoreCardModelObject.getJurisIdReportSearchString();
				
				for (String jurisIdSingleValue: jurisIds) {
					if(jurisIdSingleValue.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
						break;
					}
					jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
				}
				
				scoreCardModelObject.setJurIdList(jurisdictionArrayList);
			}
			
			if(scoreCardModelObject.getFilterFromDateString() != null && 
					!scoreCardModelObject.getFilterFromDateString().equalsIgnoreCase("")) {
				String filterFromDateString = scoreCardModelObject.getFilterFromDateString() + " 00:00:00 AM";
				
				scoreCardModelObject.setFilterFromDateString(filterFromDateString);
				
				if (scoreCardFailObject != null) {
					scoreCardFailObject.setFilterFromDateString(filterFromDateString);
				}
			}
			
			
			if(scoreCardModelObject.getFilterToDateString() != null && 
					!scoreCardModelObject.getFilterToDateString().equalsIgnoreCase("")) {
				String filterToDateString = scoreCardModelObject.getFilterToDateString() + " 11:59:59 PM";
				
				scoreCardModelObject.setFilterToDateString(filterToDateString);
				
				if (scoreCardFailObject != null) {
					scoreCardFailObject.setFilterToDateString(filterToDateString);
				}
			}
			
			
			//Scorecard All List
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCardModelObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap, new TypeReference<List<ScoreCard>>() { });			
			
			List<ScoreCard> scoreCardFailList = null;
			//Scorecard Fail List
			
			if (scoreCardFailObject != null) {
				BasicAuthRestTemplate basicAuthRestTemplate2 = new BasicAuthRestTemplate("qamadmin", "123456");
				ResponseEntity<List> responseEntity2 = basicAuthRestTemplate2.postForEntity(ROOT_URI, scoreCardFailObject, List.class);
				
				resultsMap = responseEntity2.getBody();
				scoreCardFailList = mapper.convertValue(resultsMap, new TypeReference<List<ScoreCard>>() { });
				
				if(scoreCardFailList!=null) {
					if (scoreCardList == null) {
						scoreCardList = new ArrayList<ScoreCard>();
						scoreCardList.addAll(scoreCardFailList);
					} else {
						scoreCardList.addAll(scoreCardFailList);
					}
				}
			}
			
			for(ScoreCard scoreCardTemp: scoreCardList) {
				String qamStartdateTimeString = utilityFunctions.convertToStringFromDate(scoreCardTemp.getQamStartdateTime(), UIGenericConstants.DATE_TYPE_FULL);
				scoreCardTemp.setQamStartdateTimeString(qamStartdateTimeString);
				scoreCardTemp.setMacName(HomeController.MAC_ID_MAP.get(scoreCardTemp.getMacId()));
				scoreCardTemp.setJurisdictionName(HomeController.JURISDICTION_MAP.get(scoreCardTemp.getJurId()));
				finalResultsMap.put(scoreCardTemp.getId(), scoreCardTemp);
			}		
		} catch (Exception e) {
			log.error("--> Error in retrieveScoreCardList <--"+e.getMessage());
		}
		log.debug("--> Enter retrieveScoreCardList <--");
		return finalResultsMap;
	}
	
	 @RequestMapping(value ={"/admin/edit-scorecard/{id}/{reportSearchString}", "/quality_manager/edit-scorecard/{id}/{reportSearchString}", "/cms_user/edit-scorecard/{id}/{reportSearchString}",
			 "/mac_admin/edit-scorecard/{id}/{reportSearchString}","/mac_user/edit-scorecard/{id}/{reportSearchString}","/quality_monitor/edit-scorecard/{id}/{reportSearchString}"}, method = RequestMethod.GET)	
	public String editScoreCardGet(@PathVariable("id") final Integer id, @PathVariable("reportSearchString") final String reportSearchString, final Model model, 
			Authentication authentication, HttpSession session) {
		
		 
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		
		model.addAttribute("menu_highlight", "scorecard");
		model.addAttribute("ReportSearchString", reportSearchString);
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime(), UIGenericConstants.DATE_TYPE_FULL);
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime(), UIGenericConstants.DATE_TYPE_FULL);
		//String callMonitoringDateString = utilityFunctions.convertToStringFromLocalDate(scoreCard.getCallMonitoringDate(), UIGenericConstants.DATE_TYPE_ONLY_DATE);
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		//scoreCard.setCallMonitoringDateString(callMonitoringDateString);
		
		
		//System Screen Access--Start
		if(scoreCard.getSystemScreenAccess() != null && !scoreCard.getSystemScreenAccess().equalsIgnoreCase("")) {
			String systemScreenAccessArray[] = scoreCard.getSystemScreenAccess().split(",");
			
			scoreCard.setSystemScreenAccessArray(systemScreenAccessArray);
		}
		
		
		//System Screen Access--End
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");		
			
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
			
			scoreCard.setMacId(loggedInUserMacId);
			String[] jurisIdStrings = loggedInJurisdictionIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			scoreCard.setJurisIdReportSearchString(jurisIdStrings);				
			
			model.addAttribute("macIdMapEdit", loggedInUserMacMap);		
			model.addAttribute("jurisMapEdit", loggedInUserJurisdictionMaps);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);	
			
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
			model.addAttribute("jurisMapEdit", jurisMap);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		}
		
		/*String[] callCategorySubCatMsIds = scoreCard.getCallCatSubCatMsStringArray();
		String callSubCategoryStringMsTemp = "";
		for(String callCatSubCatMsIdSingleValue: callCategorySubCatMsIds) {
			if(!callCatSubCatMsIdSingleValue.equalsIgnoreCase("")) {					
				callSubCategoryStringMsTemp += callCatSubCatMsIdSingleValue+",";					
			}				
		}
		scoreCard.setCallCatSubCatMsString(callSubCategoryStringMsTemp);*/
			
		//model.addAttribute("subCategoryMapListEdit", subCategoryMapFinal);
		
		/*if(scoreCard.getCallSubCategoryIdKnoweledgeSkills() != null 
				&& !scoreCard.getCallSubCategoryIdKnoweledgeSkills().equalsIgnoreCase("")) {
			String[] tempString = scoreCard.getCallSubCategoryIdKnoweledgeSkills().split(",");
			
			scoreCard.setCscidKsUi(tempString);
		}*/
		
		String[] callCategorySubCatMsIds = {};
		String callSubCategoryStringMsTemp = scoreCard.getCallCatSubCatMsString();
		if(callSubCategoryStringMsTemp!=null && !callSubCategoryStringMsTemp.equalsIgnoreCase("")) {
			callCategorySubCatMsIds = callSubCategoryStringMsTemp.split(",");			
			scoreCard.setCallCatSubCatMsStringArray(callCategorySubCatMsIds);
		}
		
		
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		//model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategoryMapEdit", subCategorylMap);		
		
		model.addAttribute("callCatSubCatMultiSelectMap", HomeController.CALL_CAT_SUB_CAT_MULTI_SELECT_MAP);
		model.addAttribute("scorecard", scoreCard);
		return "scorecard";
	}
	 
	 @RequestMapping(value ={"/admin/view-scorecard/{id}/{reportSearchString}", "/quality_manager/view-scorecard/{id}/{reportSearchString}", "/cms_user/view-scorecard/{id}/{reportSearchString}",
			 "/mac_admin/view-scorecard/{id}/{reportSearchString}","/mac_user/view-scorecard/{id}/{reportSearchString}","/quality_monitor/view-scorecard/{id}/{reportSearchString}"}, method = RequestMethod.GET)
	public String viewScoreCardGet(@PathVariable("id") final Integer id, @PathVariable("reportSearchString") final String reportSearchString, final Model model,Authentication authentication,
			HttpSession session) {
		
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		model.addAttribute("menu_highlight", "scorecard");
		model.addAttribute("ReportSearchString", reportSearchString);
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime(), UIGenericConstants.DATE_TYPE_FULL);
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime(), UIGenericConstants.DATE_TYPE_FULL);
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		
		//System Screen Access--Start
		if(scoreCard.getSystemScreenAccess() != null && !scoreCard.getSystemScreenAccess().equalsIgnoreCase("")) {
			String systemScreenAccessArray[] = scoreCard.getSystemScreenAccess().split(",");
			
			scoreCard.setSystemScreenAccessArray(systemScreenAccessArray);
		}
		
		
		//System Screen Access--End
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");		
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
			
			model.addAttribute("macIdMapEdit", loggedInUserMacMap);		
			model.addAttribute("jurisMapEdit", loggedInUserJurisdictionMaps);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);
			
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
			model.addAttribute("jurisMapEdit", jurisMap);
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		}
		
		model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategoryMapEdit", subCategorylMap);
		
		String[] callCategorySubCatMsIds = {};
		String callSubCategoryStringMsTemp = scoreCard.getCallCatSubCatMsString();
		if(callSubCategoryStringMsTemp!=null && !callSubCategoryStringMsTemp.equalsIgnoreCase("")) {
			callCategorySubCatMsIds = callSubCategoryStringMsTemp.split(",");			
			scoreCard.setCallCatSubCatMsStringArray(callCategorySubCatMsIds);
		}
		
		
		
		model.addAttribute("callCatSubCatMultiSelectMap", HomeController.CALL_CAT_SUB_CAT_MULTI_SELECT_MAP);
		model.addAttribute("scorecard", scoreCard);
		return "scorecardview";
	}
	
	
	 @RequestMapping(value ={"/admin/new-scorecard", "/quality_manager/new-scorecard", "/cms_user/new-scorecard",
			 "/mac_admin/new-scorecard","/mac_user/new-scorecard","/quality_monitor/new-scorecard"}, method = RequestMethod.GET)
	
	public String newScoreCardGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		ScoreCard scoreCard = new ScoreCard();
		HashMap<Integer,String> programMap = null;
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username 
		scoreCard.setQamFullName(name);
		
		model.addAttribute("menu_highlight", "scorecard");
		model.addAttribute("ReportSearchString","null");
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date(), UIGenericConstants.DATE_TYPE_FULL);
		
		scoreCard.setQamStartdateTimeString(qamStartdateTime);
		scoreCard.setCallLanguage("English");
		model.addAttribute("scorecard", scoreCard);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");	
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
			
			model.addAttribute("macIdMapEdit", loggedInUserMacMap);		
			model.addAttribute("jurisMapEdit", loggedInUserJurisdictionMaps);	
			
			programMap = new HashMap<Integer, String> ();
			
			for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
				jurIdArrayList.add(jurisIdSingle);
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
								
				programTempMap = null;
			}
			
			model.addAttribute("programMapEdit", programMap);	
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);
		}
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		model.addAttribute("callCatSubCatMultiSelectMap", HomeController.CALL_CAT_SUB_CAT_MULTI_SELECT_MAP);	
		
		
		return "scorecard";
	}

	
	@RequestMapping(value ={"/admin/saveorupdatescorecard/{reportSearchString}", "/quality_manager/saveorupdatescorecard/{reportSearchString}", "/cms_user/saveorupdatescorecard/{reportSearchString}",
			 "/mac_admin/saveorupdatescorecard/{reportSearchString}","/mac_user/saveorupdatescorecard/{reportSearchString}","/quality_monitor/saveorupdatescorecard/{reportSearchString}"}, method = RequestMethod.POST)
	public String saveScorecard(@ModelAttribute("scorecard") ScoreCard scoreCard, final BindingResult result,
			final RedirectAttributes redirectAttributes, @PathVariable("reportSearchString") final String reportSearchString, final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {

		String returnView = "";
		log.debug("--> savescorecard <--");
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		model.addAttribute("menu_highlight", "scorecard");
		
		String validationResult = validateScoreCard(scoreCard);
		
		String loggedInUserRole = userFormSession.getRole().getRoleName();
		
		String macName ="";
		String jurisdictionName = "";
		
		if(!validationResult.equalsIgnoreCase("Validation Succesfull")) {
			
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");	
				HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
				HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
				
				
				programMap = new HashMap<Integer, String> ();
				
				for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
					jurIdArrayList.add(jurisIdSingle);
					HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
					if (programTempMap == null) continue;
					
					programMap.putAll(programTempMap);									
					programTempMap = null;
				}
				
				
			} else {
				model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);
				
				HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				model.addAttribute("jurisMapEdit", jurisMap);
				programMap = new HashMap<Integer, String> ();
				HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
				for(Integer jurisIdSingle: loggedInUserJurisdictionMaps.keySet()) {
					jurIdArrayList.add(jurisIdSingle);
					HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(loggedInUserMacId+"_"+jurisIdSingle);
					if (programTempMap == null) continue;
					
					programMap.putAll(programTempMap);
									
					programTempMap = null;
				}
			}
			
			HashMap<Integer,String> subCategoryMapFinal = new HashMap<Integer, String>();	
			
			
			String[] callCategorySubCatMsIds = scoreCard.getCallCatSubCatMsStringArray();
			String callSubCategoryStringMsTemp = "";
			for(String callCatSubCatMsIdSingleValue: callCategorySubCatMsIds) {
				if(!callCatSubCatMsIdSingleValue.equalsIgnoreCase("")) {					
					callSubCategoryStringMsTemp += callCatSubCatMsIdSingleValue+",";					
				}				
			}
			scoreCard.setCallCatSubCatMsString(callSubCategoryStringMsTemp);
	
			model.addAttribute("subCategoryMapListEdit", subCategoryMapFinal);
			HashMap<Integer, String> loggedInUserJurisdictionMaps = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP");
			HashMap<Integer, String> loggedInUserMacMap = (HashMap) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP");
			model.addAttribute("macIdMapEdit", loggedInUserMacMap);		
			model.addAttribute("jurisMapEdit", loggedInUserJurisdictionMaps);	
			model.addAttribute("programMapEdit", programMap);	
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			
			HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
			model.addAttribute("subCategoryMapEdit", subCategorylMap);
			model.addAttribute("scorecard", scoreCard);
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			model.addAttribute("callCatSubCatMultiSelectMap", HomeController.CALL_CAT_SUB_CAT_MULTI_SELECT_MAP);
			model.addAttribute("ValidationFailure", validationResult);
			redirectAttributes
			.addFlashAttribute("error", "validation.failed.scorecard");
			model.addAttribute("ReportSearchString", reportSearchString);
			returnView = "scorecard";
		} else {			
			
			try {
				
				HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
				User userForm = (User) session.getAttribute("LoggedInUserForm");
				ScoreCard existingScoreCard = resultsMap.get(scoreCard.getId());
				
				Date currentDateTime = new Date();
				
				String callResult = "";
				String qmCalibrationStatus = "";
				String cmcCalibrationSataus = "";
								
				if(scoreCard.getCallResult() != null && scoreCard.getCallResult() !="") {
					callResult = scoreCard.getCallResult(); 
				}
				
				if (existingScoreCard != null && existingScoreCard.getCmsCalibrationStatus() != null && !existingScoreCard.getCmsCalibrationStatus().equalsIgnoreCase(scoreCard.getCmsCalibrationStatus()) ) {
					scoreCard.setCmsCalibrationUpdateDateTime(currentDateTime);
					
				} 
				
				if (existingScoreCard != null &&
						 existingScoreCard.getQamCalibrationStatus() != null && !existingScoreCard.getQamCalibrationStatus().equalsIgnoreCase(scoreCard.getQamCalibrationStatus()) ) {
					scoreCard.setQamCalibrationUpdateDateTime(currentDateTime);
					
				}  
				
				if(existingScoreCard != null && existingScoreCard.getCallResult() != null && !existingScoreCard.getCallResult().equalsIgnoreCase(scoreCard.getCallResult())) {
					scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
				}
				
				
				
				String[] callCategorySubCatMsIds = scoreCard.getCallCatSubCatMsStringArray();
				String callSubCategoryStringMsTemp = "";
				for(String callCatSubCatMsIdSingleValue: callCategorySubCatMsIds) {
					if(!callCatSubCatMsIdSingleValue.equalsIgnoreCase("")) {					
						callSubCategoryStringMsTemp += callCatSubCatMsIdSingleValue+",";					
					}				
				}
				scoreCard.setCallCatSubCatMsString(callSubCategoryStringMsTemp);
				
				//Setting Call Monitoring Date, because of Issue on 02/06/2019
				String callMonitoringDateStringTemp = scoreCard.getCallMonitoringDateString() + " 11:59:59 AM";
				scoreCard.setCallMonitoringDateString(callMonitoringDateStringTemp);
				
				BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateScoreCard");
				
				//LocalDate callMonitoringDate = utilityFunctions.convertToLocalDateFromString(scoreCard.getCallMonitoringDateString(), UIGenericConstants.DATE_TYPE_ONLY_DATE);
				//scoreCard.setCallMonitoringDate(callMonitoringDate);
				Date qamStartDateTime = utilityFunctions.convertToDateFromString(scoreCard.getQamStartdateTimeString(), UIGenericConstants.DATE_TYPE_FULL);
				scoreCard.setQamStartdateTime(qamStartDateTime);
			     
				String qamEnddateTimeString = utilityFunctions.convertToStringFromDate(currentDateTime, UIGenericConstants.DATE_TYPE_FULL);
				Date qamEnddateTime = utilityFunctions.convertToDateFromString(qamEnddateTimeString, UIGenericConstants.DATE_TYPE_FULL);
				scoreCard.setQamEnddateTimeString(qamEnddateTimeString);
				scoreCard.setQamEnddateTime(qamEnddateTime);
				scoreCard.setUserId(userFormSession.getId().intValue());
				scoreCard.setQamId(userFormSession.getId().intValue());
				scoreCard.setMacName(HomeController.MAC_ID_MAP.get(scoreCard.getMacId()));
				scoreCard.setJurisdictionName(HomeController.JURISDICTION_MAP.get(scoreCard.getJurId()));
				scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
				//System Screen Access--Start
				//System Screen Access--Start
				if(scoreCard.getSystemScreenAccessArray() != null && scoreCard.getSystemScreenAccessArray().length > 0) {
					String systemScreenAccess = String.join(",", scoreCard.getSystemScreenAccessArray());
					
					scoreCard.setSystemScreenAccess(systemScreenAccess);
				}
				
				
				//System Screen Access--End
				macName= scoreCard.getMacName();
				jurisdictionName = scoreCard.getJurisdictionName();
				
				//System Screen Access--End
				ResponseEntity<ScoreCard> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard,ScoreCard.class);
				
				if(scoreCard.getId() == 0) {
					redirectAttributes.addFlashAttribute("success",
							"success.create.scorecard");
				} else {
					redirectAttributes.addFlashAttribute("success",
							"success.edit.scorecard");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(reportSearchString!=null && !reportSearchString.equalsIgnoreCase("") && !reportSearchString.equalsIgnoreCase("null") ) {
				String url = "redirect:/"+userFolder+"/mac-jur-report-drilldown/"+macName+"/"+jurisdictionName+"/"+reportSearchString;
				url = response.encodeRedirectURL(url);
				returnView =  url;
			} else {
				String url = "redirect:/"+userFolder+"/scorecardlist/false";
				url = response.encodeRedirectURL(url);
				returnView =  url;
			}
		}

		return returnView;
	}    
        
    
    private String validateScoreCard(ScoreCard scoreCard) {
    	
    	String validationResultString = "Please Provide Following Fields:";
    	
    	if(scoreCard.getScorecardType().equalsIgnoreCase("Scoreable")) {
    		
    		if(scoreCard.getCsrPrvAccInfo().equalsIgnoreCase("No")) {
    			if(scoreCard.getAccuracyCallFailureReason().equalsIgnoreCase("")) {
    				validationResultString+="Accuracy Call Failure Reason,";
    			}
    			if(scoreCard.getAccuracyCallFailureTime().equalsIgnoreCase("")) {
    				validationResultString+="Accuracy Call Failure Time,";
    			}
    		}
    		
    		if(scoreCard.getCsrPrvCompInfo().equalsIgnoreCase("No")) {
    			if(scoreCard.getCompletenessCallFailureReason().equalsIgnoreCase("")) {
    				validationResultString+="Completeness Call Failure Reason,";
    			}
    			if(scoreCard.getCompletenessCallFailureTime().equalsIgnoreCase("")) {
    				validationResultString+="Completeness Call Failure Time,";
    			}
    		}
    		
    		if(scoreCard.getCsrFallPrivacyProv().equalsIgnoreCase("No")) {
    			if(scoreCard.getPrivacyCallFailureReason().equalsIgnoreCase("")) {
    				validationResultString+="Privacy Call Failure Reason,";
    			}
    			if(scoreCard.getPrivacyCallFailureTime().equalsIgnoreCase("")) {
    				validationResultString+="Privacy Call Failure Time,";
    			}
    		}
    		
    		if(scoreCard.getCsrWasCourteous().equalsIgnoreCase("No")) {
    			if(scoreCard.getCustomerSkillsCallFailureReason().equalsIgnoreCase("")) {
    				validationResultString+="Customer Skills Call Failure Reason,";
    			}
    			if(scoreCard.getCustomerSkillsCallFailureTime().equalsIgnoreCase("")) {
    				validationResultString+="Customer Skills Call Failure Time,";
    			}
    		}
    	} else if(scoreCard.getScorecardType().equalsIgnoreCase("Non-Scoreable")) {
    		
    		if(scoreCard.getNonScoreableReason().equalsIgnoreCase("")) {
				validationResultString+="Non-Scoreable Reason,";
			}
    		
    	} 
    	
    	if(scoreCard.getCsrLevel().equalsIgnoreCase("")) {
    		validationResultString+="Csr Level,";
    	}
    	
    	if(scoreCard.getMacCallReferenceNumber().equalsIgnoreCase("")) {
    		validationResultString+="Mac Call Reference Number";
    	}
    	
    	
    	if(validationResultString.equalsIgnoreCase("Please Provide Following Fields:")) {
    		validationResultString = "Validation Succesfull";
    	}
    	return validationResultString;
    }
}