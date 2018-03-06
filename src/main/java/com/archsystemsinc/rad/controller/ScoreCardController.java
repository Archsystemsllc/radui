package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

	 
	 @RequestMapping(value ={"/admin/scorecardlist", "/quality_manager/scorecardlist", "/cms_user/scorecardlist",
			 "/mac_admin/scorecardlist","/mac_user/scorecardlist","/quality_monitor/scorecardlist"})	
	public String getScorecardList(HttpServletRequest request,Model model, Authentication authentication, HttpSession session) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardNew = new ScoreCard();
		
		String roles = authentication.getAuthorities().toString();
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		
		try {
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
				
				scoreCardNew.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
				scoreCardNew.setJurisIdReportSearchString(HomeController.LOGGED_IN_USER_JURISDICTION_IDS);				
				
				model.addAttribute("macMapEdit", HomeController.LOGGED_IN_USER_MAC_MAP);		
				model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);			
			} else {
				model.addAttribute("macMapEdit", HomeController.MAC_ID_MAP);			
			}
			
			if(roles.contains("Quality Monitor")) {
				scoreCardNew.setUserId(userForm.getId().intValue());
			}
			
			HashMap<Integer, ScoreCard> resultsMap = retrieveScoreCardList(scoreCardNew);
			ObjectMapper mapper = new ObjectMapper();
			//Convert the result set to string and replace single character with spaces
			model.addAttribute("scoreCardList",mapper.writeValueAsString(resultsMap.values()).replaceAll("'", " "));
			
			model.addAttribute("scorecard", scoreCardNew);
			model.addAttribute("ScoreCardFilter",true);
			
			model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
			
			request.getSession().setAttribute("SESSION_SCOPE_SCORECARD_FILTER", scoreCardNew);
			request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
			request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "scorecardlist";
	}
	 
	 
	
	 @RequestMapping(value ={"/admin/scorecardfilter", "/quality_manager/scorecardfilter", "/cms_user/scorecardfilter",
			 "/mac_admin/scorecardfilter","/mac_user/scorecardfilter","/quality_monitor/scorecardfilter"})		
	public String filterScorecardList(@ModelAttribute("scorecard") ScoreCard scoreCard, 
			final BindingResult result,
			final Model model,HttpServletRequest request, Authentication authentication) {
		log.debug("--> getScorecardList Screen <--");
		HashMap<Integer, ScoreCard> resultsMap = null;
		try {		
			
			if(!scoreCard.getFilterFromDateString().equalsIgnoreCase("")) {
				String fromDateString = scoreCard.getFilterFromDateString()+ " 00:00:01 AM";
				Date fromDateObject = utilityFunctions.convertToDateFromString(fromDateString);
				scoreCard.setFilterFromDate(fromDateObject);
			}
			if(!scoreCard.getFilterToDateString().equalsIgnoreCase("")) {
				String toDateString = scoreCard.getFilterToDateString()+ " 11:59:59 PM";
				Date toDateObject = utilityFunctions.convertToDateFromString(toDateString);
				scoreCard.setFilterToDate(toDateObject);
			}
			
			String roles = authentication.getAuthorities().toString();
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
				
				scoreCard.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
				//scoreCard.setJurId(HomeController.LOGGED_IN_USER_JURISDICTION_IDS);				
				
				model.addAttribute("macMapEdit", HomeController.LOGGED_IN_USER_MAC_MAP);		
				model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);			
			} else {
				model.addAttribute("macMapEdit", HomeController.MAC_ID_MAP);
				HashMap<Integer,String> jurisMap = null;
				if(scoreCard.getMacId()==0) {
					jurisMap = HomeController.JURISDICTION_MAP;
				} else {
					jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
				}
				
				model.addAttribute("jurisMapEdit", jurisMap);
			}
			
			resultsMap = retrieveScoreCardList(scoreCard);
			ObjectMapper mapper = new ObjectMapper();
	        model.addAttribute("scoreCardList", mapper.writeValueAsString(resultsMap.values()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("scorecard",scoreCard);
		model.addAttribute("ScoreCardFilter",true);    
		
		request.getSession().setAttribute("SESSION_SCOPE_SCORECARD_FILTER", scoreCard);
				
		request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		
		return "scorecardlist";
	}
	 
	 @RequestMapping(value ={"/admin/gobackscorecardfilter", "/quality_manager/gobackscorecardfilter", "/cms_user/gobackscorecardfilter",
			 "/mac_admin/gobackscorecardfilter","/mac_user/gobackscorecardfilter","/quality_monitor/gobackscorecardfilter"}, method = RequestMethod.GET)		
	public String goBackScoreCardFilter(
			final Model model,HttpServletRequest request, Authentication authentication) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardFromSession = (ScoreCard) request.getSession().getAttribute("SESSION_SCOPE_SCORECARD_FILTER");
		HashMap<Integer, ScoreCard> resultsMap = null;
		try {		
			
			String roles = authentication.getAuthorities().toString();
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
							
				model.addAttribute("macMapEdit", HomeController.LOGGED_IN_USER_MAC_MAP);		
				model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);		
			} else {
				model.addAttribute("macMapEdit", HomeController.MAC_ID_MAP);
				HashMap<Integer,String> jurisMap = null;
				if(scoreCardFromSession.getMacId() == null || scoreCardFromSession.getMacId()==0) {
					jurisMap = HomeController.JURISDICTION_MAP;
				} else {
					jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCardFromSession.getMacId());
				}
				
				model.addAttribute("jurisMapEdit", jurisMap);
			}
			
			resultsMap = retrieveScoreCardList(scoreCardFromSession);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("scorecard",scoreCardFromSession);
		model.addAttribute("ScoreCardFilter",true);
		
		request.getSession().setAttribute("SESSION_SCOPE_SCORECARD_FILTER", scoreCardFromSession);
				
		request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		
		return "scorecardlist";
	}
	
	private HashMap<Integer,ScoreCard> retrieveScoreCardList(ScoreCard scoreCardModelObject) {	
		log.debug("--> Enter retrieveScoreCardList <--");
		
		List<ScoreCard> resultsMap = new ArrayList<ScoreCard> ();
		HashMap<Integer, ScoreCard> finalResultsMap = new HashMap<Integer, ScoreCard> ();
		try {
		
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchScoreCard");
			
			if(scoreCardModelObject.getJurisIdReportSearchString() != null && !scoreCardModelObject.getJurisIdReportSearchString().equalsIgnoreCase("")) {
				ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
				
				String[] jurisIds = scoreCardModelObject.getJurisIdReportSearchString().split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
				
				for (String jurisIdSingleValue: jurisIds) {
					
					jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
				}
				
				scoreCardModelObject.setJurIdList(jurisdictionArrayList);
			}
			
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCardModelObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap, new TypeReference<List<ScoreCard>>() { });
			
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
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime());
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime());
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		
		model.addAttribute("scorecard", scoreCard);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			
			scoreCard.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
			scoreCard.setJurisIdReportSearchString(HomeController.LOGGED_IN_USER_JURISDICTION_IDS);				
			
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
		
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategorylMapEdit", subCategorylMap);		
		
		return "scorecard";
	}
	 
	 @RequestMapping(value ={"/admin/view-scorecard/{id}", "/quality_manager/view-scorecard/{id}", "/cms_user/view-scorecard/{id}",
			 "/mac_admin/view-scorecard/{id}","/mac_user/view-scorecard/{id}","/quality_monitor/view-scorecard/{id}"}, method = RequestMethod.GET)
	public String viewScoreCardGet(@PathVariable("id") final Integer id, final Model model,Authentication authentication,
			HttpSession session) {
		
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		User userForm = (User) session.getAttribute("LoggedInUserForm");
		ScoreCard scoreCard = resultsMap.get(id);
		
		String qamStartDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamStartdateTime());
		String qamEndDateString = utilityFunctions.convertToStringFromDate(scoreCard.getQamEnddateTime());
		
		scoreCard.setQamStartdateTimeString(qamStartDateString);
		scoreCard.setQamEnddateTimeString(qamEndDateString);
		
		model.addAttribute("scorecard", scoreCard);
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
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
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		model.addAttribute("loggedInUserRole", userForm.getRole().getRoleName());
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategorylMapEdit", subCategorylMap);
		
		
		return "scorecardview";
	}
	
	
	 @RequestMapping(value ={"/admin/new-scorecard", "/quality_manager/new-scorecard", "/cms_user/new-scorecard",
			 "/mac_admin/new-scorecard","/mac_user/new-scorecard","/quality_monitor/new-scorecard"}, method = RequestMethod.GET)
	
	public String newScoreCardGet(@ModelAttribute("userForm") User userForm,final Model model,Authentication authentication,
			HttpSession session) {
		
		ScoreCard scoreCard = new ScoreCard();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username 
		scoreCard.setQamFullName(name);
		
		String qamStartdateTime = utilityFunctions.convertToStringFromDate(new Date());
		
		scoreCard.setQamStartdateTimeString(qamStartdateTime);
		scoreCard.setCallLanguage("English");
		model.addAttribute("scorecard", scoreCard);
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			model.addAttribute("macIdMap", HomeController.LOGGED_IN_USER_MAC_MAP);		
			model.addAttribute("jurisMapEdit", HomeController.LOGGED_IN_USER_JURISDICTION_MAP);	
			
		} else {
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		}
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		return "scorecard";
	}

	
	@RequestMapping(value ={"/admin/saveorupdatescorecard", "/quality_manager/saveorupdatescorecard", "/cms_user/saveorupdatescorecard",
			 "/mac_admin/saveorupdatescorecard","/mac_user/saveorupdatescorecard","/quality_monitor/saveorupdatescorecard"}, method = RequestMethod.POST)
	public String saveScorecard(@ModelAttribute("scorecard") ScoreCard scoreCard, final BindingResult result,
			final Model model, Authentication authentication, HttpSession session) {

		String returnView = "";
		log.debug("--> savescorecard <--");
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		
		String validationResult = validateScoreCard(scoreCard);
		
		String loggedInUserRole = userFormSession.getRole().getRoleName();
		
		if(!validationResult.equalsIgnoreCase("Validation Succesfull")) {
			
			
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
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			
			HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
			model.addAttribute("subCategorylMapEdit", subCategorylMap);
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
				
				if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.ADMIN_ROLE_STRING) && 
						existingScoreCard != null && !existingScoreCard.getCmsCalibrationStatus().equalsIgnoreCase(scoreCard.getCmsCalibrationStatus()) ) {
					scoreCard.setCmsCalibrationUpdateDateTime(currentDateTime);
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.QUALITY_MANAGER_ROLE_STRING) && 
						existingScoreCard != null && !existingScoreCard.getQamCalibrationStatus().equalsIgnoreCase(scoreCard.getQamCalibrationStatus()) ) {
					scoreCard.setQamCalibrationUpdateDateTime(currentDateTime);
					
				}  else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.QUALITY_MONITOR_ROLE_STRING) && 
						(existingScoreCard != null && !existingScoreCard.getCallResult().equalsIgnoreCase(scoreCard.getCallResult()) || existingScoreCard == null)  ){
					scoreCard.setScoreCardStatusUpdateDateTime(currentDateTime);
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.CMS_USER_ROLE_STRING)) {
					
					
				} else if (loggedInUserRole.equalsIgnoreCase(UIGenericConstants.MAC_ADMIN_ROLE_STRING)) {
					
					
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
				ResponseEntity<ScoreCard> response = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard,ScoreCard.class);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnView = "forward:/admin/scorecardlist";
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