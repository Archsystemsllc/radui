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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.MacProgJurisPccMapping;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.UserService;
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
	
	@Value("${rad.webservice.uri}")
	String radServicesEndPoint;
	@Value("${radservices.username}")
	String radservicesUserName;
	@Value("${radservices.password}")
	String radservicesPassword;
	
	@Value("${rad.webservice.uri}")
    public String RAD_WS_URI2;
	
	@Autowired
	private UserService userService;
	
	public static String RAD_WS_URI;
	
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
	public static HashMap<Integer,HashMap<Integer,String>> JURISDICTION_PROGRAM_MAP;
	public static HashMap<String,HashMap<Integer,String>> MAC_JURISDICTION_PROGRAM_PCC_MAP;
	
	public static HashMap<Integer, String> CALL_CATEGORY_MAP = null;
	
	public static HashMap<Integer,HashMap<Integer,String>> CALL_CATEGORY_SUB_CATEGORY_MAP = null;
	
	public static HashMap<Integer, String> LOGGED_IN_USER_MAC_MAP;
	public static HashMap<Integer, String> LOGGED_IN_USER_JURISDICTION_MAP;
	public static HashMap<Integer, String> LOGGED_IN_USER_PCC_LOCATION_MAP;
	
	public static Integer LOGGED_IN_USER_MAC_ID;
	
	public static String LOGGED_IN_USER_JURISDICTION_IDS;
	
	public static String LOGGED_IN_USER_JURISDICTION_NAMES;
	
	/**
     * This method provides the functionalities for the User Registration.
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/privacy", method = RequestMethod.GET)
    public String privacy(Model model) {       
       
        return "privacy";
    }
    
	
	 @RequestMapping(value = "/admin/dashboard")
	 public String showAdminDashboard(Model model, HttpSession session) {
		  log.debug("--> Enter showAdminDashboard <--");
		  try {
			RAD_WS_URI = radServicesEndPoint;
			  User form = new User();
			  model.addAttribute("userForm", form);
			  model.addAttribute("menu_highlight", "home");
			  
			  session.setAttribute("SS_USER_FOLDER","admin");
			 if(MAC_ID_MAP == null) {
				 setupStaticGlobalVariables();
			  }
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 String userName = auth.getName(); //get logged in username 
			 User user = userService.findByUsername(userName);
			 
			 session.setAttribute("LoggedInUserForm", user);
			 session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
			 
			 setupSessionGlobalVariables(user);
			 
		} catch (Exception e) {
			log.error("--> Error in showAdminDashboard <--"+e.getMessage());
		}
		  log.debug("--> Exit showAdminDashboard <--");
		  return "homepage";
	} 
	 
	 @RequestMapping(value = "/quality_manager/dashboard")
	 public String showQualityManagerDashboard(Model model, HttpSession session) {
		  log.debug("-->Enter showQualityManagerDashboard <--");
		  try {
			RAD_WS_URI = radServicesEndPoint;
			  User form = new User();
			  model.addAttribute("userForm", form);
			  model.addAttribute("menu_highlight", "home");
			 
			  session.setAttribute("SS_USER_FOLDER","quality_manager");
			 if(MAC_ID_MAP == null) {
				 setupStaticGlobalVariables();
			  }
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 String userName = auth.getName(); //get logged in username 
			 User user = userService.findByUsername(userName);
			 
			 session.setAttribute("LoggedInUserForm", user);
			 session.setAttribute("WEB_SERVICE_URL",HomeController.RAD_WS_URI);
			 setupSessionGlobalVariables(user);
		} catch (Exception e) {
			log.error("--> Error in showQualityManagerDashboard <--"+e.getMessage());
		}
		 log.debug("-->Exit showQualityManagerDashboard <--");
		  return "homepage";
	} 
	 
	 @RequestMapping(value = "/cms_user/dashboard")
	 public String showCmsUserDashboard(Model model, HttpSession session) {
		  log.debug("--> showCmsUserDashboard <--");
		  RAD_WS_URI = radServicesEndPoint;
		  User form = new User();
		  model.addAttribute("userForm", form);
		  model.addAttribute("menu_highlight", "home");
		 
		  session.setAttribute("SS_USER_FOLDER","cms_user");
		 if(MAC_ID_MAP == null) {
			 setupStaticGlobalVariables();
		  }
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String userName = auth.getName(); //get logged in username 
		 User user = userService.findByUsername(userName);
		 
		 session.setAttribute("LoggedInUserForm", user);
		 session.setAttribute("WEB_SERVICE_URL",RAD_WS_URI);
		 
		 setupSessionGlobalVariables(user);
		  return "homepage";
	} 
	 
	 @RequestMapping(value = "/quality_monitor/dashboard")
	 public String showQualityMonitorDashboard(Model model, HttpSession session) {
		  log.debug("--> showQualityMonitorDashboard <--");
		  RAD_WS_URI = radServicesEndPoint;
		  User form = new User();
		  model.addAttribute("userForm", form);
		  model.addAttribute("menu_highlight", "home");
		  
		  session.setAttribute("SS_USER_FOLDER","quality_monitor");
		 if(MAC_ID_MAP == null) {
			 setupStaticGlobalVariables();
		  }
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String userName = auth.getName(); //get logged in username 
		 User user = userService.findByUsername(userName);
		 session.setAttribute("WEB_SERVICE_URL",RAD_WS_URI);
		 session.setAttribute("LoggedInUserForm", user);
		 
		 setupSessionGlobalVariables(user);
		 return "homepage";
	} 
	 
	 @RequestMapping(value = "/mac_admin/dashboard")
	 public String showMacAdminDashboard(Model model, HttpSession session) {
		  log.debug("--> showMacAdminDashboard <--");
		  RAD_WS_URI = radServicesEndPoint;
		  User form = new User();
		  model.addAttribute("userForm", form);
		  model.addAttribute("menu_highlight", "home");
		  
		  session.setAttribute("SS_USER_FOLDER","mac_admin");
		 if(MAC_ID_MAP == null) {
			 setupStaticGlobalVariables();
		  }
		 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String userName = auth.getName(); //get logged in username 
		 User user = userService.findByUsername(userName);
		 session.setAttribute("WEB_SERVICE_URL",RAD_WS_URI);
		 session.setAttribute("LoggedInUserForm", user);
		 
		 setupSessionGlobalVariables(user);
		 return "homepage";
	} 
	 
	 
	 @RequestMapping(value = "/mac_user/dashboard")
	 public String showMacUserDashboard(Model model, HttpSession session) {
		  log.debug("--> showMacUserDashboard <--");
		  RAD_WS_URI = radServicesEndPoint;
		  User form = new User();
		  model.addAttribute("userForm", form);
		  model.addAttribute("menu_highlight", "home");
		  
		  session.setAttribute("SS_USER_FOLDER","mac_user");
		 if(MAC_ID_MAP == null) {
			 setupStaticGlobalVariables();
		  }
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 String userName = auth.getName(); //get logged in username 
		 User user = userService.findByUsername(userName);
		 
		 session.setAttribute("LoggedInUserForm", user);
		 session.setAttribute("WEB_SERVICE_URL",RAD_WS_URI);
		 setupSessionGlobalVariables(user);
		 return "homepage";
	} 
	 
	 private void setupStaticGlobalVariables() {
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
				
				HashMap<Integer,HashMap<Integer,String>> macJurisMap = new HashMap<Integer,HashMap<Integer,String>>();
				
				HashMap<String,HashMap<Integer,String>> macJurisProgramMap = new HashMap<String,HashMap<Integer,String>>();
				
				HashMap<Integer,HashMap<Integer,String>> jurisProgramMap = new HashMap<Integer,HashMap<Integer,String>>();
				
				HashMap<String,HashMap<Integer,String>> macJurisProgramLocationMap = new HashMap<String,HashMap<Integer,String>>();
				
				HashMap<Integer, MacInfo> macObjectMap = new HashMap<Integer, MacInfo>();
				
				HashMap<Integer, String> callCategoryMap = null;
				
				HashMap<Integer,HashMap<Integer,String>> callCatSubCatMap = null;
				try {
					
					RAD_WS_URI = RAD_WS_URI2;
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "pccLocationMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					pccLocationMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
					
					ALL_PCC_LOCATION_MAP = pccLocationMap;
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "programMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					programMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
					
					ALL_PROGRAM_MAP = programMap;
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "jurisdictionMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					jurisdictionMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
					
					JURISDICTION_MAP = jurisdictionMap;
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "macIdMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					macIdMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});			
					
					MAC_ID_MAP = macIdMap;
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "orgMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					ORGANIZATION_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "roleMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					ROLE_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "pccLocationMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					PCC_LOC_MAP = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});	
					
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "macList", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					macInfoList = mapper.readValue(webServiceExchange.getBody(), new TypeReference<ArrayList<MacInfo>>(){});			
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "macPrgmJurisPccList", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					macProgJurisPccMappingList = mapper.readValue(webServiceExchange.getBody(), new TypeReference<List<MacProgJurisPccMapping>>(){});
					
					
					for (MacProgJurisPccMapping macProgJurisPccMapping: macProgJurisPccMappingList) {					
										
						//MAC Jurisdiction Map Code
						String jurisName = jurisdictionMap.get(macProgJurisPccMapping.getJurisdictionId());
						HashMap<Integer,String> jurisTempMap = macJurisMap.get(macProgJurisPccMapping.getMacId());
						if(jurisTempMap == null) {
							jurisTempMap = new HashMap<Integer,String>();
							jurisTempMap.put(macProgJurisPccMapping.getJurisdictionId(),jurisName);
						} else {
							jurisTempMap.put(macProgJurisPccMapping.getJurisdictionId(),jurisName);
						}
						
						macJurisMap.put(macProgJurisPccMapping.getMacId(), jurisTempMap);
						
						//MAC Jurisdiction Program Map Code
						String programName = programMap.get(macProgJurisPccMapping.getProgramId());
						String macId_jurisId_Key = macProgJurisPccMapping.getMacId()+"_"+macProgJurisPccMapping.getJurisdictionId();
						Integer allMacjurisId_Key = macProgJurisPccMapping.getJurisdictionId();
						
						HashMap<Integer,String> programTempMap = macJurisProgramMap.get(macId_jurisId_Key);
						HashMap<Integer,String> allMacProgramTempMap = jurisProgramMap.get(allMacjurisId_Key);
						
						if(programTempMap == null) {
							programTempMap = new HashMap<Integer,String>();
							programTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
						} else {
							programTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
						}
						
						if(allMacProgramTempMap == null) {
							allMacProgramTempMap = new HashMap<Integer,String>();
							allMacProgramTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
						} else {
							allMacProgramTempMap.put(macProgJurisPccMapping.getProgramId(),programName);
						}
						
						macJurisProgramMap.put(macId_jurisId_Key, programTempMap);		
						
						jurisProgramMap.put(allMacjurisId_Key, allMacProgramTempMap);		
						
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
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "callCategoryMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					callCategoryMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,String>>(){});
					
					webServiceExchange = restTemplate.exchange(RAD_WS_URI2 + "callSubcategoriesMap", HttpMethod.GET,new HttpEntity<String>(headers), String.class);
					
					callCatSubCatMap = mapper.readValue(webServiceExchange.getBody(), new TypeReference<HashMap<Integer,HashMap<Integer,String>>>(){});
					
					CALL_CATEGORY_MAP = callCategoryMap;
					CALL_CATEGORY_SUB_CATEGORY_MAP = callCatSubCatMap;
					
					MAC_OBJECT_MAP = macObjectMap;
					MAC_JURISDICTION_MAP = macJurisMap;
					MAC_JURISDICTION_PROGRAM_MAP = macJurisProgramMap;
					JURISDICTION_PROGRAM_MAP = jurisProgramMap;
					MAC_JURISDICTION_PROGRAM_PCC_MAP = macJurisProgramLocationMap;
					
				} catch (JsonParseException e) {
					
					e.printStackTrace();
				} catch (JsonMappingException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		}
	 
	 	private void setupSessionGlobalVariables(User user) {
			
			//Mac Id Setup
			HashMap<Integer, String> userBasedMacIdMap = new HashMap<Integer, String> ();
			
			String macName = MAC_ID_MAP.get(user.getMacId().intValue());
			
			LOGGED_IN_USER_MAC_ID = user.getMacId().intValue();
			
			userBasedMacIdMap.put(user.getMacId().intValue(), macName);
			
			LOGGED_IN_USER_MAC_MAP = userBasedMacIdMap;
			
			//Jurisdiction Id Setup
			HashMap<Integer, String> userBasedJurisdictionMap = new HashMap<Integer, String> ();
			
			String[] jurisIds = user.getJurId().split(UIGenericConstants.DB_JURISDICTION_SEPERATOR);
			
			
			String jurisdictionNamesValues = "";
			String jurIdList = "";
			for (String jurisIdSingleValue: jurisIds) {
				
				String jurisdictionTempName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(jurisIdSingleValue));				
				jurisdictionNamesValues += jurisdictionTempName+ " ";
				jurIdList = jurisIdSingleValue + UIGenericConstants.UI_JURISDICTION_SEPERATOR;
				userBasedJurisdictionMap.put(Integer.valueOf(jurisIdSingleValue), jurisdictionTempName);
			}
			
			LOGGED_IN_USER_JURISDICTION_MAP = userBasedJurisdictionMap;
			LOGGED_IN_USER_JURISDICTION_IDS = jurIdList;
			LOGGED_IN_USER_JURISDICTION_NAMES =	jurisdictionNamesValues;
			
			//PCC Location Id Setup
			HashMap<Integer, String> userBasedPccLocationMap = new HashMap<Integer, String> ();
			
			String pccLocationName = PCC_LOC_MAP.get(user.getPccId().intValue());
			
			userBasedPccLocationMap.put(user.getPccId().intValue(), pccLocationName);
			
			LOGGED_IN_USER_PCC_LOCATION_MAP = userBasedPccLocationMap;
				
		}	
}