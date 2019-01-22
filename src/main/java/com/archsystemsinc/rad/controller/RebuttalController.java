package com.archsystemsinc.rad.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
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
	
		
	@RequestMapping(value ={"/admin/rebuttallist/{sessionBack}", "/quality_manager/rebuttallist/{sessionBack}", "/cms_user/rebuttallist/{sessionBack}",
			 "/mac_admin/rebuttallist/{sessionBack}","/mac_user/rebuttallist/{sessionBack}","/quality_monitor/rebuttallist/{sessionBack}"})		
	public String getRebuttalList(@ModelAttribute("rebuttal") Rebuttal rebuttalFromModel, final BindingResult result,
			final Model model,HttpServletRequest request, Authentication authentication,@PathVariable("sessionBack") final String sessionBackObject,HttpServletResponse response, HttpSession session) {
		log.debug("--> getRebuttalList Screen <--");
		
		HashMap<Integer,Rebuttal> resultsMap = new HashMap<Integer,Rebuttal>();
		
		List<Rebuttal> rebuttalTempList = null;
		
		Rebuttal rebuttalNew = new Rebuttal();
		String objectType = "";
		
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Date today = new Date();			
		
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			Rebuttal rebuttalFromSession = (Rebuttal) request.getSession().getAttribute("SESSION_SCOPE_REBUTTAL_FILTER");
			
			String roles = authentication.getAuthorities().toString();
			if (rebuttalFromModel.getMacId() != null && rebuttalFromModel.getJurisIdReportSearchString() !=null ){
				rebuttalNew = rebuttalFromModel;	
				objectType = "Model";
			} else if(rebuttalFromSession != null && sessionBackObject.equalsIgnoreCase("true")) {
				//Back Button is Clicked				
				rebuttalNew = rebuttalFromSession;
				objectType = "Session";
			} else {
				//ScoreCard Menu Item Is Clicked
				rebuttalNew = new Rebuttal();
				String[] tempValues = {UIGenericConstants.ALL_STRING};
				rebuttalNew.setJurisIdReportSearchString(tempValues);
				objectType = "New";
				
				//Restricting From Date to 6 months from Current Date
				Calendar fromDateCalendar = Calendar.getInstance();
				fromDateCalendar.setTime(today);
				
				fromDateCalendar.add(Calendar.MONTH, -6);
				String fromDate = mdyFormat.format(fromDateCalendar.getTime());				
				
				rebuttalNew.setFilterFromDateString(fromDate);
				
				if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
					
					// Restricting Mac User and Mac Admin to only see data until 15th of the Month, based on the day
					Calendar toDateCalendar = Calendar.getInstance();
					Integer dayOfMonth = toDateCalendar.get(Calendar.DAY_OF_MONTH);
									
					if(dayOfMonth < 15) {					
						toDateCalendar.add(Calendar.MONTH, -1);					
					} 
					
					toDateCalendar.set(Calendar.DATE, 15);		
					String toDate = mdyFormat.format(toDateCalendar.getTime());
					
					rebuttalNew.setFilterToDateString(toDate);
					
					
				} else {
					// Restricting Mac User and Mac Admin to only see data until 15th of the Month, based on the day
					Calendar toDateCalendar = Calendar.getInstance();
					toDateCalendar.setTime(today);
					String toDate = mdyFormat.format(toDateCalendar.getTime());
					
					rebuttalNew.setFilterToDateString(toDate);
					
				}
			}
			
			
			
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				
				rebuttalNew.setMacId(loggedInUserMacId);
				String loggedInJurisdictionIdList = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");		
				
				if(loggedInJurisdictionIdList != null && !loggedInJurisdictionIdList.equalsIgnoreCase("")  && objectType == "New") {
					ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
					
					String[] jurisIds = loggedInJurisdictionIdList.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
					
					for (String jurisIdSingleValue: jurisIds) {
						
						jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
					}
					
					rebuttalNew.setJurisIdList(jurisdictionArrayList);
					
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
					
				}				
				
				rebuttalNew.setMacId(loggedInUserMacId);
								
				model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
				model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );	
			} else {
				model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);		
				model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);	
				model.addAttribute("programMapEdit", HomeController.ALL_PROGRAM_MAP);	
			}
			
			if(rebuttalNew.getJurisIdReportSearchString() != null && rebuttalNew.getJurisIdReportSearchString().length > 0 && rebuttalNew.getJurisIdList() == null) {
				ArrayList<Integer> jurisdictionArrayList = new ArrayList<Integer>();
				
				String[] jurisIds = rebuttalNew.getJurisIdReportSearchString();
				
				for (String jurisIdSingleValue: jurisIds) {
					if(jurisIdSingleValue.equalsIgnoreCase(UIGenericConstants.ALL_STRING)) {
						break;
					}
					jurisdictionArrayList.add(Integer.valueOf(jurisIdSingleValue));
				}
				
				rebuttalNew.setJurisIdList(jurisdictionArrayList);
			}
			
			
			
			if(rebuttalNew.getFilterFromDateString() != null && 
					!rebuttalNew.getFilterFromDateString().equalsIgnoreCase("")) {
				String filterFromDateString = rebuttalNew.getFilterFromDateString() + " 00:00:00 AM";
				Date filterFromDate = utilityFunctions.convertToDateFromString(filterFromDateString);
				rebuttalNew.setFilterFromDate(filterFromDate);
				
				
			}
			
			
			if(rebuttalNew.getFilterToDateString() != null && 
					!rebuttalNew.getFilterToDateString().equalsIgnoreCase("")) {
				String filterFromDateString = rebuttalNew.getFilterToDateString() + " 11:59:59 PM";
				Date filterToDate = utilityFunctions.convertToDateFromString(filterFromDateString);
				rebuttalNew.setFilterToDate(filterToDate);
				
				
			}
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "rebuttallist");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttalNew, List.class);
			ObjectMapper mapper = new ObjectMapper();
			rebuttalTempList = responseEntity.getBody();
			
			List<Rebuttal> rebuttalList = mapper.convertValue(rebuttalTempList, new TypeReference<List<Rebuttal>>() { });
			
			UtilityFunctions utilityFunctions = new UtilityFunctions();
			List<Rebuttal> rebuttalListTemp = new ArrayList<Rebuttal>();
			//rebuttalList = mapper.readValue(exchange.getBody(), new TypeReference<List<Rebuttal>>(){});
			for(Rebuttal rebuttal: rebuttalList) {
				String macPCCNameTempValue = HomeController.PCC_LOC_MAP.get(rebuttal.getPccLocationId());
				rebuttal.setMacPCCNameTempValue(macPCCNameTempValue);
				rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
				
				if(rebuttal.getDatePosted() != null) {
					rebuttal.setDatePostedString(
							utilityFunctions.convertToStringFromDate(rebuttal.getDatePosted()));
				}
				rebuttal.setDescriptionComments(rebuttal.getDescriptionComments().replaceAll("\n","::"));
				rebuttal.setDescriptionComments(rebuttal.getDescriptionComments().replaceAll("<br/>","::"));
				//rebuttal.setDescriptionComments("");
				resultsMap.put(rebuttal.getId(), rebuttal);
				rebuttalListTemp.add(rebuttal);
			}
			model.addAttribute("rebuttal",rebuttalNew);
			
			request.getSession().setAttribute("SESSION_SCOPE_REBUTTAL_MAP", resultsMap);
			model.addAttribute("rebuttalFilter",true);
			
			Collections.sort(rebuttalListTemp);
			
			String rebuttalListString = mapper.writeValueAsString(rebuttalListTemp).replaceAll("'", " ");
					
			rebuttalListString = response.encodeRedirectURL(rebuttalListString);
			request.getSession().setAttribute("SESSION_SCOPE_REBUTTAL_FILTER", rebuttalNew);
			model.addAttribute("rebuttalList",rebuttalListString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
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
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			
			if(loggedInUserMacId != null ) {
				userSearchObject.setMacId(Long.valueOf(loggedInUserMacId));
			}
			
			if(userFormSession.getJurId() !=null && !userFormSession.getJurId().equalsIgnoreCase("")) {
				String[] jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				
				ArrayList<String> jurIdArrayList = new ArrayList<String>();
				for (String jurisIdSingleValue: jurisIds) {
					
					jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
				}
				userSearchObject.setJurIdList(jurIdArrayList);
			}			
		}
		
		HashMap<Integer,String> failedMacRefList = setMacRefInSession(request, authentication, session);		
		
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
	
	private HashMap<Integer,String> setMacRefInSession( HttpServletRequest request, Authentication authentication, HttpSession session) {
		
		
		HashMap<Integer, ScoreCard> resultsMap = new HashMap<Integer,ScoreCard>();
		
		HashMap<Integer,String> failedMacRefList = new HashMap<Integer,String>();
		
		ScoreCard scoreCardTemp = new ScoreCard();
		List<ScoreCard> resultsMapTemp = null;
		List<ScoreCard> failedScorecardList = null;
		
		List<Rebuttal> rebuttalTempList = null;
		
		Rebuttal rebuttalNew = new Rebuttal();
		
		SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "retrieveMacCallRefFailList");
			
			String roles = authentication.getAuthorities().toString();
			
			//Restricting From Date to CRAD Migration Date Into CMS
			
			String filterFromDateString = "12/15/2018 00:00:00 AM";
			Date filterFromDate = utilityFunctions.convertToDateFromString(filterFromDateString);
				
			scoreCardTemp.setFilterFromDate(filterFromDate);	
			rebuttalNew.setFilterFromDate(filterFromDate);
			
			Date today = new Date();
									
			if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
				Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
				
				scoreCardTemp.setMacId(loggedInUserMacId);
				
				rebuttalNew.setMacId(loggedInUserMacId);
				
				String loggedInUserJurisdictionIds = (String) session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_IDS");
				
				if(loggedInUserJurisdictionIds !=null && !loggedInUserJurisdictionIds.equalsIgnoreCase("")) {
					String[] jurisIds = loggedInUserJurisdictionIds.split(UIGenericConstants.UI_JURISDICTION_SEPERATOR);
					
					ArrayList<Integer> jurIdArrayList = new ArrayList<Integer>();
					for (String jurisIdSingleValue: jurisIds) {
						
						jurIdArrayList.add(Integer.valueOf(jurisIdSingleValue));
					}				
					scoreCardTemp.setJurIdList(jurIdArrayList);
					rebuttalNew.setJurisIdList(jurIdArrayList);
				}			
			
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
								
				if(dayOfMonth < 15) {					
					cal.add(Calendar.MONTH, -1);					
				} 
				
				cal.set(Calendar.DATE, 14);		
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				
				scoreCardTemp.setFilterToDate(cal.getTime());	
				rebuttalNew.setFilterToDate(cal.getTime());
				
			} else {
				// Restricting Mac User and Mac Admin to only see data until 15th of the Month, based on the day
				scoreCardTemp.setFilterToDate(today);					
				rebuttalNew.setFilterToDate(today);
				
			}
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCardTemp, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMapTemp = responseEntity.getBody();
			failedScorecardList = mapper.convertValue(resultsMapTemp, new TypeReference<List<ScoreCard>>() { });
			
			rebuttalNew.setRebuttalStatus("Completed");
			
			String ROOT_URI2 = new String(HomeController.RAD_WS_URI + "rebuttallist");
			responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI2, rebuttalNew, List.class);
			
			rebuttalTempList = responseEntity.getBody();
			
			List<Rebuttal> rebuttalCompletedList = mapper.convertValue(rebuttalTempList, new TypeReference<List<Rebuttal>>() { });
			
			HashMap<String, String> rebuttalCompletedMacCallRefMap = new HashMap<String, String>();
			for(Rebuttal rebuttal: rebuttalCompletedList) {
				rebuttalCompletedMacCallRefMap.put(rebuttal.getMacCallReferenceNumber(), rebuttal.getMacCallReferenceNumber());
			}
			
			for(ScoreCard scoreCard: failedScorecardList) {
				if(rebuttalCompletedMacCallRefMap.get(scoreCard.getMacCallReferenceNumber())==null ) {
					resultsMap.put(scoreCard.getId(), scoreCard);
					failedMacRefList.put(scoreCard.getId(), scoreCard.getMacCallReferenceNumber());
				}
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
	
	@Value("${radui.uploadfile.server.location}")
    public String SERVER_UPLOAD_FILE_LOCATION;
	
	@RequestMapping(value ={"/admin/saveOrUpdateRebuttal", "/quality_manager/saveOrUpdateRebuttal", "/cms_user/saveOrUpdateRebuttal",
			 "/mac_admin/saveOrUpdateRebuttal","/mac_user/saveOrUpdateRebuttal","/quality_monitor/saveOrUpdateRebuttal"}, method = RequestMethod.POST)	
	public @ResponseBody Integer saveRebuttal(@ModelAttribute("rebuttal") Rebuttal rebuttal, final BindingResult result, 
			final RedirectAttributes redirectAttributes, final Model model, HttpSession session, HttpServletResponse response) {

		String returnView = "";
		log.debug("--> saverebuttal <--");
		ByteArrayResource fileAsResource = null;
		Integer returnRebuttalId = 0;
		try {
		
		//MultipartFile rebuttalMultipartObject = rebuttal.getRebuttalFileObject();

		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateRebuttal");
		
		String pattern = "MM/dd/yyyy hh:mm:ss a";
		
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
       TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
       sdfAmerica.setTimeZone(tzInAmerica);
       String currentDateString = sdfAmerica.format(new Date());
       
       User user =  (User) session.getAttribute("LoggedInUserForm");
		rebuttal.setUserId(user.getId().intValue());
       
       if(rebuttal.getId()==0) {
   		rebuttal.setDatePosted(new Date() );
   		rebuttal.setCreatedDate(currentDateString);
   		rebuttal.setUpdatedDate(currentDateString);
   		rebuttal.setCreatedBy(user.getUserName());
       } else {
       	rebuttal.setUpdatedDate(currentDateString);
       	rebuttal.setUpdatedBy(user.getUserName());
       }
       
		if(rebuttal.getRebuttalCompleteFlag()==null) {
			rebuttal.setRebuttalStatus("Pending");
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("Yes")) {
			rebuttal.setRebuttalStatus("Completed");
			
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("No")) {
			rebuttal.setRebuttalStatus("Pending");
			rebuttal.setRebuttalResult("Pending");
		} 
		
		if (rebuttal.getDescriptionComments() != null && !rebuttal.getDescriptionComments().equalsIgnoreCase("")) {
			rebuttal.setDescriptionComments(rebuttal.getDescriptionComments()+"::"+rebuttal.getDescriptionCommentsAppend());
		} else {
			rebuttal.setDescriptionComments(rebuttal.getDescriptionCommentsAppend());
		}
		
		
			rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
			rebuttal.setJurisName(HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId()));
			rebuttal.setRebuttalFileObject(null);
			ResponseEntity<Rebuttal> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
					Rebuttal.class);
			ObjectMapper mapper = new ObjectMapper();
			Rebuttal rebuttalTempObject = responseObject.getBody();
					    
			if (rebuttal.getId() == 0) {				
				
				redirectAttributes.addFlashAttribute("success",
						"success.create.rebuttal");
			} else {
				redirectAttributes.addFlashAttribute("success",
						"success.edit.rebuttal");
			}
			
			if(rebuttalTempObject.getId() != 0) {
				returnRebuttalId = rebuttalTempObject.getId();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		//String url = "redirect:/"+userFolder+"/rebuttallist/false";
		//url = response.encodeRedirectURL(url);
		
		
		return returnRebuttalId;
	}
	
	@RequestMapping(value ={"/admin/saveOrUpdateRebuttal2", "/quality_manager/saveOrUpdateRebuttal2", "/cms_user/saveOrUpdateRebuttal2",
			 "/mac_admin/saveOrUpdateRebuttal2","/mac_user/saveOrUpdateRebuttal2","/quality_monitor/saveOrUpdateRebuttal2"}, method = RequestMethod.POST)	
	public String saveRebuttal2(@ModelAttribute("rebuttal") Rebuttal rebuttal, final BindingResult result, 
			final RedirectAttributes redirectAttributes, final Model model, HttpSession session, HttpServletResponse response) {

		String returnView = "";
		log.debug("--> saverebuttal <--");
		
		ByteArrayResource fileAsResource = null;
		/*try {
			fileAsResource = new ByteArrayResource(rebuttal.getRebuttalFileObject().getBytes()) {
			    @Override
			    public String getFilename() {
			        return rebuttal.getRebuttalFileObject().getOriginalFilename();
			    }
			};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateRebuttalUpload");
		
		String pattern = "MM/dd/yyyy hh:mm:ss a";

	    //add file
	    LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
	    params.add("file", fileAsResource);

	    //add array
	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ROOT_URI);
	   
	    
	   
	    //add some String
	    //builder.queryParam("name", name);

	    //another staff
	    String resultOutput = "";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	    HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
	            new HttpEntity<>(params, headers);

	    ResponseEntity<Rebuttal> responseEntity = basicAuthRestTemplate.exchange(
	            builder.build().encode().toUri(),
	            HttpMethod.POST,
	            requestEntity,
	            Rebuttal.class);
	    
	   /* ResponseEntity<Rebuttal> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
				Rebuttal.class);*/

	  /*  HttpStatus statusCode = responseEntity.getStatusCode();
	    if (statusCode == HttpStatus.ACCEPTED) {
	        result = responseEntity.getBody();
	    }*/
	    return resultOutput;

		//Finish
		
		
		
		
		
		
		
		/*

		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateRebuttal");
		
		String pattern = "MM/dd/yyyy hh:mm:ss a";
		
		
		String fileName = "";
		 MultipartFile tempMultipartFile = null;
		 LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		 map.add("file", new ClassPathResource(file));
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		 HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
		                     map, headers);
		 ResponseEntity<String> result = template.get().exchange(
		                     contextPath.get() + path, HttpMethod.POST, requestEntity,
		                     String.class);
		
		if (rebuttal.getRebuttalFileObject() != null && !rebuttal.getRebuttalFileObject().isEmpty()) {
           try {
               fileName = rebuttal.getRebuttalFileObject().getOriginalFilename();
               tempMultipartFile = rebuttal.getRebuttalFileObject();
               byte[] bytes = rebuttal.getRebuttalFileObject().getBytes();
               rebuttal.setRebuttalFileAttachment(bytes);
               rebuttal.setHttpFileData(new ByteArrayResource(bytes));
               BufferedOutputStream buffStream = 
                       new BufferedOutputStream(new FileOutputStream(new File(SERVER_UPLOAD_FILE_LOCATION + fileName)));
               buffStream.write(bytes);
               buffStream.close();
               //return "You have successfully uploaded " + fileName;
           } catch (Exception e) {
               //return "You failed to upload " + fileName + ": " + e.getMessage();
           }
       } else {
           //return "Unable to upload. File is empty.";
       }
		
		//Nulling the File Value
		rebuttal.setRebuttalFileObject(null);
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(pattern);
      TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
      sdfAmerica.setTimeZone(tzInAmerica);
      String currentDateString = sdfAmerica.format(new Date());
      
      User user =  (User) session.getAttribute("LoggedInUserForm");
		rebuttal.setUserId(user.getId().intValue());
      
      if(rebuttal.getId()==0) {
  		rebuttal.setDatePosted(new Date() );
  		rebuttal.setCreatedDate(currentDateString);
  		rebuttal.setUpdatedDate(currentDateString);
  		rebuttal.setCreatedBy(user.getUserName());
      } else {
      	rebuttal.setUpdatedDate(currentDateString);
      	rebuttal.setUpdatedBy(user.getUserName());
      }
      
		if(rebuttal.getRebuttalCompleteFlag()==null) {
			rebuttal.setRebuttalStatus("Pending");
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("Yes")) {
			rebuttal.setRebuttalStatus("Completed");
			
		} else if(rebuttal.getRebuttalCompleteFlag().equalsIgnoreCase("No")) {
			rebuttal.setRebuttalStatus("Pending");
			rebuttal.setRebuttalResult("Pending");
		} 
		
		if (rebuttal.getDescriptionComments() != null && !rebuttal.getDescriptionComments().equalsIgnoreCase("")) {
			rebuttal.setDescriptionComments(rebuttal.getDescriptionComments()+"::"+rebuttal.getDescriptionCommentsAppend());
		} else {
			rebuttal.setDescriptionComments(rebuttal.getDescriptionCommentsAppend());
		}
		
		
		 
		
		try {
			rebuttal.setMacName(HomeController.MAC_ID_MAP.get(rebuttal.getMacId()));
			rebuttal.setJurisName(HomeController.JURISDICTION_MAP.get(rebuttal.getJurisId()));
			
			ResponseEntity<Rebuttal> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, rebuttal,
					Rebuttal.class);
			if (rebuttal.getId() == 0) {
				redirectAttributes.addFlashAttribute("success",
						"success.create.rebuttal");
			} else {
				redirectAttributes.addFlashAttribute("success",
						"success.edit.rebuttal");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		String url = "redirect:/"+userFolder+"/rebuttallist/false";
		url = response.encodeRedirectURL(url);

		return url;*/
	}
	
	
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
	public String editRebuttalGet(@PathVariable("id") final Integer id, @ModelAttribute("userForm") User userForm,final Model model, HttpSession session,HttpServletRequest request, Authentication authentication) {
		HashMap<Integer,Rebuttal> resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTAL_MAP");
		Rebuttal rebuttal = resultsMap.get(id);
		
		HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
		
		String datePostedString = utilityFunctions.convertToStringFromDate(rebuttal.getDatePosted());
		
		if(rebuttal.getRebuttalStatus() == null) {
			rebuttal.setRebuttalCompleteFlag("");
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Completed")) {
			rebuttal.setRebuttalCompleteFlag("Yes");			
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Pending")) {
			rebuttal.setRebuttalCompleteFlag("No");
		} 
		if(rebuttal.getDatePosted() != null) {
			rebuttal.setDatePostedString(
					utilityFunctions.convertToStringFromDate(rebuttal.getDatePosted()));
		}
		rebuttal.setCallCategoryForDisplay(rebuttal.getCallCategory());
		rebuttal.setLobForDisplay(rebuttal.getLob());
		//rebuttal.setDescriptionCommentsAppend(rebuttal.getDescriptionComments());
		
		String roles = authentication.getAuthorities().toString();
		User userSearchObject = new User();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			Integer loggedInUserMacId = (Integer) session.getAttribute("SESSION_LOGGED_IN_USER_MAC_ID");
			userSearchObject.setMacId(Long.valueOf(loggedInUserMacId));
			
			String[] jurisIds = userFormSession.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			
			ArrayList<String> jurIdArrayList = new ArrayList<String>();
			for (String jurisIdSingleValue: jurisIds) {
				
				jurIdArrayList.add(jurisIdSingleValue+UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			}
			userSearchObject.setJurIdList(jurIdArrayList);
		}
		
		HashMap<Integer,String> failedMacRefList = setMacRefInSession(request, authentication, session);		
		
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		
		ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
		ObjectMapper mapper = new ObjectMapper();
		List<User> userResultsList = new ArrayList<User> ();
		userResultsList = responseEntity.getBody();
		List<User> userList = mapper.convertValue(userResultsList, new TypeReference<List<User>>() { });
		
		for(User userTemp: userList) {
			pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
		}
		
		
		model.addAttribute("pccContactPersonMap",pccContactPersonMap);
		
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
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
		HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
		User userSearchObject = new User();
		
		if (resultsMap == null ) {
			resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTAL_MAP");
		}
		
		Rebuttal rebuttal = null;
		if (resultsMap != null) {
			rebuttal = resultsMap.get(id);
		}
		if(rebuttal.getDatePosted() != null) {
			rebuttal.setDatePostedString(
					utilityFunctions.convertToStringFromDate(rebuttal.getDatePosted()));
		}
		
		if(rebuttal.getRebuttalStatus() == null) {
			rebuttal.setRebuttalCompleteFlag("");
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Completed")) {
			rebuttal.setRebuttalCompleteFlag("Yes");			
		} else if(rebuttal.getRebuttalStatus().equalsIgnoreCase("Pending")) {
			rebuttal.setRebuttalCompleteFlag("No");
		} 
		
		rebuttal.setCallCategoryForDisplay(rebuttal.getCallCategory());
		rebuttal.setLobForDisplay(rebuttal.getLob());
		model.addAttribute("rebuttal", rebuttal);
		model.addAttribute("callCategoryMap", HomeController.CALL_CATEGORY_MAP);
		HashMap<Integer,String> pccLocationMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(rebuttal.getMacId()+"_"+rebuttal.getJurisId()+"_"+rebuttal.getProgramId());
		
		model.addAttribute("programMapEdit", pccLocationMap);
		userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
		userSearchObject.setRoleString(UIGenericConstants.MAC_USER_ROLE);
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchUsers");
		
		ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, userSearchObject, List.class);
		ObjectMapper mapper = new ObjectMapper();
		List<User> userResultsList = new ArrayList<User> ();
		userResultsList = responseEntity.getBody();
		List<User> userList = mapper.convertValue(userResultsList, new TypeReference<List<User>>() { });
		
		for(User userTemp: userList) {
			pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
		}
		
		
		model.addAttribute("pccContactPersonMap",pccContactPersonMap);
		return "rebuttalview";
	}	
	
	@RequestMapping(value ={"/admin/download-rebuttal/{id}", "/quality_manager/download-rebuttal/{id}", "/cms_user/download-rebuttal/{id}",
			 "/mac_admin/download-rebuttal/{id}","/mac_user/download-rebuttal/{id}","/quality_monitor/download-rebuttal/{id}"}, method = RequestMethod.GET)		
	public void downladRebuttalGet(@PathVariable("id") final Integer id, HttpSession session, HttpServletResponse response) {
		HashMap<Integer,Rebuttal> resultsMap = (HashMap<Integer, Rebuttal>) session.getAttribute("SESSION_SCOPE_REBUTTAL_MAP");
		Rebuttal rebuttal = resultsMap.get(id);		
		try {
			response.setContentType(rebuttal.getFileType());
			response.setContentLength(rebuttal.getRebuttalFileAttachment().length);
			response.setHeader("Content-Disposition","attachment; filename=\"" + rebuttal.getFileName() +"\"");
						 
			response.addHeader("Content-Disposition","attachment; filename=\"" + rebuttal.getFileName() +"\"");
			response.addHeader("X-Frame-Options", "ALLOWALL");
						 
			InputStream targetStream = new ByteArrayInputStream(rebuttal.getRebuttalFileAttachment());
			IOUtils.copy(targetStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}