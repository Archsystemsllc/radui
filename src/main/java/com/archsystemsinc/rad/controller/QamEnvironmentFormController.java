package com.archsystemsinc.rad.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UploadResponse;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.QamEnvironmentChangeForm;
import com.archsystemsinc.rad.model.Rebuttal;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class QamEnvironmentFormController {
	private static final Logger log = Logger.getLogger(QamEnvironmentFormController.class);

	/**
	 * This method provides the functionalities for listing users.
	 * 
	 * @param
	 * @return
	 */
	
	BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");

	
	@RequestMapping(value ={"/admin/qamenvironmentform", "/quality_manager/qamenvironmentform", "/cms_user/qamenvironmentform",
			 "/mac_admin/qamenvironmentform","/mac_user/qamenvironmentform","/quality_monitor/qamenvironmentform"})	
	public String viewQamEnvironmentForm(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewQamEnvironmentForm <--");
		
		QamEnvironmentChangeForm qamEnvironmentChangeForm = new QamEnvironmentChangeForm();
		
		
		User userObject = (User) session.getAttribute("LoggedInUserForm");
		
		String roles = authentication.getAuthorities().toString();
		
		if(roles.contains(UIGenericConstants.MAC_ADMIN_ROLE_STRING) || roles.contains(UIGenericConstants.MAC_USER_ROLE_STRING)) {
			
			model.addAttribute("macIdMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_MAC_MAP"));		
			model.addAttribute("jurisMapEdit", session.getAttribute("SESSION_LOGGED_IN_USER_JURISDICTION_MAP"));			
			
		} else {
			model.addAttribute("macIdMapEdit", HomeController.MAC_ID_MAP);	
			model.addAttribute("jurisMapEdit", HomeController.JURISDICTION_MAP);			
			
		}		
		qamEnvironmentChangeForm.setUserId(userObject.getId().intValue());
		model.addAttribute("qamEnvironmentForm", qamEnvironmentChangeForm);
		//session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
		return "qam_environment_change";
	}
	
	
	@RequestMapping(value ={"/admin/download-qamenvironmentform/{id}", "/quality_manager/download-qamenvironmentform", "/cms_user/download-qamenvironmentform/{id}",
			 "/mac_admin/download-qamenvironmentform/{id}","/mac_user/download-qamenvironmentform/{id}","/quality_monitor/download-qamenvironmentform/{id}"}, method = RequestMethod.GET)	
	public void downloadQamEnvironmentForm(@PathVariable("id") final Integer id, HttpSession session,HttpServletResponse response) {
		QamEnvironmentChangeForm qamEnvironmentChangeForm = new QamEnvironmentChangeForm();
		ObjectMapper mapper = new ObjectMapper();
		qamEnvironmentChangeForm.setQamEnvironmentChangeFormId(Long.valueOf(id));
		
		String ROOT_URI = new String(HomeController.RAD_WS_URI + "findEnvironmentalChangeControlForm");
		ResponseEntity<QamEnvironmentChangeForm> webServiceResponse = basicAuthRestTemplate.postForEntity(ROOT_URI, qamEnvironmentChangeForm,QamEnvironmentChangeForm.class);
		
		QamEnvironmentChangeForm qamEnvironmentFormTemp = mapper.convertValue(webServiceResponse.getBody(), new TypeReference<QamEnvironmentChangeForm>() { });
		
		
		try {
			response.setContentType(qamEnvironmentFormTemp.getFileType());
			response.setContentLength(qamEnvironmentFormTemp.getDocumentContent().length);
			response.setHeader("Content-Disposition","attachment; filename=\"" + qamEnvironmentFormTemp.getDocumentName() +"\"");
			
			response.addHeader("X-Frame-Options", "ALLOWALL");
						 
			InputStream targetStream = new ByteArrayInputStream(qamEnvironmentFormTemp.getDocumentContent());
			IOUtils.copy(targetStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ={"/admin/uploadQamEnvFormUI", "/quality_manager/uploadQamEnvFormUI", "/cms_user/uploadQamEnvFormUI",
			 "/mac_admin/uploadQamEnvFormUI","/mac_user/uploadQamEnvFormUI","/quality_monitor/uploadQamEnvFormUI"}, method = RequestMethod.POST)	
	
	public @ResponseBody   UploadResponse uploadFileData(@RequestParam("file") MultipartFile uploadedFile,@RequestParam("userId") Long userId,@RequestParam("macIdU") Long macId
			,@RequestParam("jurisdictionUText") Long jurisdictionId,@RequestParam("formType") String formType){
		log.debug("--> uploadFileData:");
		UploadResponse response = new UploadResponse();
		
		try {		

			String ROOT_URI = new String(HomeController.RAD_WS_URI + "uploadQamEnvForm");
			
			 String file_name = uploadedFile.getOriginalFilename();
		      ByteArrayResource contentsAsResource = new ByteArrayResource(uploadedFile.getBytes()) {
			        @Override
			        public String getFilename() {
			            return file_name; // Filename has to be returned in order to be able to post.
			        }
			    };
		    
			MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		      bodyMap.add("file", contentsAsResource);
		      bodyMap.add("userId", userId);
		      bodyMap.add("macIdU", macId);
		      bodyMap.add("jurisdictionUText", jurisdictionId);
		      bodyMap.add("formType", formType);
		      
		      HttpHeaders headers = new HttpHeaders();
		      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		      //RestTemplate restTemplate = new RestTemplate();
		     ResponseEntity<UploadResponse> fileUploadResponse = basicAuthRestTemplate.exchange(ROOT_URI,
		              HttpMethod.POST, requestEntity, UploadResponse.class);
		     response = fileUploadResponse.getBody();
			//String uploadResult=csrListService.uploadFileData(uploadedFile,userId,null,macId,jurisdictionList);
			//response.setStatus(uploadResult);
		} catch(Exception e) {
			log.error("Error while uploading QamEnvironmentChangeForm",e);
			response.setStatus("ERROR");
			response.setErroMessage(e.getMessage());
		}
		
		log.debug("<-- uploadFileData");
		return response;
	}
	
	@RequestMapping(value ={"/admin/qamEnvListMonthsUI", "/quality_manager/qamEnvListMonthsUI", "/cms_user/qamEnvListMonthsUI",
			 "/mac_admin/qamEnvListMonthsUI","/mac_user/qamEnvListMonthsUI","/quality_monitor/qamEnvListMonthsUI"}, method = RequestMethod.GET)	
	public @ResponseBody List<Object[]> getQamEnvListMonths(@RequestParam("fromDate") String from, @RequestParam("toDate") String to, @RequestParam("macIdS") String macLookupIdList, @RequestParam("jurisdictionS") String jurisdictionList){
		log.debug("--> getQamEnvListMonths:");
		List<Object[] > finalDataSet = null;
		
		QamEnvironmentChangeForm qamEnvironmentChangeForm = new QamEnvironmentChangeForm();
		//List<CsrLists> csrListNames = new ArrayList<CsrLists>();
		List<Object[]> resultsList = new ArrayList<Object[]> ();
		try {			
			qamEnvironmentChangeForm.setMacIdS(macLookupIdList.substring(1,macLookupIdList.length()-1));
			qamEnvironmentChangeForm.setJurisdictionS(jurisdictionList.substring(1,jurisdictionList.length()-1));
			qamEnvironmentChangeForm.setFromDate(from);
			qamEnvironmentChangeForm.setToDate(to);
			
			List<QamEnvironmentChangeForm> resultsMap = new ArrayList<QamEnvironmentChangeForm> ();			
			
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "qamEnvListMonths");
			
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, qamEnvironmentChangeForm, List.class);
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