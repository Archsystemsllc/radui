/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.MacProgJurisPccMapping;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author PrakashTotta
 *
 */
@Controller
public class HomeController {

	private static final Logger log = Logger.getLogger(HomeController.class);
	
	//Local For Testing 
	public static final String REST_SERVICE_URI = "http://localhost:8080/radservices/api/";
	//Prod URL
	//public static final String REST_SERVICE_URI = "http://radservices.us-east-1.elasticbeanstalk.com/api/";
	
	public static Integer setupGlobalVarRanCount = 1;
	
	public static HashMap<Integer, String> MAC_ID_MAP;
	public static HashMap<Integer, MacInfo> MAC_OBJECT_MAP;
	public static HashMap<Integer, String> JURISDICTION_MAP;
	public static HashMap<Integer, String> ALL_PROGRAM_MAP;
	public static HashMap<Integer, String> ALL_PCC_LOCATION_MAP;
	public static HashMap<Integer,HashMap<Integer,String>> MAC_JURISDICTION_MAP;
	public static HashMap<Integer,HashMap<Integer,String>> ORGANIZATION_MAP;
	public static HashMap<Integer,HashMap<Integer,String>> ROLE_MAP;
	public static HashMap<Integer,String> PCC_LOC_MAP;
	public static HashMap<String,HashMap<Integer,String>> MAC_JURISDICTION_PROGRAM_MAP;
	public static HashMap<String,HashMap<Integer,String>> MAC_JURISDICTION_PROGRAM_PCC_MAP;
	
	public static HashMap<Integer, String> CALL_CATEGORY_MAP = null;
	
	public static HashMap<Integer,HashMap<Integer,String>> CALL_CATEGORY_SUB_CATEGORY_MAP = null;
	
	
	 @RequestMapping(value = "/admin/dashboard")
	 public String showAdminDashboard(Model model, HttpSession session) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  model.addAttribute("userForm", form);
		  session.setAttribute("WEB_SERVICE_URL",HomeController.REST_SERVICE_URI);
		  if(MAC_ID_MAP == null) {
			  setupGlobalVariables();
		  }
		  return "homepage";
	} 
	 
	private void setupGlobalVariables() {
		System.out.println("Setup Run Count"+setupGlobalVarRanCount);
		setupGlobalVarRanCount++;
		 String plainCreds = "qamadmin:123456";
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.add("Authorization", "Basic " + base64Creds);

			headers.set("Content-Length", "35");
			RestTemplate restTemplate = new RestTemplate();
			
			
			ResponseEntity<String> webServiceExchange = null;			
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<MacProgJurisPccMapping> macProgJurisPccMappingList = null;
			
			HashMap<Integer, String> programMap = null;
			
			HashMap<Integer, String> pccLocationMap = null;
			
			HashMap<Integer, String> macIdMap = null;
			
			ArrayList<MacInfo> macInfoList = null;
			
			HashMap<Integer, String> jurisdictionMap = null;
			
			HashMap<Integer,HashMap<Integer,String>> mapJurisMap = new HashMap<Integer,HashMap<Integer,String>>();
			
			HashMap<String,HashMap<Integer,String>> mapJurisProgramMap = new HashMap<String,HashMap<Integer,String>>();
			
			HashMap<String,HashMap<Integer,String>> macJurisProgramLocationMap = new HashMap<String,HashMap<Integer,String>>();
			
			HashMap<Integer, MacInfo> macObjectMap = new HashMap<Integer, MacInfo>();
			
			HashMap<Integer, String> callCategoryMap = null;
			
			HashMap<Integer,HashMap<Integer,String>> callCatSubCatMap = null;
			try {
				
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "pccLocationMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				pccLocationMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
				
				ALL_PCC_LOCATION_MAP = pccLocationMap;
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "programMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				programMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
				
				ALL_PROGRAM_MAP = programMap;
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "jurisdictionMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				jurisdictionMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
				
				JURISDICTION_MAP = jurisdictionMap;
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "macIdMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				macIdMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});			
				
				MAC_ID_MAP = macIdMap;
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "orgMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				ORGANIZATION_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "roleMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				ROLE_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "pccLocationMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				PCC_LOC_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
				
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "macList", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				macInfoList = mapper.readValue(webServiceExchange.getBody(), new TypeReference<ArrayList<MacInfo>>(){});			
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "macPrgmJurisPccList", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				macProgJurisPccMappingList = mapper.readValue(webServiceExchange.getBody(), new TypeReference<List<MacProgJurisPccMapping>>(){});
				for (MacProgJurisPccMapping macProgJurisPccMapping: macProgJurisPccMappingList) {					
									
					//MAC Jurisdiction Map Code
					String jurisName = jurisdictionMap.get(macProgJurisPccMapping.getJurisdictionId());
					HashMap<Integer,String> jurisTempMap = mapJurisMap.get(macProgJurisPccMapping.getMacId());
					if(jurisTempMap == null) {
						jurisTempMap = new HashMap<Integer,String>();
						jurisTempMap.put(macProgJurisPccMapping.getJurisdictionId(),jurisName);
					} else {
						jurisTempMap.put(macProgJurisPccMapping.getJurisdictionId(),jurisName);
					}
					
					mapJurisMap.put(macProgJurisPccMapping.getMacId(), jurisTempMap);
					
					//MAC Jurisdiction Program Map Code
					String programName = programMap.get(macProgJurisPccMapping.getProgramId());
					String macId_jurisId_Key = macProgJurisPccMapping.getMacId()+"_"+macProgJurisPccMapping.getJurisdictionId();
					HashMap<Integer,String> programTempMap = mapJurisProgramMap.get(macId_jurisId_Key);
					if(programTempMap == null) {
						programTempMap = new HashMap<Integer,String>();
						programTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
					} else {
						programTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
					}
					
					mapJurisProgramMap.put(macId_jurisId_Key, programTempMap);		
					
					//MAC Jurisdiction Program Location Map Code
					
					String pccLocation = pccLocationMap.get(macProgJurisPccMapping.getPccId());
					String macId_jurisId_programId_Key = macProgJurisPccMapping.getMacId()+"_"+macProgJurisPccMapping.getJurisdictionId()+"_"+macProgJurisPccMapping.getProgramId();
					
					HashMap<Integer,String> pccLocationTempMap = macJurisProgramLocationMap.get(macId_jurisId_programId_Key);
					if(pccLocationTempMap == null) {
						pccLocationTempMap = new HashMap<Integer,String>();
						pccLocationTempMap.put(macProgJurisPccMapping.getPccId(),pccLocation);
					} else {
						pccLocationTempMap.put(macProgJurisPccMapping.getPccId(),pccLocation);
					}
					
					macJurisProgramLocationMap.put(macId_jurisId_programId_Key, pccLocationTempMap);		
				}
				
				for (MacInfo macInfo: macInfoList) {
					macObjectMap.put(macInfo.getId().intValue(), macInfo);
				}
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "callCategoryMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				callCategoryMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "callSubcategoriesMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				callCatSubCatMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,HashMap<Integer,String>>>(){});
				
				CALL_CATEGORY_MAP = callCategoryMap;
				CALL_CATEGORY_SUB_CATEGORY_MAP = callCatSubCatMap;
				
				MAC_OBJECT_MAP = macObjectMap;
				MAC_JURISDICTION_MAP = mapJurisMap;
				MAC_JURISDICTION_PROGRAM_MAP = mapJurisProgramMap;
				MAC_JURISDICTION_PROGRAM_PCC_MAP = macJurisProgramLocationMap;
				
			} catch (JsonParseException e) {
				
				e.printStackTrace();
			} catch (JsonMappingException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	
}
