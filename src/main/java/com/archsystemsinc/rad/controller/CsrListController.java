package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;


import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UploadResponse;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.CsrLists;
import com.archsystemsinc.rad.model.CsrUploadForm;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CsrListController {
	private static final Logger log = Logger.getLogger(CsrListController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */

	BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
	
	@RequestMapping(value ={"/admin/csrlist", "/quality_manager/csrlist", "/cms_user/csrlist",
			 "/mac_admin/csrlist","/mac_user/csrlist","/quality_monitor/csrlist"})	
	public String viewCSRList(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> showAdminDashboard <--");
		
		CsrUploadForm csrUploadForm = new CsrUploadForm();
		
		
		User userObject = (User) session.getAttribute("LoggedInUserForm");
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			session.getAttribute("LOGGED_IN_USER_MAC_MAP");
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP") );			
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		csrUploadForm.setUserId(userObject.getId().intValue());
		model.addAttribute("csrUploadForm", csrUploadForm);
		return "csrlist";
	}
	
	
	@RequestMapping(value ={"/admin/csrListNamesUI", "/quality_manager/csrListNamesUI", "/cms_user/csrListNamesUI",
			 "/mac_admin/csrListNamesUI","/mac_user/csrListNamesUI","/quality_monitor/csrListNamesUI"}, method = RequestMethod.GET)	
	public @ResponseBody List<CsrLists> getCsrListNames(@RequestParam("term")  String nameLiteral,@RequestParam("macIdS") String macLookupId, 
			@RequestParam("jurisdictionS") String jurisdiction, @RequestParam("programS") String program) {
		
		
		CsrLists csrList = new CsrLists();
		List<CsrLists> csrListNames = new ArrayList<CsrLists>();
		try {
			csrList.setMacLookupId(Long.valueOf(macLookupId));
			csrList.setJurisdiction(jurisdiction);
			csrList.setProgram(program);
			csrList.setSearchStringLiteral(nameLiteral);
			List<CsrLists> resultsMap = new ArrayList<CsrLists> ();			
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "csrListNames");
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, csrList, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			csrListNames = mapper.convertValue(resultsMap, new TypeReference<List<CsrLists>>() { });
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return csrListNames;
	}
	
	
	
	@RequestMapping(value ={"/admin/keepCurrentListUI", "/quality_manager/keepCurrentListUI", "/cms_user/keepCurrentListUI",
			 "/mac_admin/keepCurrentListUI","/mac_user/keepCurrentListUI","/quality_monitor/keepCurrentListUI"}, method = RequestMethod.GET)	
	public @ResponseBody UploadResponse keepCurrentList(@RequestParam("userId") Long userId,@RequestParam("macIdK") Long macId,@RequestParam("jurisdictionK") String jurisdiction){
		log.debug("--> keepCurrentList:");
		UploadResponse response = new UploadResponse();
		String keepCurrentList = "true";
		String statusString = "";
		CsrLists csrList = new CsrLists();
		try {
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "keepCurrentList");
			
			csrList.setUserId(userId);
			csrList.setMacLookupId(macId);
			csrList.setJurisdiction(jurisdiction.substring(1,jurisdiction.length()-1));
						
			ResponseEntity<UploadResponse> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, csrList, UploadResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			response = responseEntity.getBody();
				
		} catch (Exception e) {
			log.error("Error while uploading data",e);
			response.setStatus("ERROR");
			response.setErroMessage(e.getMessage());
		}
		log.debug("<-- keepCurrentList");
		return response;
	}
	
	@RequestMapping(value ={"/admin/uploadCsrListUI", "/quality_manager/uploadCsrListUI", "/cms_user/uploadCsrListUI",
			 "/mac_admin/uploadCsrListUI","/mac_user/uploadCsrListUI","/quality_monitor/uploadCsrListUI"}, method = RequestMethod.POST)
	public @ResponseBody  UploadResponse uploadFileData(@RequestParam("csrFileObject") MultipartFile csrFileObject, @RequestParam("userId") String userId, 
			@RequestParam("macIdU") String macLookupIdList, @RequestParam("jurisdictionU") String jurisdictionList){
		log.debug("--> uploadFileData:");
		UploadResponse response = new UploadResponse();
		//CsrLists csrList = new CsrLists();
		try {
			
			
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "uploadCsrList");
			
			 String file_name = csrFileObject.getOriginalFilename();
		      ByteArrayResource contentsAsResource = new ByteArrayResource(csrFileObject.getBytes()) {
			        @Override
			        public String getFilename() {
			            return file_name; // Filename has to be returned in order to be able to post.
			        }
			    };
		    
			MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		      bodyMap.add("file", contentsAsResource);
		      bodyMap.add("userId", userId);
		      bodyMap.add("macIdU", macLookupIdList);
		      bodyMap.add("jurisdictionUText", jurisdictionList);
		      
		      HttpHeaders headers = new HttpHeaders();
		      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		      //RestTemplate restTemplate = new RestTemplate();
		     ResponseEntity<UploadResponse> fileUploadResponse = basicAuthRestTemplate.exchange(ROOT_URI,
		              HttpMethod.POST, requestEntity, UploadResponse.class);
			
			//String uploadResult=csrListService.uploadFileData(uploadedFile,userId,null,macId,jurisdictionList);
			//response.setStatus(uploadResult);
		} catch (Exception e) {
			log.error("Error while uploading data",e);
			response.setStatus("ERROR");
			response.setErroMessage(e.getMessage());
		}
		log.debug("<-- uploadFileData");
		return response;
	}
	
	@RequestMapping(value ={"/admin/csrListUI", "/quality_manager/csrListUI", "/cms_user/csrListUI",
			 "/mac_admin/csrListUI","/mac_user/csrListUI","/quality_monitor/csrListUI"}, method = RequestMethod.GET)
	
	public @ResponseBody   List<CsrLists> getCsrList(@RequestParam("fromDate") String from, @RequestParam("toDate") String to, @RequestParam("macIdS") String macLookupIdList, @RequestParam("jurisdictionS") String jurisdictionList){
		log.debug("--> getCsrList:");
		
		log.debug("--> getCsrListMonths:");
		
		CsrLists csrList = new CsrLists();
		List<CsrLists> finalList = new ArrayList<CsrLists>();
		List<CsrLists> resultsList = new ArrayList<CsrLists>();
		try {
			csrList.setMacIdString(macLookupIdList.substring(1,macLookupIdList.length()-1));
			csrList.setJurisdiction(jurisdictionList.substring(1,jurisdictionList.length()-1));
			csrList.setCsrSearchFromDate(from);
			csrList.setCsrSearchToDate(to);
			
			List<CsrLists> resultsMap = new ArrayList<CsrLists> ();			
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "csrList");
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, csrList, List.class);
			ObjectMapper mapper = new ObjectMapper();
					
			resultsMap = responseEntity.getBody();
			resultsList = mapper.convertValue(resultsMap, new TypeReference<List<CsrLists>>() { });
			
			for (CsrLists csrListTemp: resultsList) {
				csrListTemp.setMacName(HomeController.MAC_ID_MAP.get(csrListTemp.getMacLookupId()));
				finalList.add(csrListTemp);
			}
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return finalList;
	}	
	
	
	@RequestMapping(value ={"/admin/csrListMonthsUI", "/quality_manager/csrListMonthsUI", "/cms_user/csrListMonthsUI",
			 "/mac_admin/csrListMonthsUI","/mac_user/csrListMonthsUI","/quality_monitor/csrListMonthsUI"}, method = RequestMethod.GET)
	public @ResponseBody List<Object[]> getCsrListMonths(@RequestParam("fromDate") String from, @RequestParam("toDate") String to, @RequestParam("macIdS") String macLookupIdList, @RequestParam("jurisdictionS") String jurisdictionList){
		log.debug("--> getCsrListMonths:");
		
		CsrLists csrList = new CsrLists();
		//List<CsrLists> csrListNames = new ArrayList<CsrLists>();
		List<Object[]> resultsList = new ArrayList<Object[]> ();
		try {
			csrList.setMacIdString(macLookupIdList.substring(1,macLookupIdList.length()-1));
			csrList.setJurisdiction(jurisdictionList.substring(1,jurisdictionList.length()-1));
			csrList.setCsrSearchFromDate(from);
			csrList.setCsrSearchToDate(to);
			//csrList.setProgram(program);
			//csrList.setSearchStringLiteral(nameLiteral);
			List<CsrLists> resultsMap = new ArrayList<CsrLists> ();			
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "csrListMonths");
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, csrList, List.class);
			ObjectMapper mapper = new ObjectMapper();
			resultsMap = responseEntity.getBody();
			resultsList = mapper.convertValue(resultsMap, new TypeReference<List<Object[]>>() { });
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultsList;
	}	
	
	
	
}