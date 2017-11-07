package com.archsystemsinc.pqrs.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.service.DataAnalysisService;

/**
 * This is the Spring Controller Class for the Dashboard Screen.
 * 
 * This controller class re-directs to the appropriate JSP file(i.e. either to  "adminDataAnalysis.jsp" file for Admin User or to 
 * "dataanalysis.jsp" file for Report Viewer User).
 * 
 * This Class provides the list of DataAnalysis Object which have the list of all data analysis(i.e. Hypothesis) 
 * and also its associated sub-data analysis(i.e. sub-hypothesis) from the Database Table.
 * 
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/26/2017
 */
@Controller
public class DataAnalysisController {
	
	@Autowired
	private DataAnalysisService dataAnalysisService;

	/**
	 * This is the controller method for the DataAnalysis Screen for Admin User.
	 * 
	 * This method retrieves the list of DataAnalysis Object which have the list of all data analysis(i.e. Hypothesis) 
	 * and also its associated sub-data analysis(i.e. sub-hypothesis) from the Database Table.
	 * 
	 * Associated Database Table Names : 
	 * data_analysis and sub_data_analysis.
	 * 
	 * View Page: adminDataAnalysis.jsp
	 *  
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return String
	 */
	@RequestMapping("admin/dashboard")
    public String adminDataAnalysisScreen(HttpServletRequest request, Principal currentUser, Model model) {
		
		final List<DataAnalysis> dataAnalysisList = dataAnalysisService.findAll();
		
		model.addAttribute("dataAnalysisList", dataAnalysisList);

        return "adminDataAnalysis";
    }
	
	/**
	 * This is the controller method for the DataAnalysis Screen for Report Viewer User..
	 * 
	 * This method retrieves the list of DataAnalysis Object which have the list of all data analysis(i.e. Hypothesis) 
	 * and also its associated sub-data analysis(i.e. sub-hypothesis) from the Database Table.
	 * 
	 * Associated Database Table Names : 
	 * data_analysis and sub_data_analysis.
	 * 
	 * View Page: dataanalysis.jsp
	 * 
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return String
	 */
	@RequestMapping("user/dashboard")
    public String userDataAnalysisScreen(HttpServletRequest request, Principal currentUser, Model model) {
		
		final List<DataAnalysis> dataAnalysisList = dataAnalysisService.findAll();
		
		model.addAttribute("dataAnalysisList", dataAnalysisList);

        return "dataanalysis";
    }


}