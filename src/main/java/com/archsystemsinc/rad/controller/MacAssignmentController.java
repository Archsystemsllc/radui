/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.archsystemsinc.rad.model.QamMacByJurisdictionReviewReport;
import com.archsystemsinc.rad.model.ReportsForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AbdulNissar Shaik
 *
 */
@Controller
public class MacAssignmentController {

	private static final Logger log = Logger.getLogger(MacAssignmentController.class);
	 @RequestMapping(value ={"/admin/macassignment", "/quality_manager/macassignment", "/cms_user/macassignment",
			 "/mac_admin/macassignment","/mac_user/macassignment","/quality_monitor/macassignment"}, method = RequestMethod.GET)		
	public String viewMacAssignmentScreenGetMethod(Model model, HttpSession session, Authentication authentication) {
		log.debug("--> viewMacAssignmentScreenGetMethod <--");
		  
		HashMap<String,HashMap<Integer,String>> macJurisProgramMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP;
		ReportsForm reportsForm = new ReportsForm();
		QamMacByJurisdictionReviewReport qamMacByJurisdictionReviewReport = null;
		
		ArrayList<QamMacByJurisdictionReviewReport> qamMacJurisProgramList = new ArrayList<QamMacByJurisdictionReviewReport>();
		
		Map<String, QamMacByJurisdictionReviewReport> finalSortedMap = new TreeMap<String, QamMacByJurisdictionReviewReport>(
				new Comparator<String>() {

					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}

				});
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(String macJurisCode : macJurisProgramMap.keySet()) {
			
			String[] stringList = macJurisCode.split("_");
			String macName = HomeController.MAC_ID_MAP.get(Integer.valueOf(stringList[0]));
			String jurisdictionName = HomeController.JURISDICTION_MAP.get(Integer.valueOf(stringList[1]));
			HashMap<Integer,String> jurisProgramMap = macJurisProgramMap.get(macJurisCode);
			
			for(Integer programId: jurisProgramMap.keySet()) {
				
				String programName = jurisProgramMap.get(programId);
				
				qamMacByJurisdictionReviewReport = new QamMacByJurisdictionReviewReport();
				qamMacByJurisdictionReviewReport.setMacName(macName);
				qamMacByJurisdictionReviewReport.setJurisdictionName(jurisdictionName);
				qamMacByJurisdictionReviewReport.setProgram(programName);
				qamMacByJurisdictionReviewReport.setPlannedCalls(20);
				qamMacByJurisdictionReviewReport.setCreatedMethod("Auto");
				qamMacByJurisdictionReviewReport.setAssignedCalls(6);
				
				qamMacJurisProgramList.add(qamMacByJurisdictionReviewReport);
			}
		}
		model.addAttribute("reportsForm", reportsForm);
		
		
		try {
			model.addAttribute("MAC_ASSIGNMENT_REPORT",mapper.writeValueAsString(qamMacJurisProgramList).replaceAll("'", " "));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
					
		return "mac_assignment";
	}
	
	
}
