/*package com.archsystemsinc.rad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.ReportsForm;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ReportsController {
	private static final Logger log = Logger.getLogger(ReportsController.class);

	*//**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 *//*

	@RequestMapping(value = "/admin/reports")
	public String viewCSRList(Model model, HttpSession session) {
		log.debug("--> showAdminDashboard <--");
		
		ReportsForm reportsForm = new ReportsForm();
		model.addAttribute("reportsForm", reportsForm);
		model.addAttribute("macIdMap", HomeController.MAC_ID_MAP);
		session.setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		return "reports";
	}
	
	@RequestMapping(value = "/admin/scorecardlist")
	public String getScorecardList(HttpServletRequest request,Model model) {
		log.debug("--> getScorecardList Screen <--");
		User form = new User();
		model.addAttribute("userForm", form);
		String plainCreds = "qamadmin:123456";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HashMap<Integer,ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
		
		System.out.println(HomeController.MAC_ID_MAP);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("Authorization", "Basic " + base64Creds);

		headers.set("Content-Length", "35");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> exchange = restTemplate.exchange(HomeController.REST_SERVICE_URI + "scorecardlist", HttpMethod.GET,
				new HttpEntity<String>(headers), String.class);
		ObjectMapper mapper = new ObjectMapper();
		List<ScoreCard> scoreCardList = null;
		
		try {
			scoreCardList = mapper.readValue(exchange.getBody(), new TypeReference<List<ScoreCard>>(){});
			for(ScoreCard scoreCard: scoreCardList) {
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
		request.getSession().setAttribute("SCORECARDS_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		
		return "scorecardlist";
	}
	
	@RequestMapping(value = "/admin/getMacJurisReport", method = RequestMethod.POST)
	public String getMacJurisReport(@ModelAttribute("reportsForm") ReportsForm reportsForm, HttpServletRequest request, final BindingResult result,
			final Model model) {

		String returnView = "";
		log.debug("--> getMacJurisReport <--");
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer, ScoreCard> ();
		//BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.REST_SERVICE_URI + "getMacJurisReport");
		
		HashMap<String,QamMacByJurisdictionReviewReport> finalResultsMap = new HashMap<String,QamMacByJurisdictionReviewReport> ();
			
		try {
			String plainCreds = "qamadmin:123456";
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);
			
			HttpHeaders headers = new HttpHeaders();
			//headers.set("Accept", "application/json");
			headers.add("Authorization", "Basic " + base64Creds);

			headers.set("Content-Length", "35");
			RestTemplate restTemplate = new RestTemplate();
						
			ParameterizedTypeReference<HashMap<String,ScoreCard>> typeRef = new ParameterizedTypeReference<HashMap<String,ScoreCard>>() {	};
			ResponseEntity<HashMap<String,ScoreCard>> responseEntity = restTemplate.exchange(ROOT_URI, HttpMethod.POST, new HttpEntity<String>(headers), typeRef);
					//restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(mvm), typeRef);
			//List<MyModelClass> myModelClasses = responseEntity.getBody();
			
			HashMap<String,ScoreCard> myMapObject = responseEntity.getBody();
			
			ResponseEntity<String> exchange = restTemplate.exchange(HomeController.REST_SERVICE_URI + "scorecardlist", HttpMethod.GET,
							new HttpEntity<String>(headers), String.class);
			
			
			//resultsMap =response.getBody();
			for(ScoreCard scoreCard: resultsMap.values()) {
				MacInfo macInfo = HomeController.MAC_OBJECT_MAP.get(scoreCard.getMacId());
				String macNameTemp = macInfo.getMacName();
				String jurisdictionTemp = HomeController.JURISDICTION_MAP.get(scoreCard.getJurId());
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macNameTemp+"_"+jurisdictionTemp);
				String scoreCardType = scoreCard.getScorecardType();
				
				if(qamMacByJurisdictionReviewReport == null) {
					qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
					qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionTemp);
					qamMacByJurisdictionReviewReport.setMacName(macNameTemp);
					
					if(scoreCardType.equalsIgnoreCase("Scorable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(1);
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
							qamMacByJurisdictionReviewReport.setScorableFail(1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scorable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(1);						
					}
					
					
				} else {
					if(scoreCardType.equalsIgnoreCase("Scorable")) {
						qamMacByJurisdictionReviewReport.setScorableCount(qamMacByJurisdictionReviewReport.getScorableCount()+1);
						if(scoreCard.getCallResult().equalsIgnoreCase("Pass")) {
							qamMacByJurisdictionReviewReport.setScorablePass(qamMacByJurisdictionReviewReport.getScorablePass()+1);
						} else if(scoreCard.getCallResult().equalsIgnoreCase("Fail")) {
							qamMacByJurisdictionReviewReport.setScorableFail(qamMacByJurisdictionReviewReport.getScorableFail()+1);
						}
					} else if(scoreCardType.equalsIgnoreCase("Non-Scorable")) {
						qamMacByJurisdictionReviewReport.setNonScorableCount(qamMacByJurisdictionReviewReport.getNonScorableCount()+1);	
					}
				}
				finalResultsMap.put(macNameTemp+"_"+jurisdictionTemp, qamMacByJurisdictionReviewReport);
			}
			
			for(String macJurisKey: finalResultsMap.keySet()) {
				
				QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = finalResultsMap.get(macJurisKey);
				Float scPassPercent = (float) (qamMacByJurisdictionReviewReport.getScorablePass()/qamMacByJurisdictionReviewReport.getScorableCount());
				Float scFailPercent = (float) (qamMacByJurisdictionReviewReport.getScorableFail()/qamMacByJurisdictionReviewReport.getScorableCount());
				Float scNsPercent = (float) (qamMacByJurisdictionReviewReport.getNonScorableCount()/(qamMacByJurisdictionReviewReport.getScorableCount()+qamMacByJurisdictionReviewReport.getNonScorableCount()));
				qamMacByJurisdictionReviewReport.setScorablePassPercent(scPassPercent);			
				qamMacByJurisdictionReviewReport.setScorableFailPercent(scFailPercent);
				qamMacByJurisdictionReviewReport.setNonScorablePercent(scNsPercent);		
				finalResultsMap.put(macJurisKey, qamMacByJurisdictionReviewReport);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("MAC_JURIS_REPORT",finalResultsMap);
		model.addAttribute("reportsForm", reportsForm);
		return "macjurisreviewreports";
	}
}*/