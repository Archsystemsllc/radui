/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

import com.archsystemsinc.rad.common.utils.UIGenericConstants;
import com.archsystemsinc.rad.common.utils.UtilityFunctions;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.model.MacAssignmentObject;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class MacAssignmentController {

	private static final Logger log = Logger.getLogger(MacAssignmentController.class);
	
	 @Autowired
	 UtilityFunctions utilityFunctions;
	
	@RequestMapping(value ={"/admin/macassignmentlist", "/quality_manager/macassignmentlist", "/cms_user/macassignmentlist",
			 "/mac_admin/macassignmentlist","/mac_user/macassignmentlist","/quality_monitor/macassignmentlist"})		
	public String getMacAssignmentList(final Model model,HttpServletRequest request, Authentication authentication,HttpServletResponse response) {
		log.debug("--> getRebuttalList Screen <--");
		
		HashMap<Integer,MacAssignmentObject> resultsMap = new HashMap<Integer,MacAssignmentObject>();
		
		List<Object[]> macAssignmentObjectTempList = null;
		
		
		ArrayList<MacAssignmentObject> resultsList= new ArrayList<MacAssignmentObject>();
		try {
			
			User userFormFromSession = (User) request.getSession().getAttribute("LoggedInUserForm");
			
			
									
			String roles = authentication.getAuthorities().toString();
			Date today = new Date(); 			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM_yyyy");
			String currentMonthYear = mdyFormat.format(today);
			MacAssignmentObject macAssignmentSearchObject = new MacAssignmentObject();
			macAssignmentSearchObject.setAssignedMonthYear(currentMonthYear);
						
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "macAssignmentList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, macAssignmentSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			macAssignmentObjectTempList = responseEntity.getBody();
			
			List<Object[]> macAssignmentObjectList = mapper.convertValue(macAssignmentObjectTempList, new TypeReference<List<Object[]> >() { });
			
			MacAssignmentObject macAssignmentObject = null;
			for (Object singleObject: macAssignmentObjectList) {
				Object[] singleObjectArray =  (Object[]) singleObject;
				macAssignmentObject = new MacAssignmentObject();
				macAssignmentObject.setAssignedMonthYear(singleObjectArray[0]+"_"+singleObjectArray[1]);
				resultsList.add(macAssignmentObject);
			}
			
			model.addAttribute("macAssignmentObjectList",mapper.writeValueAsString(resultsList).replaceAll("'", " "));	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "macassignmentlist";
	}
	
	@RequestMapping(value ={"/admin/edit-macassignment/{monthyear}/{action}", "/quality_manager/edit-macassignment/{monthyear}/{action}", "/cms_user/edit-macassignment/{monthyear}/{action}",
			 "/mac_admin/edit-macassignment/{monthyear}/{action}","/mac_user/edit-macassignment/{monthyear}/{action}","/quality_monitor/edit-macassignment/{monthyear}/{action}"}, method = RequestMethod.GET)		
	public String viewNewMacAssignmentScreenGetMethod(@PathVariable("monthyear") final String monthYearPath,@PathVariable("action") final String action,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewMacAssignmentScreenGetMethod <--");
		  
		
		
		MacAssignmentObject macAssignmentObject = null;
		
		ArrayList<MacAssignmentObject> macAssignmentObjectList = new ArrayList<MacAssignmentObject>();
		
		Date today = new Date(); 
		List<User> macAssignmentListResultsMap = new ArrayList<User> ();
		
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
	
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, MacAssignmentObject> finalSortedMap = new TreeMap<String, MacAssignmentObject>(
					new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}

			});
			MacAssignmentObject macAssignmentSearchObject = new MacAssignmentObject();
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM_yyyy");
			if(monthYearPath.equalsIgnoreCase("null")) {
				
				String currentMonthYear = mdyFormat.format(today);
				macAssignmentSearchObject.setAssignedMonthYear(currentMonthYear);
				model.addAttribute("currentMonthYear", currentMonthYear);
			} else {
				
				SimpleDateFormat mydFormat = new SimpleDateFormat("MMM_yyyy_dd");
				try {
					Date monthDate = mydFormat.parse(monthYearPath+"_01");
					String currentMonthYear = mdyFormat.format(monthDate);
					macAssignmentSearchObject.setAssignedMonthYear(currentMonthYear);
					model.addAttribute("currentMonthYear", currentMonthYear);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			model.addAttribute("action", action);
			
			
			String ROOT_URI_MAC_ASSIGNMENT = new String(HomeController.RAD_WS_URI + "macAssignmentWithMonthYear");
			ResponseEntity<List> macAssignmentResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_MAC_ASSIGNMENT, macAssignmentSearchObject, List.class);
			
			macAssignmentListResultsMap = macAssignmentResponseEntity.getBody();
			List<MacAssignmentObject> macAssignmentList = mapper.convertValue(macAssignmentListResultsMap, new TypeReference<List<MacAssignmentObject>>() { });
			
			if(macAssignmentList == null || macAssignmentList.size() == 0 ) {
				HashMap<String,HashMap<Integer,String>> macJurisProgramMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP;
				for(String macJurisCode : macJurisProgramMap.keySet()) {
					
					String[] stringList = macJurisCode.split("_");
					String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(stringList[0]));
					String jurisdictionName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(stringList[1]));
					HashMap<Integer,String> jurisProgramMap = macJurisProgramMap.get(macJurisCode);
					
					for(Integer programId: jurisProgramMap.keySet()) {
						
						String programName = jurisProgramMap.get(programId);
						
						macAssignmentObject = new MacAssignmentObject();
						macAssignmentObject.setMacName(macName);
						macAssignmentObject.setJurisdictionName(jurisdictionName);
						macAssignmentObject.setProgramName(programName);
						macAssignmentObject.setPlannedCalls("20");
						macAssignmentObject.setCreatedMethod("Auto");
						macAssignmentObject.setAssignedCallsForCindy(0);
						macAssignmentObject.setAssignedCallsForLydia(0);
						macAssignmentObject.setAssignedCallsForKelly(0);
						macAssignmentObject.setAssignedCallsForJaneene(0);
						
						macAssignmentObjectList.add(macAssignmentObject);
						
					}
				}
				model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(macAssignmentObjectList).replaceAll("'", " "));
			} else {
				HashMap<Integer, MacAssignmentObject> macAssignmentMap = new HashMap<Integer, MacAssignmentObject>();
				for(MacAssignmentObject macAssignmentObjectTemp: macAssignmentList) {
					String assignedCalls = macAssignmentObjectTemp.getAssignedCalls();
					String eachQualityMontiorCalls[] = assignedCalls.split(",");
					if(eachQualityMontiorCalls.length > 1) {
						macAssignmentObjectTemp.setAssignedCallsForCindy(Integer.valueOf(eachQualityMontiorCalls[0]));
						macAssignmentObjectTemp.setAssignedCallsForLydia(Integer.valueOf(eachQualityMontiorCalls[1]));
						macAssignmentObjectTemp.setAssignedCallsForKelly(Integer.valueOf(eachQualityMontiorCalls[2]));
						macAssignmentObjectTemp.setAssignedCallsForJaneene(Integer.valueOf(eachQualityMontiorCalls[3]));
					} else {
						macAssignmentObjectTemp.setAssignedCallsForCindy(Integer.valueOf(eachQualityMontiorCalls[0]));
						macAssignmentObjectTemp.setAssignedCallsForLydia(0);
						macAssignmentObjectTemp.setAssignedCallsForKelly(0);
						macAssignmentObjectTemp.setAssignedCallsForJaneene(0);
					}
					
					macAssignmentMap.put(macAssignmentObjectTemp.getId(),macAssignmentObjectTemp);
				}
				session.setAttribute("SESSION_SCOPE_MAC_ASSIGNMENT_MAP", macAssignmentMap);
				model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(macAssignmentList).replaceAll("'", " "));
			}
			
			model.addAttribute("macAssignmentObjectForm", macAssignmentObject);
			
			String ROOT_URI_USER_SEARCH = new String(HomeController.RAD_WS_URI + "searchUsers");
			
			User userSearchObject = new User();
			
			List<User> userListResultsMap = new ArrayList<User> ();
			userSearchObject.setStatus(UIGenericConstants.RECORD_STATUS_ACTIVE);
			userSearchObject.setRoleString(UIGenericConstants.QUALITY_MONITOR_ROLE);
			ResponseEntity<List> userListResponseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI_USER_SEARCH, userSearchObject, List.class);
			
			userListResultsMap = userListResponseEntity.getBody();
			List<User> userList = mapper.convertValue(userListResultsMap, new TypeReference<List<User>>() { });
			HashMap<Integer,String> pccContactPersonMap = new HashMap<Integer,String>();
			for(User userTemp: userList) {
				pccContactPersonMap.put(userTemp.getId().intValue(), userTemp.getLastName()+" "+userTemp.getFirstName());
			}
			
			
			model.addAttribute("pccContactPersonMap",mapper.writeValueAsString(pccContactPersonMap).replaceAll("'", " "));
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
					
		return "mac_assignment";
	}
	
	@RequestMapping(value ={"/admin/save-macassignmentlist", "/quality_manager/save-macassignmentlist", "/cms_user/save-macassignmentlist",
			 "/mac_admin/save-macassignmentlist","/mac_user/save-macassignmentlist","/quality_monitor/save-macassignmentlist"}, method = RequestMethod.GET)	
	public String saveMacAssignmentScreenGetMethod(@RequestParam("monthNumber") final String assignedMonthYear, @RequestParam("finalDataSet[]") String[] saveDataSet,final Model model, Authentication authentication, HttpSession session, HttpServletResponse response) {
		log.debug("--> saveMacAssignmentScreenGetMethod <--");
		  
		String returnView = "";
		log.debug("--> saveMacAssignmentScreenGetMethod <--");
		HashMap<Integer,String> programMap = new HashMap<Integer, String> ();
		ArrayList<Integer> jurIdArrayList = new ArrayList<Integer> ();
		
		String roles = authentication.getAuthorities().toString();
		User userFormSession = (User) session.getAttribute("LoggedInUserForm");
		String userFolder = (String) session.getAttribute("SS_USER_FOLDER"); 
		model.addAttribute("menu_highlight", "scorecard");
		
				
		String loggedInUserRole = userFormSession.getRole().getRoleName();
		MacAssignmentObject macAssignmentObject = null;
		ArrayList<MacAssignmentObject> macAssignmentObjectList = new ArrayList<MacAssignmentObject>();
		
		String currentDate = utilityFunctions.convertToStringFromDate(new Date());
				
		try {
			HashMap<Integer, MacAssignmentObject> macAssignmentMap=(HashMap<Integer, MacAssignmentObject>) session.getAttribute("SESSION_SCOPE_MAC_ASSIGNMENT_MAP");
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "saveOrUpdateMacAssignment");
						
			for(String eachRow: saveDataSet) {
				String[] eachCellArray = eachRow.split(",");
				
				
				if(eachCellArray[3] != null && !eachCellArray[3].equalsIgnoreCase("") && !eachCellArray[3].equalsIgnoreCase("NoId")) {
						Integer idValue = Integer.valueOf(eachCellArray[3]);
						macAssignmentObject = macAssignmentMap.get(idValue);
						//macAssignmentObject.setUpdatedDate(new Date());
						macAssignmentObject.setUpdatedBy(userFormSession.getUserName());
						macAssignmentObject.setPlannedCalls("20");
						macAssignmentObject.setCreatedMethod("Auto");
										
				} else {
					macAssignmentObject = new MacAssignmentObject();
					macAssignmentObject.setAssignedMonthYear(assignedMonthYear);
					macAssignmentObject.setMacName(eachCellArray[0]);
					macAssignmentObject.setJurisdictionName(eachCellArray[1]);
					macAssignmentObject.setProgramName(eachCellArray[2]);
					macAssignmentObject.setPlannedCalls("20");
					macAssignmentObject.setCreatedMethod("Auto");
					//macAssignmentObject.setCreatedDate(new Date());
					macAssignmentObject.setCreatedBy(userFormSession.getUserName());
				}
				
				String assignedCalls = "";
				
				if(eachCellArray.length > 4) {
					if(eachCellArray[4] != null && !eachCellArray[4].equalsIgnoreCase("") && !eachCellArray[4].equalsIgnoreCase("NoInput")) {
						assignedCalls += eachCellArray[4]+","+eachCellArray[5]+","+eachCellArray[6]+","+eachCellArray[7];
						
					}					
				}
				
				macAssignmentObject.setAssignedCalls(assignedCalls);
				
				macAssignmentObjectList.add(macAssignmentObject);			
				
			}
			ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, macAssignmentObjectList,String.class);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "redirect:/"+userFolder+"/macassignmentlist";
		url = response.encodeRedirectURL(url);
		returnView =  url;		

		return returnView;
		
	}
	
	
	
}
