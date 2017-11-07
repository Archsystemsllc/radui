/**
 * 
 */
package com.archsystemsinc.pqrs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.archsystemsinc.pqrs.constant.ChartNameEnum;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.ParameterLookup;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.YearLookup;
import com.archsystemsinc.pqrs.service.MeasureLookupService;
import com.archsystemsinc.pqrs.service.ParameterLookUpService;
import com.archsystemsinc.pqrs.service.ReportingOptionLookUpService;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;
import com.archsystemsinc.pqrs.service.YearLookUpService;

/**
 * This is the Spring Controller Class for ADDA Screen(Graph Display Screen) Functionality.
 * 
 * This controller class loads the drop down value data for all selection criteria from the look up tables 
 * and send it to ADDA(Graph Display) Screen.
 * 
 * Associated Database Tables:
 * Option year : year_lookup
 * Reporting Option : reporting_option_lookup
 * Parameter Name : parameter_lookup
 * 
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 */
@Controller
public class MapAndChartDisplayController {

	@Autowired
	private SubDataAnalysisService subDataAnalysisService;
	
	@Autowired
	private YearLookUpService yearLookUpService;
	
	@Autowired
	private ParameterLookUpService parameterLookUpService;
	
	@Autowired
	private ReportingOptionLookUpService reportingOptionLookUpService;	
	
	@Autowired
	private MeasureLookupService measureLookupService;
	/**
	 * 
	 * This is the controller method for Graph Display screen and it provides the information to be displayed in that screen.
	 * 
	 * This controller class loads the drop down value data for all selection criteria from the look up tables 
	 * and send it to ADDA(Graph Display) Screen.
	 * 
	 * Associated Database Tables:
	 * Option year : year_lookup
	 * Reporting Option : reporting_option_lookup
	 * Parameter Name : parameter_lookup
	 * 
	 * View Page: mapandchartdisplay.jsp
	 * 
	 * @param dataanalysis
	 * @param subdataanalysis
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return String
	 */
	@RequestMapping("/mapandchartdisplay/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}")
	public String mapAndChartScreen(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, HttpServletRequest request, Principal currentUser, Model model, final RedirectAttributes redirectAttributes) {
		
		model.addAttribute("dataAnalysisId", dataAnalysisId);
		model.addAttribute("subDataAnalysisId", subDataAnalysisId);
		
		final SubDataAnalysis subDataAnalysis = subDataAnalysisService.findById(subDataAnalysisId);
		model.addAttribute("subDataAnalysis",subDataAnalysis);
		 
		final List<YearLookup> yearLookups = yearLookUpService.findAll();			
		model.addAttribute("yearLookups", yearLookups);
		
		final List<ReportingOptionLookup> reportingOptionLookups = reportingOptionLookUpService.findAll();			
		model.addAttribute("reportingOptionLookups", reportingOptionLookups);
		
		final List<ParameterLookup> parameterLookups = parameterLookUpService.findAll();			
		model.addAttribute("parameterLookups", parameterLookups);
		
		// Gets the different type of Chart Name from the constant class: ChartNameEnum.
		List<String> reportTypes = new ArrayList<String>();
		for (ChartNameEnum chartName : ChartNameEnum.values()) {
			reportTypes.add(chartName.getChartName());
		}
		
		model.addAttribute("reportTypes", reportTypes);
		
		// View Page: mapandchartdisplay.jsp
		return "mapandchartdisplay";		
	}	
	
//	public String mapAndChartScreenHy5(int dataAnalysisId, int subDataAnalysisId) {
//		
//		model.addAttribute("dataAnalysisId", dataAnalysisId);
//		model.addAttribute("subDataAnalysisId", subDataAnalysisId);
//		
//		final SubDataAnalysis subDataAnalysis = subDataAnalysisService.findById(subDataAnalysisId);
//		model.addAttribute("subDataAnalysis",subDataAnalysis);
//		
//		final List<YearLookup> yearLookups = yearLookUpService.findAll();			
//		model.addAttribute("yearLookups", yearLookups);
//		
//		final List<MeasureLookup> measureLookups = measureLookupService.findAll();	
//		model.addAttribute("measureLookups", measureLookups);		
//		
//		final List<ReportingOptionLookup> reportingOptionLookups = reportingOptionLookUpService.findAll();			
//		model.addAttribute("reportingOptionLookups", reportingOptionLookups);
//		
//		final List<ParameterLookup> parameterLookups = parameterLookUpService.findAll();			
//		model.addAttribute("parameterLookups", parameterLookups);
//		
//		List<String> reportTypes = new ArrayList<String>();
//		for (ChartNameEnum chartName : ChartNameEnum.values()) {
//			reportTypes.add(chartName.getChartName());
//		}
//		
//		model.addAttribute("reportTypes", reportTypes);
//		
//		//angular page
//		return "hy5display";
//		
//	}

	@RequestMapping("/measures/mapandchartdisplay/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}")
	public String mapAndChartScreenHy5(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, HttpServletRequest request, Principal currentUser, Model model ) {		
		
		model.addAttribute("dataAnalysisId", dataAnalysisId);
		model.addAttribute("subDataAnalysisId", subDataAnalysisId);
		
		final SubDataAnalysis subDataAnalysis = subDataAnalysisService.findById(subDataAnalysisId);
		model.addAttribute("subDataAnalysis",subDataAnalysis);
		 
		final List<YearLookup> yearLookups = yearLookUpService.findAll();			
		model.addAttribute("yearLookups", yearLookups);
		
		final List<MeasureLookup> measureLookups = measureLookupService.findAll();			
		model.addAttribute("measureLookups", measureLookups);
		
		if(dataAnalysisId == 4 || dataAnalysisId == 5){
			final List<ReportingOptionLookup> reportingOptionLookups = reportingOptionLookUpService.findAll();			
			model.addAttribute("reportingOptionLookups", reportingOptionLookups);
		}
		
		// Gets the different type of Chart Name from the constant class: ChartNameEnum.
		List<String> reportTypes = new ArrayList<String>();
		for (ChartNameEnum chartName : ChartNameEnum.values()) {
			reportTypes.add(chartName.getChartName());
		}
		
		model.addAttribute("reportTypes", reportTypes);		
		// View Page: mapandchartdisplay.jsp
		return "mapandchartdisplay_hy5";		
	}
	
	@RequestMapping("/exclusion/mapandchartdisplay/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}")
	public String mapAndChartScreenHy3(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, HttpServletRequest request, Principal currentUser, Model model, final RedirectAttributes redirectAttributes) {
		
		model.addAttribute("dataAnalysisId", dataAnalysisId);
		model.addAttribute("subDataAnalysisId", subDataAnalysisId);
		
		final SubDataAnalysis subDataAnalysis = subDataAnalysisService.findById(subDataAnalysisId);
		model.addAttribute("subDataAnalysis",subDataAnalysis);
		 
		final List<YearLookup> yearLookups = yearLookUpService.findAll();			
		model.addAttribute("yearLookups", yearLookups);
		
		final List<ReportingOptionLookup> reportingOptionLookups = reportingOptionLookUpService.findAll();			
		model.addAttribute("reportingOptionLookups", reportingOptionLookups);
		
		final List<ParameterLookup> parameterLookups = parameterLookUpService.findAll();			
		model.addAttribute("parameterLookups", parameterLookups);
		
		// Gets the different type of Chart Name from the constant class: ChartNameEnum.
		List<String> reportTypes = new ArrayList<String>();
		for (ChartNameEnum chartName : ChartNameEnum.values()) {
			reportTypes.add(chartName.getChartName());
		}
		
		model.addAttribute("reportTypes", reportTypes);
		
		// View Page: mapandchartdisplay.jsp
		return "mapandchartdisplay_hy3";		
	}
}
