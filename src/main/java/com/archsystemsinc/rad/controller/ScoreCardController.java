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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.impl.SecurityServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
			final Model model,HttpServletRequest request, Authentication authentication,@PathVariable("sessionBack") final String sessionBackObject) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardNew = null;
		ScoreCard scoreCardFailObject = null;
		HashMap<Integer,String> locationMap = null;
		HashMap<Integer,String> jurisMap = null;
		HashMap<Integer,String> programMap = null;
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		String roles = authentication.getAuthorities().toString();
		
		model.addAttribute("menu_highlight", "scorecard");
		
		try {
			

			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			ScoreCard scoreCardFromSession = (ScoreCard) request.getSession().getAttribute("SESSION_SCOPE_SCORECARD_FILTER");
			if (scoreCardFromModel.getMacId() != null && scoreCardFromModel.getJurisIdReportSearchString() !=null && scoreCardFromModel.getCallResult() !=null){
				scoreCardNew = scoreCardFromModel;				
			} else if(scoreCardFromSession != null && sessionBackObject.equalsIgnoreCase("true")) {
				//Back Button is Clicked				
				scoreCardNew = scoreCardFromSession;
			} else {
				//ScoreCard Menu Item Is Clicked
				scoreCardNew = new ScoreCard();
			}
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					
					model.addAttribute("macMapEdit", HomeController.LOGGED_IN_USER_MAC_MAP);		
					model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
					
					String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
					programMap = new HashMap<Integer, String> ();
					locationMap = new HashMap<Integer, String> ();
					
					for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
						jurIdArrayList.add(jurisIdSingle);
					}
										
					scoreCardNew.setJurIdList(jurIdArrayList);
					scoreCardNew.setCallResult(UIGenericConstants.RESULT_PASS_STRING);
					
					scoreCardNew.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
					
					scoreCardFailObject = new ScoreCard();
					scoreCardFailObject.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
					scoreCardFailObject.setJurisIdReportSearchString(jurisIdStrings);
					scoreCardFailObject.setJurIdList(jurIdArrayList);
					scoreCardFailObject.setCallResult(UIGenericConstants.RESULT_FAIL_STRING);
					scoreCardFailObject.setQamCalibrationStatus(UIGenericConstants.RESULT_FAIL_STRING);
					scoreCardFailObject.setCmsCalibrationStatus(UIGenericConstants.RESULT_FAIL_STRING);
					
			} else {
				model.addAttribute("macMapEdit", HomeController.MAC_ID_MAP);	
				
				if(scoreCardNew.getMacId() == null || scoreCardNew.getMacId() == 0) {
					model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);		
				} else {
					jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCardNew.getMacId());
					model.addAttribute("jurisMapEdit", jurisMap);
				}
			}			
			
			if(roles.contains("Quality Monitor")) {
				scoreCardNew.setUserId(userFormFromSession.getId().intValue());
			}
			
			HashMap<Integer, ScoreCard> resultsMap = retrieveScoreCardList(scoreCardNew,scoreCardFailObject);
			ObjectMapper mapper = new ObjectMapper();
			//Convert the result set to string and replace single character with spaces
			model.addAttribute("scoreCardList",mapper.writeValueAsString(resultsMap.values()).replaceAll("'", " "));
			
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
			
			if(scoreCardModelObject.getJurisIdReportSearchString() != null && scoreCardModelObject.getJurisIdReportSearchString().length > 0) {
				ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
				
				String[] jurisIds = scoreCardModelObject.getJurisIdReportSearchString();
				
				for (String jurisIdSingleValue: jurisIds) {
					if(jurisIdSingleValue.equalsIgnoreCase("ALL")) {
						break;
					}
					jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
				}
				
				scoreCardModelObject.setJurIdList(jurisdictionArrayList);
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
					}
				}
			}
			
			for(ScoreCard scoreCardTemp: scoreCardList) {
				String qamStartdateTimeString = utilityFunctions.convertToStringFromDate(scoreCardTemp.getQamStartdateTime());
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
	
	 @RequestMapping(value ={"/admin/edit-scorecard/{id}", "/quality_manager/edit-scorecard/{id}", "/cms_user/edit-scorecard/{id}",
			 "/mac_admin/edit-scorecard/{id}","/mac_user/edit-scorecard/{id}","/quality_monitor/edit-scorecard/{id}"}, method = RequestMethod.GET)	
	public String editScoreCardGet(@PathVariable("id") final Integer id, final Model model, 
			Authentication authentication, HttpSession session) {
		
		 
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		
		model.addAttribute("menu_highlight", "scorecard");
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime());
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime());
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		
		model.addAttribute("scorecard", scoreCard);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			scoreCard.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
			String[] jurisIdStrings = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
			scoreCard.setJurisIdReportSearchString(jurisIdStrings);				
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);	
			
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
			model.addAttribute("jurisMapEdit", jurisMap);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		}
		
		if(scoreCard.getCallCategoryIdKnoweledgeSkills() != null 
				&& !scoreCard.getCallCategoryIdKnoweledgeSkills().equalsIgnoreCase("")) {
			String[] tempString = scoreCard.getCallCategoryIdKnoweledgeSkills().split(",");
			
			scoreCard.setCallCategoryIdKnoweledgeSkillsUIObject(tempString);
		}
		
		HashMap<Integer,String> subCategoryMapFinal = new HashMap<Integer, String>();	
		
		if(scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject() != null) {
			String[] callCategoryIds = scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject();
			for(String callCategoryIdsSingleValue: callCategoryIds) {
				if(!callCategoryIdsSingleValue.equalsIgnoreCase("")) {
					
					if(callCategoryIdsSingleValue.equalsIgnoreCase("ALL")) {
						
						for(Integer callCategoryId : HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.keySet()) {
							HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
							subCategoryMapFinal.putAll(subCategoryMap);
						}
						break;
					}
					Integer callCategoryId = Integer.valueOf(callCategoryIdsSingleValue);
					HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
					subCategoryMapFinal.putAll(subCategoryMap);
				}			
			}			
		}	

		model.addAttribute("subCategoryMapListEdit", subCategoryMapFinal);
		
		if(scoreCard.getCallSubCategoryIdKnoweledgeSkills() != null 
				&& !scoreCard.getCallSubCategoryIdKnoweledgeSkills().equalsIgnoreCase("")) {
			String[] tempString = scoreCard.getCallSubCategoryIdKnoweledgeSkills().split(",");
			
			scoreCard.setCallSubCategoryIdKnoweledgeSkillsUIObject(tempString);
		}
		
		
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		//model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategoryMapEdit", subCategorylMap);		
		
		return "scorecard";
	}
	 
	 @RequestMapping(value ={"/admin/view-scorecard/{id}", "/quality_manager/view-scorecard/{id}", "/cms_user/view-scorecard/{id}",
			 "/mac_admin/view-scorecard/{id}","/mac_user/view-scorecard/{id}","/quality_monitor/view-scorecard/{id}"}, method = RequestMethod.GET)
	public String viewScoreCardGet(@PathVariable("id") final Integer id, final Model model,Authentication authentication,
			HttpSession session) {
		
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		model.addAttribute("menu_highlight", "scorecard");
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime());
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime());
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		
		model.addAttribute("scorecard", scoreCard);
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
			
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
			model.addAttribute("jurisMapEdit", jurisMap);
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
		}
		
		model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		HashMap<Integer, String> callCatMap = new HashMap<Integer, String>();
		
		if(scoreCard.getCallCategoryIdKnoweledgeSkills() != null) {
			String[] tempValues = scoreCard.getCallCategoryIdKnoweledgeSkills().split(",");
			for(String stringTempValue:tempValues) {
				String callCategoryName = HomeController.CALL_CATEGORY_MAP.get(Integer.valueOf(stringTempValue));
				callCatMap.put(Integer.valueOf(stringTempValue), callCategoryName);
			}
			
		}
		
		model.addAttribute("callCategoryListView", callCatMap);
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategoryMapEdit", subCategorylMap);
		
		HashMap<Integer,String> subCategoryMapFinal = new HashMap<Integer, String>();	
		
		if(scoreCard.getCallCategoryIdKnoweledgeSkills() != null) {
			String[] callCategoryIds = scoreCard.getCallCategoryIdKnoweledgeSkills().split(",");
			for(String callCategoryIdsSingleValue: callCategoryIds) {
				if(!callCategoryIdsSingleValue.equalsIgnoreCase("")) {
					
					if(callCategoryIdsSingleValue.equalsIgnoreCase("ALL")) {
						
						for(Integer callCategoryId : HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.keySet()) {
							HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
							subCategoryMapFinal.putAll(subCategoryMap);
						}
						break;
					}
					Integer callCategoryId = Integer.valueOf(callCategoryIdsSingleValue);
					HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
					subCategoryMapFinal.putAll(subCategoryMap);
				}			
			}			
		}	

		
		HashMap<Integer, String> callSubCatMap = new HashMap<Integer, String>();
		
		if(scoreCard.getCallSubCategoryIdKnoweledgeSkills() != null 
				&& !scoreCard.getCallSubCategoryIdKnoweledgeSkills().equalsIgnoreCase("")) {
			String[] tempValues = scoreCard.getCallSubCategoryIdKnoweledgeSkills().split(",");
			
			for(String stringTempValue:tempValues) {
				String callSubCategoryName = subCategoryMapFinal.get(Integer.valueOf(stringTempValue));
				callSubCatMap.put(Integer.valueOf(stringTempValue), callSubCategoryName);
			}
		}
		
		model.addAttribute("subCategoryMapListEdit", callSubCatMap);		
		
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
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		scoreCard.setQamStartdateTimeString(qamStartdateTime);
		scoreCard.setCallLanguage("English");
		model.addAttribute("scorecard", scoreCard);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
			programMap = new HashMap<Integer, String> ();
			
			for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
				jurIdArrayList.add(jurisIdSingle);
				HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
				if (programTempMap == null) continue;
				
				programMap.putAll(programTempMap);
								
				programTempMap = null;
			}
			
			model.addAttribute("programMapEdit", programMap);	
			
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		}
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		return "scorecard";
	}

	
	@RequestMapping(value ={"/admin/saveorupdatescorecard", "/quality_manager/saveorupdatescorecard", "/cms_user/saveorupdatescorecard",
			 "/mac_admin/saveorupdatescorecard","/mac_user/saveorupdatescorecard","/quality_monitor/saveorupdatescorecard"}, method = RequestMethod.POST)
	public String saveScorecard(@ModelAttribute("scorecard") ScoreCard scoreCard, final BindingResult result,
			final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {

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
		
		if(!validationResult.equalsIgnoreCase("Validation Succesfull")) {
			
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				
				programMap = new HashMap<Integer, String> ();
				
				for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
					jurIdArrayList.add(jurisIdSingle);
					HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
					if (programTempMap == null) continue;
					
					programMap.putAll(programTempMap);									
					programTempMap = null;
				}
				
				
			} else {
				model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
				
				HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
				model.addAttribute("jurisMapEdit", jurisMap);
				programMap = new HashMap<Integer, String> ();
				for(Integer jurisIdSingle: HomeController.LOGGED_IN_USER_JURISDICTION_MAP.keySet()) {
					jurIdArrayList.add(jurisIdSingle);
					HashMap<Integer, String> programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(HomeController.LOGGED_IN_USER_MAC_ID+"_"+jurisIdSingle);
					if (programTempMap == null) continue;
					
					programMap.putAll(programTempMap);
									
					programTempMap = null;
				}
			}
			
			HashMap<Integer,String> subCategoryMapFinal = new HashMap<Integer, String>();	
			
			
			String[] callCategoryIds = scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject();
			for(String callCategoryIdsSingleValue: callCategoryIds) {
				if(!callCategoryIdsSingleValue.equalsIgnoreCase("")) {
					
					if(callCategoryIdsSingleValue.equalsIgnoreCase("ALL")) {
						
						for(Integer callCategoryId : HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.keySet()) {
							HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
							subCategoryMapFinal.putAll(subCategoryMap);
						}
						break;
					}
					Integer callCategoryId = Integer.valueOf(callCategoryIdsSingleValue);
					HashMap<Integer,String> subCategoryMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(callCategoryId);
					subCategoryMapFinal.putAll(subCategoryMap);
				}				
			}
		
	
			model.addAttribute("subCategoryMapListEdit", subCategoryMapFinal);
			
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			model.addAttribute("programMapEdit", programMap);	
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			
			HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
			model.addAttribute("subCategoryMapEdit", subCategorylMap);
			model.addAttribute("scorecard", scoreCard);
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			model.addAttribute("ValidationFailure", validationResult);
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
				
				if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.ADMIN_ROLE_STRING) && 
						existingScoreCard != null && existingScoreCard.getCmsCalibrationStatus() != null && !existingScoreCard.getCmsCalibrationStatus().equalsIgnoreCase(scoreCard.getCmsCalibrationStatus()) ) {
					scoreCard.setCmsCalibrationUpdateDateTime(currentDateTime);
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.QUALITY_MANAGER_ROLE_STRING) && 
						existingScoreCard != null && existingScoreCard.getCmsCalibrationStatus() != null && 
						( (!existingScoreCard.getQamCalibrationStatus().equalsIgnoreCase(scoreCard.getQamCalibrationStatus())) || 
								( existingScoreCard.getCmsCalibrationStatus() != null 
								&& !existingScoreCard.getCmsCalibrationStatus().equalsIgnoreCase(scoreCard.getCmsCalibrationStatus()) ) ) ) {
					scoreCard.setQamCalibrationUpdateDateTime(currentDateTime);
					
				}  
				
				if(existingScoreCard != null && existingScoreCard.getCallResult() != null && !existingScoreCard.getCallResult().equalsIgnoreCase(scoreCard.getCallResult())) {
					scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
				}
				
				/*else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.QUALITY_MONITOR_ROLE_STRING) && 
						(existingScoreCard != null && existingScoreCard.getCmsCalibrationStatus() != null && !existingScoreCard.getCallResult().equalsIgnoreCase(scoreCard.getCallResult()) || existingScoreCard == null)  ){
					scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.CMS_USER_ROLE_STRING)) {
					
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.MAC_ADMIN_ROLE_STRING)) {
					
					
				}*/
				
				
				if(scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject() != null 
						&& scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject().length > 0) {
					String tempString = "";
					for(String singleValue:scoreCard.getCallCategoryIdKnoweledgeSkillsUIObject()) {
						tempString += singleValue +",";
					}
					scoreCard.setCallCategoryIdKnoweledgeSkills(tempString);
				}
				
				if(scoreCard.getCallSubCategoryIdKnoweledgeSkillsUIObject() != null 
						&& scoreCard.getCallSubCategoryIdKnoweledgeSkillsUIObject().length > 0) {
					String tempString = "";
					for(String singleValue:scoreCard.getCallSubCategoryIdKnoweledgeSkillsUIObject()) {
						tempString += singleValue +",";
					}
					scoreCard.setCallSubCategoryIdKnoweledgeSkills(tempString);
				}
				
				BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
				String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateScoreCard");
				
				
				Date qamStartDateTime = utilityFunctions.convertToDateFromString(scoreCard.getQamStartdateTimeString());
				scoreCard.setQamStartdateTime(qamStartDateTime);
			     
				String qamEnddateTimeString = utilityFunctions.convertToStringFromDate(currentDateTime);
				Date qamEnddateTime = utilityFunctions.convertToDateFromString(qamEnddateTimeString);
				scoreCard.setQamEnddateTimeString(qamEnddateTimeString);
				scoreCard.setQamEnddateTime(qamEnddateTime);
				scoreCard.setUserId(userFormSession.getId().intValue());
				scoreCard.setMacName(HomeController.MAC_ID_MAP.get(scoreCard.getMacId()));
				scoreCard.setJurisdictionName(HomeController.JURISDICTION_MAP.get(scoreCard.getJurId()));
				scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
				ResponseEntity<ScoreCard> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard,ScoreCard.class);
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = "redirect:/"+userFolder+"/scorecardlist/false";
			url = response.encodeRedirectURL(url);
			returnView =  url;
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
				validationResultString+="Non-Scoreable Reason";
			}
    		
    	} else if(scoreCard.getScorecardType().equalsIgnoreCase("Does Not Count")) {
    		
    			if(scoreCard.getFailReasonAdditionalComments().equalsIgnoreCase("")) {
    				validationResultString+="Additional Comments";
    			}    		
    	}
    	
    	if(validationResultString.equalsIgnoreCase("Please Provide Following Fields:")) {
    		validationResultString = "Validation Succesfull";
    	}
    	return validationResultString;
    }
}