package com.archsystemsinc.pqrs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.service.ProviderHypothesisServiceUI;

/**
 * This is the Rest Controller Class for Bar and Line Chart Implementation for Hypothesis 1 and 2.
 * 
 * This class contains APIs for getting the data for Bar and Line Chart.
 * 1. Bar Chart API Method: gets the Percent value of five reporting options(CLAIMS, EHR, REGISTRY, GPROWI, and QCDR)
 * from the Database; sets it as the array list of value for each reporting options and returns as JSON object to client 
 * which calls this API. 
 * 2. Line Chart API Method: gets the Yes and No Percent, and Yes and No Count value for Option Year and the Reporting Option(example : CLAIMS) combination 
 * from the Database; sets it as the array list of value and returns as JSON object to client which calls this API.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 */
@RestController
@RequestMapping("/api")
public class ProviderHypothesisController {
	
	@Autowired
	private ProviderHypothesisServiceUI providerHypothesisService;

	/**
	 * 
	 * This method returns the JSON Object that has the details for Bar Chart Display.
	 * 
	 * This method gets the Yes and No Percent, and Yes and No Count value for Option Year and the Reporting Option(example : CLAIMS) combination 
	 * from the Database; sets it as the array list of value and returns as JSON object to client which calls this API.
	 * 
	 * @param dataAnalysisName
	 * @param subdataAnalysisName
	 * @param year
	 * @param reportingOption
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/barChart/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}/yearId/{yearId}/reportingOptionId/{reportingOptionId}/parameterId/{parameterId}")
    public Map barChartDisplay(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, @PathVariable("yearId") int yearId, @PathVariable("reportingOptionId") int reportingOptionId, @PathVariable("parameterId") int parameterId,HttpServletRequest request, Principal currentUser, Model model) {

		model.addAttribute("yearId", yearId);
		model.addAttribute("reportingOptionId", reportingOptionId);
		model.addAttribute("parameterId", parameterId);

		String dataAvailable = "NO";
		
		Map barChartDataMap = new HashMap();
	System.out.println("hellooo");
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookupAndParameterLookup(dataAnalysisId, subDataAnalysisId, yearId, reportingOptionId, parameterId);
		
		// Preparing Parameter String Array
		List<String> parameters = new ArrayList<String>();
		List<Double> yesPercents = new ArrayList<Double>();
		List<Double> noPercents = new ArrayList<Double>();
		List<String> yesCountValues = new ArrayList<String>();
		List<String> noCountValues = new ArrayList<String>();
		
		// Iterate over all the records returned from the Database and sets the Yes and No Percents, and Yes and No Count Value.
		for (ProviderHypothesis providerHypothesis : providerHypothesisList){
			parameters.add(providerHypothesis.getParameterLookup().getParameterName());
			yesPercents.add(providerHypothesis.getYesPercent());
			noPercents.add(providerHypothesis.getNoPercent());
			yesCountValues.add(providerHypothesis.getYesCount()+"");
			noCountValues.add(providerHypothesis.getNoCount()+"");
			dataAvailable = "YES";
		}
		System.out.println("Data for Bar Chart Data(AVAILABLE)::"+dataAvailable);
		
		// Setting barChartData in the Map to be returned back to View....
		barChartDataMap.put("parameters", parameters);
		barChartDataMap.put("yesPercents", yesPercents);
		barChartDataMap.put("noPercents", noPercents);
		barChartDataMap.put("dataAvailable", dataAvailable);
		barChartDataMap.put("yesCountValues",yesCountValues);
		barChartDataMap.put("noCountValues",noCountValues);
		
        return barChartDataMap;
    }
	
	/**
	 * 
	 * This method returns the JSON Object that has the details for Line Chart Display.
	 * 
	 * This method gets the Percent value of five reporting options(CLAIMS, EHR, REGISTRY, GPROWI, and QCDR)
	 * from the Database; sets it as the array list of value for each reporting options and returns as JSON object to client 
	 * which calls this API.
	 * 
	 * @param dataAnalysisName
	 * @param subdataAnalysisName
	 * @param parameterName
	 * @param request
	 * @param currentUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/lineChart/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}/parameterId/{parameterId}")
    public Map lineChartDisplay(@PathVariable("dataAnalysisId") int dataAnalysisId, @PathVariable("subDataAnalysisId") int subDataAnalysisId, @PathVariable("parameterId") int parameterId, HttpServletRequest request, Principal currentUser, Model model) {

		model.addAttribute("parameterId", parameterId);
		Map lineChartDataMap = new HashMap();
		String dataAvailable = "NO";
		
		final List<ProviderHypothesis> providerHypothesisList = providerHypothesisService.findByDataAnalysisAndSubDataAnalysisAndParameterLookup(dataAnalysisId, subDataAnalysisId, parameterId);
		
		List<String> uniqueYears = providerHypothesisService.getUniqueYearsForLineChart();
		List<Double> claimsPercents = new ArrayList<Double>();
		List<Double> ehrPercents = new ArrayList<Double>();
		List<Double> registryPercents = new ArrayList<Double>();
		List<Double> gprowiPercents = new ArrayList<Double>();
		List<Double> qcdrPercents = new ArrayList<Double>();
		System.out.println("Size: " + providerHypothesisList.size());
		providerHypothesisService.setRPPercentValue(providerHypothesisList, claimsPercents, ehrPercents, registryPercents, gprowiPercents, qcdrPercents);
		
		lineChartDataMap.put("uniqueYears", uniqueYears);
		lineChartDataMap.put("claimsPercents", claimsPercents);
		lineChartDataMap.put("ehrPercents", ehrPercents);
		lineChartDataMap.put("registryPercents", registryPercents);
		lineChartDataMap.put("gprowiPercents", gprowiPercents);
		lineChartDataMap.put("qcdrPercents", qcdrPercents);
		
		if (claimsPercents != null && claimsPercents.size()>0){
			for (Double claimPercent : claimsPercents) {
				if (claimPercent != null) {
					dataAvailable = "YES";
					break;
				}
			}
		}
		System.out.println("Data for Line Chart Data(AVAILABLE)::"+dataAvailable);
		lineChartDataMap.put("dataAvailable", dataAvailable);
		
		return lineChartDataMap;
    }

}