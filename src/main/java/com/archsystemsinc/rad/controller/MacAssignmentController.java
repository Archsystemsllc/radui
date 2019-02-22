/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.beans.factory.annotation.Value;
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
import com.archsystemsinc.rad.model.ScoreCard;
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
	 
	@Value("${user1.id}")
	Integer user1Id;
	@Value("${user2.id}")
	Integer user2Id;
	@Value("${user3.id}")
	Integer user3Id;
	
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
			
			List<String> macAssignmentObjectList = mapper.convertValue(macAssignmentObjectTempList, new TypeReference<List<String> >() { });
			
			MacAssignmentObject macAssignmentObject = null;
			for (String singleObject: macAssignmentObjectList) {
				
				macAssignmentObject = new MacAssignmentObject();
				macAssignmentObject.setAssignedMonthYear(singleObject);
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
			 "/mac_admin/edit-macassignment/{monthyear}/{action}","/mac_user/edit-macassignment/{monthyear}/{action}","/quality_monitor/edit-macassignment/{monthyear}/{action}"})		
	public String viewNewMacAssignmentScreenGetMethod(@PathVariable("monthyear") final String monthYearPath,@PathVariable("action") final String action,Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewNewMacAssignmentScreenGetMethod <--");
		  		
		MacAssignmentObject macAssignmentObject = null;
		
		ArrayList<MacAssignmentObject> macAssignmentObjectList = new ArrayList<MacAssignmentObject>();
		
		
		List<User> macAssignmentListResultsMap = new ArrayList<User> ();
		
		BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
		Integer user1TempCompletedCount = 0;
		Integer user2TempCompletedCount = 0;
		Integer user3TempCompletedCount = 0;
		
		Integer user1TotalAssignedCount = 0, user2TotalAssignedCount = 0, user3TotalAssignedCount = 0;
		Integer user1TotalCompletedCount = 0, user2TotalCompletedCount = 0, user3TotalCompletedCount = 0;
		Integer macJurisdictionProgramPlanned = 0,  macJurisdictionProgramCompleted = 0, totalPlanned = 0, totalCompleted = 0;
		Date searchFromDateForCallMonitoringDate = new Date(), searchToDateForCallMonitoringDate = new Date();
		
		SimpleDateFormat myMonthYearPathFormat = new SimpleDateFormat("MM_yyyy_dd hh:mm:ss a");
		
		String macAssignmentType = "New";
	
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
			SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM_yyyy");		
			
			Date today = new Date(); 
			if(monthYearPath.equalsIgnoreCase("null")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
								
				if(dayOfMonth <= 15) {					
					cal.add(Calendar.MONTH, -1);					
				} 
				
				//Restricting From Date to 6 months from Current Date
				Calendar fromDateCalendar = Calendar.getInstance();
				fromDateCalendar.setTime(today);
				
				if(dayOfMonth <= 15) {					
					fromDateCalendar.add(Calendar.MONTH, -1);	
					searchFromDateForCallMonitoringDate = fromDateCalendar.getTime();
					searchToDateForCallMonitoringDate = today;
				} else {
					fromDateCalendar.set(Calendar.DATE, 16);
					searchFromDateForCallMonitoringDate = fromDateCalendar.getTime();
					searchToDateForCallMonitoringDate = today;
				}
				
				String currentMonthYear = monthYearFormat.format(cal.getTime());
				macAssignmentSearchObject.setAssignedMonthYear(currentMonthYear);
				model.addAttribute("currentMonthYear", currentMonthYear);
				
				macAssignmentType = "New";
			} else {
				
				
				try {
					String filterFromDateString = monthYearPath+"_01 00:00:00 AM";
					searchFromDateForCallMonitoringDate = myMonthYearPathFormat.parse(filterFromDateString);
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(searchFromDateForCallMonitoringDate);
					cal.add(Calendar.MONTH, 1);		
					cal.add(Calendar.DATE, -1);  
					
					cal.set(Calendar.HOUR, 11);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.SECOND, 59);
					cal.set(Calendar.AM_PM, Calendar.PM);
					
					searchToDateForCallMonitoringDate = cal.getTime();
					
					macAssignmentSearchObject.setAssignedMonthYear(monthYearPath);
					model.addAttribute("currentMonthYear", monthYearPath);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				macAssignmentType = "Edit";
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
					Integer macId = Integer.valueOf(stringList[0]);
					Integer jurisdictionId = Integer.valueOf(stringList[1]);
					String macName = HomeController.MAC_ID_MAP.get(macId);
					String jurisdictionName = HomeController.JURISDICTION_MAP.get(jurisdictionId);
					HashMap<Integer,String> jurisProgramMap = macJurisProgramMap.get(macJurisCode);
					
					for(Integer programId: jurisProgramMap.keySet()) {
						
						String programName = jurisProgramMap.get(programId);
						
						macAssignmentObject = new MacAssignmentObject();
						macAssignmentObject.setMacName(macName);
						macAssignmentObject.setJurisdictionName(jurisdictionName);
						macAssignmentObject.setProgramName(programName);
						macAssignmentObject.setMacId(macId);
						macAssignmentObject.setJurisdictionId(jurisdictionId);
						macAssignmentObject.setProgramId(programId);
						macAssignmentObject.setPlannedCalls("20");
						macAssignmentObject.setCreatedMethod("Auto");
						macAssignmentObject.setAssignedCallsForCindy("0");
						macAssignmentObject.setAssignedCallsForLydia("0");
						macAssignmentObject.setAssignedCallsForJaneene("0");
						
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
						
						
						if(action.equalsIgnoreCase("View")) {
							ScoreCard scoreCard;
							String ROOT_URI = new String(HomeController.RAD_WS_URI + "searchScoreCard");
							ResponseEntity<List> responseEntity;
							List<ScoreCard> scoreCardRsultsMap;
							List<ScoreCard> scoreCardList;
														
							scoreCard = new ScoreCard();
							//scoreCard.setFilterFromDateForQamStartDateTime(searchFromDateForQamStartDate);
							//scoreCard.setFilterToDateForQamStartDateTime(searchToDateForQamStartDate);
							scoreCard.setFilterFromDate(searchFromDateForCallMonitoringDate);
							scoreCard.setFilterToDate(searchToDateForCallMonitoringDate);
							
							scoreCard.setMacId(macAssignmentObjectTemp.getMacId());
							scoreCard.setJurId(macAssignmentObjectTemp.getJurisdictionId());
							scoreCard.setProgramId(macAssignmentObjectTemp.getProgramId());
							scoreCard.setScorecardType("Scoreable");
							
							//Scorecard List Count for First User
							
							
							scoreCard.setUserId(user1Id);
							responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard, List.class);
							
							scoreCardRsultsMap = responseEntity.getBody();
							scoreCardList = mapper.convertValue(scoreCardRsultsMap, new TypeReference<List<ScoreCard>>() { });
							user1TempCompletedCount = scoreCardList.size();
							if(user1TempCompletedCount >0 ) {
								//System.out.println("Test");
							}
							
							//Scorecard List Count for Second User						
							scoreCard.setUserId(user2Id);
							responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard, List.class);
							
							scoreCardRsultsMap = responseEntity.getBody();
							scoreCardList = mapper.convertValue(scoreCardRsultsMap, new TypeReference<List<ScoreCard>>() { });
							user2TempCompletedCount = scoreCardList.size();
							if(user2TempCompletedCount > 0 ) {
								//System.out.println("Test");
							}
							//Scorecard List Count for Second User
														
							scoreCard.setUserId(user3Id);
							responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, scoreCard, List.class);
							
							scoreCardRsultsMap = responseEntity.getBody();
							scoreCardList = mapper.convertValue(scoreCardRsultsMap, new TypeReference<List<ScoreCard>>() { });
							user3TempCompletedCount = scoreCardList.size();
							if(user3TempCompletedCount > 0) {
								//System.out.println("Test");
							}
							macAssignmentObjectTemp.setAssignedCallsForCindy(eachQualityMontiorCalls[0]+"("+user1TempCompletedCount+")");
							macAssignmentObjectTemp.setAssignedCallsForLydia(eachQualityMontiorCalls[1]+"("+user2TempCompletedCount+")");						
							macAssignmentObjectTemp.setAssignedCallsForJaneene(eachQualityMontiorCalls[2]+"("+user3TempCompletedCount+")");
							
							user1TotalAssignedCount += Integer.valueOf(eachQualityMontiorCalls[0]);
							user2TotalAssignedCount += Integer.valueOf(eachQualityMontiorCalls[1]);
							user3TotalAssignedCount += Integer.valueOf(eachQualityMontiorCalls[2]);
							
							user1TotalCompletedCount += user1TempCompletedCount;
							user2TotalCompletedCount += user2TempCompletedCount;
							user3TotalCompletedCount += user3TempCompletedCount;
							
							macJurisdictionProgramPlanned = Integer.valueOf(eachQualityMontiorCalls[0]) + Integer.valueOf(eachQualityMontiorCalls[1]) + Integer.valueOf(eachQualityMontiorCalls[2]); 
							macJurisdictionProgramCompleted = user1TempCompletedCount + user2TempCompletedCount + user3TempCompletedCount; 
							
							
							macAssignmentObjectTemp.setPlannedCalls(macJurisdictionProgramPlanned.toString());
							macAssignmentObjectTemp.setMacJurisdictionProgramCompleted(macJurisdictionProgramCompleted);
							totalPlanned += macJurisdictionProgramPlanned;
							totalCompleted += macJurisdictionProgramCompleted;
							macJurisdictionProgramPlanned=0;
							macJurisdictionProgramCompleted=0;
							
						} else {
							macAssignmentObjectTemp.setAssignedCallsForCindy(eachQualityMontiorCalls[0]);
							macAssignmentObjectTemp.setAssignedCallsForLydia(eachQualityMontiorCalls[1]);						
							macAssignmentObjectTemp.setAssignedCallsForJaneene(eachQualityMontiorCalls[2]);
						}
						
					} else {
						macAssignmentObjectTemp.setAssignedCallsForCindy("0");
						macAssignmentObjectTemp.setAssignedCallsForLydia("0");						
						macAssignmentObjectTemp.setAssignedCallsForJaneene("0");
					}
					
					macAssignmentMap.put(macAssignmentObjectTemp.getId(),macAssignmentObjectTemp);
				}
				session.setAttribute("SESSION_SCOPE_MAC_ASSIGNMENT_MAP", macAssignmentMap);
				model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(macAssignmentList).replaceAll("'", " "));
			}
				
			if(action.equalsIgnoreCase("View")) {
				MacAssignmentObject macAssignmentObjectTemp = new MacAssignmentObject();
				macAssignmentObjectTemp.setMacName("zTotals");
				macAssignmentObjectTemp.setJurisdictionName("");
				macAssignmentObjectTemp.setProgramName("");
			
				macAssignmentObjectTemp.setCreatedMethod("Total");
				macAssignmentObjectTemp.setAssignedCallsForCindy(user1TotalAssignedCount+"("+user1TotalCompletedCount+")");
				macAssignmentObjectTemp.setAssignedCallsForLydia(user2TotalAssignedCount+"("+user2TotalCompletedCount+")");
				macAssignmentObjectTemp.setAssignedCallsForJaneene(user3TotalAssignedCount+"("+user3TotalCompletedCount+")");
				macAssignmentObjectTemp.setPlannedCalls(totalPlanned.toString());
				macAssignmentObjectTemp.setMacJurisdictionProgramCompleted(totalCompleted);
				macAssignmentList.add(macAssignmentObjectTemp);		
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
		Integer plannedCalls = 0;
				
		String loggedInUserRole = userFormSession.getRole().getRoleName();
		MacAssignmentObject macAssignmentObject = null;
		ArrayList<MacAssignmentObject> macAssignmentObjectList = new ArrayList<MacAssignmentObject>();
		
		String currentDate = utilityFunctions.convertToStringFromDate(new Date(), UIGenericConstants.DATE_TYPE_FULL);
				
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
						//macAssignmentObject.setPlannedCalls("20");
						macAssignmentObject.setCreatedMethod("Auto");
										
				} else {
					macAssignmentObject = new MacAssignmentObject();
					macAssignmentObject.setAssignedMonthYear(assignedMonthYear);
					macAssignmentObject.setMacName(eachCellArray[0]);
					macAssignmentObject.setJurisdictionName(eachCellArray[1]);
					macAssignmentObject.setProgramName(eachCellArray[2]);
					macAssignmentObject.setMacId(Integer.valueOf(eachCellArray[7]));
					macAssignmentObject.setJurisdictionId(Integer.valueOf(eachCellArray[8]));
					macAssignmentObject.setProgramId(Integer.valueOf(eachCellArray[9]));
					//macAssignmentObject.setPlannedCalls("20");
					macAssignmentObject.setCreatedMethod("Auto");
					//macAssignmentObject.setCreatedDate(new Date());
					macAssignmentObject.setCreatedBy(userFormSession.getUserName());
				}
				
				String assignedCalls = "";
				
				if(eachCellArray.length > 4) {
					if(eachCellArray[4] != null && !eachCellArray[4].equalsIgnoreCase("") && !eachCellArray[4].equalsIgnoreCase("NoInput")) {
						if(eachCellArray[4].equalsIgnoreCase("NoInput")) {
							eachCellArray[4] = "0";
						}
						if(eachCellArray[5].equalsIgnoreCase("NoInput")) {
							eachCellArray[5] = "0";
						}
						if(eachCellArray[6].equalsIgnoreCase("NoInput")) {
							eachCellArray[6] = "0";
						}
						assignedCalls = eachCellArray[4]+","+eachCellArray[5]+","+eachCellArray[6];
						plannedCalls = Integer.valueOf(eachCellArray[4]) + Integer.valueOf(eachCellArray[5]) + Integer.valueOf(eachCellArray[6]);
					}					
				}
				
				macAssignmentObject.setAssignedCalls(assignedCalls);
				macAssignmentObject.setPlannedCalls(plannedCalls.toString());
				macAssignmentObjectList.add(macAssignmentObject);			
				
			}
			ResponseEntity<String> responseObject = basicAuthRestTemplate.postForEntity(ROOT_URI, macAssignmentObjectList,String.class);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String url = "forward:/"+userFolder+"/macassignmentlist";
		url = response.encodeRedirectURL(url);
		returnView =  url;		*/

		return "Success";
		
	}
	
	
	
}
