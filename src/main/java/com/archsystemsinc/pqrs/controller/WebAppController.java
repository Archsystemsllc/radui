package com.archsystemsinc.pqrs.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.pqrs.configuration.ReferenceDataLoader;

/**
 * 
 * @author lekan reju
 * 
 * This is the web controller for the map component. Receives request and loads the map.
 * Also generates custom data in JSP models based on the input.
 * 
 * updated 
 * @author Murugaraj Kandaswamy
 * @since 7/5/2017
 *
 */
@Controller
public class WebAppController {
	
	/**
	 * This is the controller method that sets different attribute values required for the States View Page.
	 * 
	 * @param epOrGpro
	 * @param ruralOrUrban
	 * @param yesOrNoOption
	 * @param yearId
	 * @param reportingOptionId
	 * @param model
	 * @return
	 */
	@RequestMapping("/maps/epOrGpro/{epOrGpro}/ruralOrUrban/{ruralOrUrban}/yesOrNoOption/{yesOrNoOption}/yearId/{yearId}/reportingOptionId/{reportingOptionId}/dataAnalysisId/{dataAnalysisId}/subDataAnalysisId/{subDataAnalysisId}")
    public String states(@PathVariable("epOrGpro") Integer epOrGpro, 
    					 @PathVariable("ruralOrUrban") Integer ruralOrUrban, 
    					 @PathVariable("yesOrNoOption") Integer yesOrNoOption, 
    					 @PathVariable("yearId") Integer yearId,
    					 @PathVariable("reportingOptionId") Integer reportingOptionId, 
    					 @PathVariable("dataAnalysisId") Integer dataAnalysisId, 
    					 @PathVariable("subDataAnalysisId") Integer subDataAnalysisId, Model model, HttpServletResponse response) {
		response.setHeader("X-Frame-Options" ,"SAMEORIGIN");
		String hoverTitle = "<h4>US State Map</h4>";
		String attribute = ReferenceDataLoader.referenceData.get("reportingOptions").get(reportingOptionId);
		model.addAttribute("attribute", attribute);
		Map<Object, String> mmap = new TreeMap<Object, String>(Collections.reverseOrder());
		// this needs to be revisited. need to calculate this based on input values.
		mmap.put(4500, "'#6F4242'"); 
		mmap.put(4000, "'#8B3A3A'");
		mmap.put(3500, "'#800026'");
		mmap.put(3000, "'#BD0026'");
		mmap.put(2500, "'#E31A1C'");
		mmap.put(2000, "'#FC4E2A'");
		mmap.put(1500, "'#FD8D3C'");
		mmap.put(1000, "'#FEB24C'");
		mmap.put(500,  "'#FED976'");
		mmap.put(0,	 "'#FFEDA0'");
		
		model.addAttribute("dataAnalysisId", dataAnalysisId);
		model.addAttribute("subDataAnalysisId", subDataAnalysisId);
		
		model.addAttribute("yearId", yearId);
		model.addAttribute("reportingOptionId", reportingOptionId);
		model.addAttribute("epOrGpro", epOrGpro);
		model.addAttribute("yesOrNoOption", yesOrNoOption);
		model.addAttribute("ruralOrUrban", ruralOrUrban);
		model.addAttribute("hoverTitle", hoverTitle);
		
		model.addAttribute("hoverEPOrGro", (epOrGpro==1?"EP":(epOrGpro==2?"GPro":"All")));
		model.addAttribute("hoverRuralOrUrban", (ruralOrUrban==3?"Rural": "Urban"));
		model.addAttribute("hoverYesOrNoOption", (yesOrNoOption==4?"Yes": "No"));
		model.addAttribute("hoverReportingOption", attribute);
		model.addAttribute("hoverStateKeyName", "props.State");
		model.addAttribute("hoverStateValueName", "props."+attribute);
		
		model.addAttribute("mapPropertyKey", "feature.properties." + attribute);
		model.addAttribute("legendKeys", mmap.keySet());
		List<String> colors = new ArrayList<String>();
		for(String string: mmap.values()) colors.add(string);
		model.addAttribute("legendValues", colors);
        return "states";
    }
	
}
