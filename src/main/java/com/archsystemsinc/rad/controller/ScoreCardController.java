package com.archsystemsinc.rad.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.impl.SecurityServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the Spring Controller Class for User Login Functionality.
 * 
 * This class provides the functionalities for 1. User Registration,
 * 2. Re-directing to the welcome Page, and 3. The Login Page.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 */

/**
 * This is the Spring Controller Class for Scorecard functionality (create, edit, monitoring scorecard).
 * 
 * This class provides the functionalities for 
 * 1. Create scorecard
 * 2. Edit score card
 * Updated
 * @author Mobeena
 * @since 11/27/2017
 */


@Controller
public class ScoreCardController {
	private static final Logger log = Logger.getLogger(ScoreCardController.class);	
	 
	 SecurityServiceImpl securityServiceImpl; 
	 
 
    /**
     * This method provides the functionalities for getting the scorecard jsp.
     * 
     * @param 
     * @return scorecard
     */
    @RequestMapping(value = "/admin/scorecard", method = RequestMethod.GET)
    public String getScorecard(Model model) {
    	 log.debug("--> showCreateScoreCard Screen <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
    	  return "scorecard";
    }
    
	@RequestMapping(value = "/admin/scorecardlist")
	public String getScorecardList(HttpServletRequest request,Model model) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardNew = new ScoreCard();
		model.addAttribute("scorecard", scoreCardNew);
		String plainCreds = "qamadmin:123456";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
		
		//System.out.println(HomeController.MAC_ID_MAP);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("Authorization", "Basic " + base64Creds);

		headers.set("Content-Length", "35");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> exchange = restTemplate.exchange(HomeController.REST_SERVICE_URI + "findAllScorecard", HttpMethod.GET,
				new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		List<ScoreCard> scoreCardList = null;
		
		try {
			scoreCardList = mapper.readValue(exchange.getBody(), new TypeReference<List<ScoreCard>>(){});
			for(ScoreCard scoreCard: scoreCardList) {
				scoreCard.setJurisdictionName(HomeController.JURISDICTION_MAP.get(scoreCard.getJurId()));
				resultsMap.put(scoreCard.getId(), scoreCard);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		model.addAttribute("ScoreCardFilter",true);
		return "scorecardlist";
	}
	
	@RequestMapping(value = "/admin/scorecardfilter", method = RequestMethod.POST)
	public String filterScorecardList(@ModelAttribute("scorecard") ScoreCard scoreCard, final BindingResult result,
			final Model model) {
		log.debug("--> getScorecardList Screen <--");
		ScoreCard scoreCardNew = new ScoreCard();
		model.addAttribute("scorecard", scoreCard);
		String ROOT_URI="";

		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		List<ScoreCard> resultsMap = new ArrayList<ScoreCard> ();
		HashMap<Integer, ScoreCard> finalResultsMap = new HashMap<Integer, ScoreCard> ();
		
		try {
			
			String pattern = "MM/dd/yyyy hh:mm:ss a";
			
			String fromDateString = scoreCard.getFilterFromDateString()+ " 00:00:01 AM";
			String toDateString = scoreCard.getFilterToDateString()+ " 11:59:59 PM";
			
			SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
	        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
	        sdfAmerica.setTimeZone(tzInAmerica);
		     
			Date fromDateObject = sdfAmerica.parse(fromDateString);
			Date toDateObject = sdfAmerica.parse(toDateString);
			
			scoreCard.setFilterFromDate(fromDateObject);
			scoreCard.setFilterToDate(toDateObject);
			
			ROOT_URI = new String(HomeController.REST_SERVICE_URI + "searchScoreCard");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			List<ScoreCard> scoreCardList = mapper.convertValue(resultsMap, new TypeReference<List<ScoreCard>>() { });
			
			for(ScoreCard scoreCardTemp: scoreCardList) {
				scoreCard.setJurisdictionName(HomeController.JURISDICTION_MAP.get(scoreCard.getJurId()));
				finalResultsMap.put(scoreCardTemp.getId(), scoreCardTemp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("scorecard",scoreCard);
		model.addAttribute("ScoreCardFilter",true);
		//request.getSession().setAttribute("SESSION_SCOPE_SCORECARDS_MAP", resultsMap);
		//request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		
		return "scorecardlist";
	}
	
	@RequestMapping(value = "/admin/edit-scorecard/{id}", method = RequestMethod.GET)
	public String editScoreCardGet(@PathVariable("id") final Integer id, @ModelAttribute("userForm") User userForm,final Model model, HttpSession session) {
		HashMap<Integer,ScoreCard> resultsMap = (HashMap<Integer, ScoreCard>) session.getAttribute("SESSION_SCOPE_SCORECARDS_MAP");
		ScoreCard scoreCard = resultsMap.get(id);
		model.addAttribute("scorecard", scoreCard);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		
		HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
		model.addAttribute("jurisMapEdit", jurisMap);
		
		HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
		model.addAttribute("programMapEdit", programMap);
		
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		model.addAttribute("subCategorylMapEdit", subCategorylMap);
		
		
		return "scorecard";
	}
	
	
	@RequestMapping(value = "/admin/new-scorecard", method = RequestMethod.GET)
	public String newScoreCardGet(@ModelAttribute("userForm") User userForm,final Model model) {
		
		ScoreCard scoreCard = new ScoreCard();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username 
	    
	    	   
		scoreCard.setQamFullName(name);
		String pattern = "MM/dd/yyyy hh:mm:ss a";
		
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        sdfAmerica.setTimeZone(tzInAmerica);
	     
		String qamStartdateTime = sdfAmerica.format(new Date());
		scoreCard.setQamStartdateTime(qamStartdateTime);
		scoreCard.setCallLanguage("English");
		model.addAttribute("scorecard", scoreCard);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		return "scorecard";
	}

	@RequestMapping(value = "/admin/saveorupdatescorecard", method = RequestMethod.POST)
	public String saveScorecard(@ModelAttribute("scorecard") ScoreCard scoreCard, final BindingResult result,
			final Model model) {

		String returnView = "";
		log.debug("--> savescorecard <--");

		
		String validationResult = validateScoreCard(scoreCard);
		
		if(!validationResult.equalsIgnoreCase("Validation Succesfull")) {
			HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(scoreCard.getMacId());
			model.addAttribute("jurisMapEdit", jurisMap);
			
			HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId());
			model.addAttribute("programMapEdit", programMap);
			
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			
			HashMap<Integer,String> subCategorylMap = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
			model.addAttribute("subCategorylMapEdit", subCategorylMap);
			model.addAttribute("scorecard", scoreCard);
			model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
			model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
			model.addAttribute("ValidationFailure", validationResult);
			returnView = "scorecard";
		} else {
			try {
				
				BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
				String ROOT_URI = new String(HomeController.REST_SERVICE_URI + "saveOrUpdateScoreCard");
				
				String pattern = "MM/dd/yyyy hh:mm:ss a";
				
				SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
		        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
		        sdfAmerica.setTimeZone(tzInAmerica);
			     
				String qamEnddateTime = sdfAmerica.format(new Date());
				scoreCard.setQamEnddateTime(qamEnddateTime);
				ResponseEntity<ScoreCard> response = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard,
						ScoreCard.class);
				//System.out.println(response.getBody().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnView = "forward:/admin/scorecardlist";
		}

		return returnView;
	}
    
    private List<HttpMessageConverter<?>> getMessageConverters() {
    	List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
    	converters.add(new MappingJackson2HttpMessageConverter());
    	return converters;
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