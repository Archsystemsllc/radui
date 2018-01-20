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
	public static HashMap<Integer,HashMap<Integer,String>> MAC_JURISDICTION_MAP;
	public static HashMap<String,HashMap<Integer,String>> MAC_JURISDICTION_PROGRAM_MAP;
	public static HashMap<String,ArrayList<String>> MAC_PCC_PROGRAM_JURISDICTION_LIST;
	
	
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
			
			HashMap<Integer, String> macIdMap = null;
			
			ArrayList<MacInfo> macInfoList = null;
			
			HashMap<Integer, String> jurisdictionMap = null;
			
			HashMap<Integer,HashMap<Integer,String>> mapJurisMap = new HashMap<Integer,HashMap<Integer,String>>();
			
			HashMap<String,HashMap<Integer,String>> mapJurisProgramMap = new HashMap<String,HashMap<Integer,String>>();
			
			HashMap<Integer, MacInfo> macObjectMap = new HashMap<Integer, MacInfo>();
			try {
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "programMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				programMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "jurisdictionMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				jurisdictionMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
				
				JURISDICTION_MAP = jurisdictionMap;
				
				webServiceExchange = restTemplate.exchange(REST_SERVICE_URI + "macIdMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
				
				macIdMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});			
				
				MAC_ID_MAP = macIdMap;
				
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
				}
				
				for (MacInfo macInfo: macInfoList) {
					macObjectMap.put(macInfo.getId().intValue(), macInfo);
				}
				
				MAC_OBJECT_MAP = macObjectMap;
				MAC_JURISDICTION_MAP = mapJurisMap;
				MAC_JURISDICTION_PROGRAM_MAP = mapJurisProgramMap;
				
			} catch (JsonParseException e) {
				
				e.printStackTrace();
			} catch (JsonMappingException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	
}
