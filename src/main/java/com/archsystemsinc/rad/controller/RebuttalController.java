package com.archsystemsinc.rad.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.openqa.selenium.server.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;

import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.model.UserFilter;
import com.archsystemsinc.rad.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class RebuttalController {
	private static final Logger log = Logger.getLogger(RebuttalController.class);
	
	@Autowired
	UtilityFunctions utilityFunctions;
	
	@Autowired
	CommonController commonController;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value ={"/admin/rebuttalfilter", "/quality_manager/rebuttalfilter", "/cms_user/rebuttalfilter",
			 "/mac_admin/rebuttalfilter","/mac_user/rebuttalfilter","/quality_monitor/rebuttalfilter"})		
	public String filterRebuttalList(@ModelAttribute("rebuttal") Rebuttal rebuttal, 
			final BindingResult result,
			final Model model,HttpServletRequest request, Authentication authentication) {
		log.debug("--> filterRebuttalList Screen <--");
		HashMap<Integer, Rebuttal> resultsMap = null;
		try {		
			
			if(!rebuttal.getFilterFromDateString().equalsIgnoreCase("")) {
				String fromDateString = rebuttal.getFilterFromDateString()+ " 00:00:01 AM";
				Date fromDateObject = utilityFunctions.convertToDateFromString(fromDateString);
				rebuttal.setFilterFromDate(fromDateObject);
			}
			if(!rebuttal.getFilterToDateString().equalsIgnoreCase("")) {
				String toDateString = rebuttal.getFilterToDateString()+ " 11:59:59 PM";
				Date toDateObject = utilityFunctions.convertToDateFromString(toDateString);
				rebuttal.setFilterToDate(toDateObject);
			}
			
			String roles = authentication.getAuthorities().toString();
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
				//User userForm = (User) request.getSession().getAttribute("LoggedInUserForm");					
						
				rebuttal.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
				
				
			} 
			
			resultsMap = retrieveRebuttalList(rebuttal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("rebuttal",rebuttal);
		//model.addAttribute("ScoreCardFilter",true);
				
		request.getSession().setAttribute("SESSION_SCOPE_REBUTTAL_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		
		return "rebuttallist";
	}
	
	private HashMap<Integer,Rebuttal> retrieveRebuttalList(Rebuttal rebuttalModelObject) {		
		
		List<ScoreCard> resultsMap = new ArrayList<ScoreCard> ();
		HashMap<Integer, Rebuttal> finalResultsMap = new HashMap<Integer, Rebuttal> ();
		try {
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "rebuttallist");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttalModelObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			List<Rebuttal> rebuttalList = mapper.convertValue(resultsMap, new TypeReference<List<Rebuttal>>() { });
			
			for(Rebuttal rebuttalTemp: rebuttalList) {
				
				finalResultsMap.put(rebuttalTemp.getId(), rebuttalTemp);
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalResultsMap;
	}
	
	
	 @RequestMapping(value ={"/admin/rebuttallist", "/quality_manager/rebuttallist", "/cms_user/rebuttallist",
			 "/mac_admin/rebuttallist","/mac_user/rebuttallist","/quality_monitor/rebuttallist"})		
	public String getRebuttalList(HttpServletRequest request,Model model, Authentication authentication) {
		log.debug("--> getRebuttalList Screen <--");
		
		HashMap<Integer,Rebuttal> resultsMap = new HashMap<Integer,Rebuttal>();
		
		List<Rebuttal> rebuttalTempList = null;
		
		Rebuttal rebuttalObject = new Rebuttal();
		
		try {
			String roles = authentication.getAuthorities().toString();
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
				rebuttalObject.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
				String jurisIdList = HomeController.LOGGED_IN_USER_JURISDICTION_IDS;		
				
				if(jurisIdList != null && !jurisIdList.equalsIgnoreCase("")) {
					ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
					
					String[] jurisIds = jurisIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
					
					for (String jurisIdSingleValue: jurisIds) {
						
						jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
					}
					
					rebuttalObject.setJurisIdList(jurisdictionArrayList);
				}
				
			} 
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "rebuttallist");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttalObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			rebuttalTempList = responseEntity.getBody();
			
			List<Rebuttal> rebuttalList = mapper.convertValue(rebuttalTempList, new TypeReference<List<Rebuttal>>() { });
			
			//rebuttalList = mapper.readValue(exchange.getBody(), new TypeReference<List<Rebuttal>>(){});
			for(Rebuttal rebuttal: rebuttalList) {
				String macPCCNameTempValue = HomeController.PCC_LOC_MAP.get(rebuttal.getPccLocationId());
				rebuttal.setMacPCCNameTempValue(macPCCNameTempValue);
				rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
				resultsMap.put(rebuttal.getId(), rebuttal);
			}
			model.addAttribute("rebuttal",rebuttalObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		request.getSession().setAttribute("SESSION_SCOPE_REBUTTAL_MAP", resultsMap);
		request.getSession().setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "rebuttallist";
	}
	 
	@RequestMapping(value ={"/admin/new-rebuttal", "/quality_manager/new-rebuttal", "/cms_user/new-rebuttal",
			 "/mac_admin/new-rebuttal","/mac_user/new-rebuttal","/quality_monitor/new-rebuttal"}, method = RequestMethod.GET)	
	public String newRebuttalGet(HttpServletRequest request,final Model model, Authentication authentication, HttpSession session) {
		
		Rebuttal rebuttal = new Rebuttal();		
		model.addAttribute("rebuttal", rebuttal);
		List<User> resultsMap = new ArrayList<User> ();
		HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		User userSearchObject = new User();
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
			userSearchObject.setMacId(Long.valueOf(HomeController.LOGGED_IN_USER_MAC_ID));
			
			String[] jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			
			ArrayList<String> jurIdArrayList = new ArrayList<String>();
			for (String jurisIdSingleValue: jurisIds) {
				
				jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			}
			userSearchObject.setJurIdList(jurIdArrayList);
		}
		
		HashMap<Integer,String> failedMacRefList = setMacRefInSession(request, authentication);		
		
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
		
		ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
		ObjectMapper mapper = new ObjectMapper();
		resultsMap = responseEntity.getBody();
		List<User> userList = mapper.convertValue(resultsMap, new TypeReference<List<User>>() { });
		
		for(User userTemp: userList) {
			pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
		}
		
		failedMacRefList = utilityFunctions.sortByValues(failedMacRefList);
		model.addAttribute("pccContactPersonMap",pccContactPersonMap);
		model.addAttribute("macReferenceFailedList",failedMacRefList);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		
		return "rebuttal";
	}
	
	private HashMap<Integer,String> setMacRefInSession( HttpServletRequest request, Authentication authentication) {
		
		
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
		
		HashMap<Integer,String> failedMacRefList = new HashMap<Integer,String>();
		
		Rebuttal rebuttal = new Rebuttal();
		
		ScoreCard scoreCardTemp = new ScoreCard();
		List<ScoreCard> resultsMapTemp = null;
		List<ScoreCard> failedScorecardList = null;
		
		try {
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "retrieveMacCallRefFailList");
			
			String roles = authentication.getAuthorities().toString();
			
			if(roles.contains("MAC Admin") || roles.contains("MAC User")) {
				
				scoreCardTemp.setMacId(HomeController.LOGGED_IN_USER_MAC_ID);
				
				String[] jurisIds = HomeController.LOGGED_IN_USER_JURISDICTION_IDS.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
				
				ArrayList<Integer> jurIdArrayList = new ArrayList<Integer>();
				for (String jurisIdSingleValue: jurisIds) {
					
					jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));
				}				
				scoreCardTemp.setJurIdList(jurIdArrayList);
			} 
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCardTemp, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMapTemp = responseEntity.getBody();
			failedScorecardList = mapper.convertValue(resultsMapTemp, new TypeReference<List<ScoreCard>>() { });
			
			for(ScoreCard scoreCard: failedScorecardList) {
				resultsMap.put(scoreCard.getId(), scoreCard);
				failedMacRefList.put(scoreCard.getId(), scoreCard.getMacCallReferenceNumber());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		request.getSession().setAttribute("MAC_REF_FAILED_LIST", failedMacRefList);
		
		request.getSession().setAttribute("MAC_REF_FAILED_MAP", resultsMap);
		return failedMacRefList;
	}
	
	@RequestMapping(value ={"/admin/selectScoreCardFromMacRef", "/quality_manager/selectScoreCardFromMacRef", "/cms_user/selectScoreCardFromMacRef",
			 "/mac_admin/selectScoreCardFromMacRef","/mac_user/selectScoreCardFromMacRef","/quality_monitor/selectScoreCardFromMacRef"}, method = RequestMethod.GET)	
	@ResponseBody
	public ScoreCard selectScoreCardFromMacRef(@RequestParam("scoreCardId") final Integer scoreCardId,final Model model, HttpSession session) {
		
		HashMap<Integer,ScoreCard> resultsMap =  (HashMap<Integer, ScoreCard>) session.getAttribute("MAC_REF_FAILED_MAP");
		ScoreCard scoreCard = resultsMap.get(scoreCardId);
		
		String callCategoryName = HomeController.CALL_CATEGORY_MAP.get(scoreCard.getCallCategoryId());
		
		HashMap<Integer,String> pccLocationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(scoreCard.getMacId()+"_"+scoreCard.getJurId()+"_"+scoreCard.getProgramId());
		
		scoreCard.setPccLocationMap(pccLocationMap);
		
		scoreCard.setCallCategoryName(callCategoryName);
		
		return scoreCard;
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateRebuttal", "/quality_manager/saveOrUpdateRebuttal", "/cms_user/saveOrUpdateRebuttal",
			 "/mac_admin/saveOrUpdateRebuttal","/mac_user/saveOrUpdateRebuttal","/quality_monitor/saveOrUpdateRebuttal"}, method = RequestMethod.POST)	
	public String saveRebuttal(@ModelAttribute("rebuttal") Rebuttal rebuttal, final BindingResult result,
			final Model model, HttpSession session) {

		String returnView = "";
		log.debug("--> saverebuttal <--");

		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateRebuttal");
		
		String pattern = "MM/dd/yyyy hh:mm:ss a";
		
		//Nulling the File Value
		rebuttal.setRebuttalFileObject(null);
		
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
       TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
       sdfAmerica.setTimeZone(tzInAmerica);
       String currentDateString = sdfAmerica.format(new Date());
       
       if(rebuttal.getId()==0) {
   		rebuttal.setDatePosted(new Date() );
   		rebuttal.setCreatedDate(currentDateString);
       } else {
       	rebuttal.setUpdatedDate(currentDateString);
       }
       
		if(rebuttal.getRebuttalCompleteFlag()==null) {
			rebuttal.setRebuttalStatus("Pending");
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("Yes")) {
			rebuttal.setRebuttalStatus("Completed");
			
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("No")) {
			rebuttal.setRebuttalStatus("Pending");
			rebuttal.setRebuttalResult("Pending");
		} 
		
		rebuttal.setDescriptionComments(rebuttal.getDescriptionComments()+"\n"+rebuttal.getDescriptionCommentsAppend());
		
		 
		User user =  (User) session.getAttribute("LoggedInUserForm");
		rebuttal.setUserId(user.getId().intValue());
		try {
			rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
			rebuttal.setJurisName(HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId()));
			ResponseEntity<Rebuttal> response = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
					Rebuttal.class);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		returnView = "forward:/admin/rebuttallist";

		return returnView;
	}
	
	/*@RequestMapping(value ={"/admin/saveOrUpdateRebuttal", "/quality_manager/saveOrUpdateRebuttal", "/cms_user/saveOrUpdateRebuttal",
			 "/mac_admin/saveOrUpdateRebuttal","/mac_user/saveOrUpdateRebuttal","/quality_monitor/saveOrUpdateRebuttal"}, method = RequestMethod.POST)	
	public String saveRebuttal(@ModelAttribute("rebuttal") Rebuttal rebuttal, BindingResult result, ModelMap model, HttpSession session) {

		String returnView = "";
		log.debug("--> saverebuttal <--");

		try {
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateRebuttal");
			
			String pattern = "MM/dd/yyyy hh:mm:ss a";
			
			SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
			TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
			sdfAmerica.setTimeZone(tzInAmerica);
			String currentDateString = sdfAmerica.format(new Date());
			if(rebuttal.getId()==0) {
				rebuttal.setDatePosted(currentDateString);
				rebuttal.setCreatedDate(currentDateString);
			} else {
				rebuttal.setUpdatedDate(currentDateString);
			}
			
			if(rebuttal.getRebuttalCompleteFlag()==null) {
				rebuttal.setRebuttalStatus("Pending");
			} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("Yes")) {
				rebuttal.setRebuttalStatus("Completed");
				
			} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("No")) {
				rebuttal.setRebuttalStatus("Pending");
				rebuttal.setRebuttalResult("Pending");
			} 
			
			if(rebuttal.getDescriptionComments() != null) {
				rebuttal.setDescriptionComments(rebuttal.getDescriptionComments()+"\n"+rebuttal.getDescriptionCommentsAppend());
			} else {
				rebuttal.setDescriptionComments(rebuttal.getDescriptionCommentsAppend());
			}
			
			MultipartFile multipartFile = rebuttal.getRebuttalFileObject();
			
			File tempFile = null;
			String extension = ".xlsx";
			
			tempFile = File.createTempFile("temp", extension);
			multipartFile.transferTo(tempFile);
			
			rebuttal.setFileName(multipartFile.getOriginalFilename());
			rebuttal.setFileType(multipartFile.getContentType());
			rebuttal.setRebuttalFileAttachment(multipartFile.getBytes());
			
			 String plainCreds = "qamadmin:123456";
				byte[] plainCredsBytes = plainCreds.getBytes();
				byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
				String base64Creds = new String(base64CredsBytes);
				
				HttpHeaders headers = new HttpHeaders();
				
				headers.add("Authorization", "Basic " + base64Creds);
				headers.setContentType(MediaType.MULTIPART_FORM_DATA);

				RestTemplate restTemplate = new RestTemplate();
			
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("file", tempFile);
			//HttpHeaders headers = new HttpHeaders();
			
			User user =  (User) session.getAttribute("LoggedInUserForm");
			rebuttal.setUserId(user.getId().intValue());
			

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
			                    map, headers);
			ResponseEntity<String> response = restTemplate.exchange(
							ROOT_URI, HttpMethod.POST, requestEntity,
							String.class);
			
			ResponseEntity<Rebuttal> response = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
					Rebuttal.class);
			
			
			
			LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		    map.add("file", new FileSystemResource(tempFile));
			
			rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
			rebuttal.setJurisName(HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId()));
			ResponseEntity<Rebuttal> response = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
					Rebuttal.class);
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		returnView = "forward:/admin/rebuttallist";

		return returnView;
	}
	*/
	
	/*public DocumentDetailed uploadDocumentInIfs(MultipartFile file, String userProfile) {
	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(backendURL + "documents/upload");
	    builder.queryParam("user", userProfile);
	    URI uri = builder.build().encode().toUri();

	    File tempFile = null;
	    try {
	        String extension = "." + getFileExtention(file.getOriginalFilename());
	        tempFile = File.createTempFile("temp", extension);
	        file.transferTo(tempFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	    map.add("file", new FileSystemResource(tempFile));
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

	    Document document = null;
	    try {
	        ResponseEntity<Document> responseEntity =
	                restTemplate.exchange(uri, HttpMethod.POST, requestEntity, Document.class);
	        document = responseEntity.getBody();
	    } catch (Exception e) {
	        e.getMessage();
	    }

	    return document;
	}
	*/
	@RequestMapping(value ={"/admin/edit-rebuttal/{id}", "/quality_manager/edit-rebuttal/{id}", "/cms_user/edit-rebuttal/{id}",
			 "/mac_admin/edit-rebuttal/{id}","/mac_user/edit-rebuttal/{id}","/quality_monitor/edit-rebuttal/{id}"}, method = RequestMethod.GET)		
	public String editRebuttalGet(@PathVariable("id") final Integer id, @ModelAttribute("userForm") User userForm,final Model model, HttpSession session,HttpServletRequest request) {
		HashMap<Integer,Rebuttal> resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTAL_MAP");
		Rebuttal rebuttal = resultsMap.get(id);
		
		String datePostedString = utilityFunctions.convertToStringFromDate(rebuttal.getDatePosted());
		
		if(rebuttal.getRebuttalStatus() == null) {
			rebuttal.setRebuttalCompleteFlag("");
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Completed")) {
			rebuttal.setRebuttalCompleteFlag("Yes");			
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Pending")) {
			rebuttal.setRebuttalCompleteFlag("No");
		} 
		model.addAttribute("rebuttal", rebuttal);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		HashMap<Integer,String> pccLocationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(rebuttal.getMacId()+"_"+rebuttal.getJurisId()+"_"+rebuttal.getProgramId());
		
		model.addAttribute("programMapEdit", pccLocationMap);
		return "rebuttal";
	}	
	
	@RequestMapping(value ={"/admin/view-rebuttal/{id}", "/quality_manager/view-rebuttal/{id}", "/cms_user/view-rebuttal/{id}",
			 "/mac_admin/view-rebuttal/{id}","/mac_user/view-rebuttal/{id}","/quality_monitor/view-rebuttal/{id}"}, method = RequestMethod.GET)		
	public String viewRebuttalGet(@PathVariable("id") final Integer id, @ModelAttribute("userForm") User userForm,final Model model, HttpSession session,HttpServletRequest request) {
		
		HashMap<Integer,Rebuttal> resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTALS_REPORT_MAP");
		
		if (resultsMap == null ) {
			resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTAL_MAP");
		}
		
		Rebuttal rebuttal = null;
		if (resultsMap != null) {
			rebuttal = resultsMap.get(id);
		}
		
		
		if(rebuttal.getRebuttalStatus() == null) {
			rebuttal.setRebuttalCompleteFlag("");
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Completed")) {
			rebuttal.setRebuttalCompleteFlag("Yes");			
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Pending")) {
			rebuttal.setRebuttalCompleteFlag("No");
		} 
		model.addAttribute("rebuttal", rebuttal);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		HashMap<Integer,String> pccLocationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(rebuttal.getMacId()+"_"+rebuttal.getJurisId()+"_"+rebuttal.getProgramId());
		
		model.addAttribute("programMapEdit", pccLocationMap);
		return "rebuttalview";
	}	
}