/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author Q
 *
 */
@Controller
public class CommonController {
	
	private static final Logger log = Logger.getLogger(CommonController.class);
	
	@RequestMapping("/home")
	public String home(){
		return "welcome";
	}
	@RequestMapping(value="/reportingHome", method = RequestMethod.GET)
	public String reportingHome(){
		log.debug("<- reportingHome ->");
		return "reporting_home";
	}	
	
	
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public String admin(){
		log.debug("<- admin ->");
		return "admin";
	}
	
	@RequestMapping(value="/admin/jqueryform_validation_example")
	public String jqueryExample()
	{
		return "jqueryform_validation_example";
		
	}
	
	@RequestMapping(value="/admin/contactus")
	public String contactUs()
	{
		return "contactUs";
		
	}
	
	
	@RequestMapping(value="/admin/aboutus")
	public String aboutUs()
	{
		return "aboutUs";
		
	}
	
	@RequestMapping(value = "/admin/selectJuris", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Integer,String> selectJuris(@RequestParam("macId") final String macIdString, @RequestParam("multipleInput") final boolean multipleInputFlag) {
		
		HashMap<Integer,String> jurisFinalMap = new HashMap<Integer, String>();		
		
		if(multipleInputFlag) {
			String[] macIds = macIdString.split(",");
			for(String macIdSingleValue: macIds) {
				if(!macIdSingleValue.equalsIgnoreCase("")) {
					
					if(macIdSingleValue.equalsIgnoreCase("ALL")) {
						jurisFinalMap = HomeController.JURISDICTION_MAP;
						break;
					}
					Integer macIdIntegerValue = Integer.valueOf(macIdSingleValue);
					HashMap<Integer,String> jurisMap = HomeController.MAC_JURISDICTION_MAP.get(macIdIntegerValue);
					jurisFinalMap.putAll(jurisMap);
				}				
			}
		} else {
			if(!macIdString.equalsIgnoreCase("")) {
				if(macIdString.equalsIgnoreCase("ALL")) {
					jurisFinalMap = HomeController.JURISDICTION_MAP;
					
				} else {
					Integer macIdIntegerValue = Integer.valueOf(macIdString);
					jurisFinalMap  = HomeController.MAC_JURISDICTION_MAP.get(macIdIntegerValue);
				}
				
			}			
		}		
		
		return jurisFinalMap;
	}
	
	@RequestMapping(value = "/admin/selectProgram", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Integer,String> selectProgram(@RequestParam("macId") final String macIdString,@RequestParam("jurisId") final String jurisIdString) {
		
		HashMap<Integer,String> programMap = new HashMap<Integer,String>();
		
		if(!macIdString.equalsIgnoreCase("") && !jurisIdString.equalsIgnoreCase("")) {
			if(macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL")) {
				programMap = HomeController.ALL_PROGRAM_MAP;
			} else if(macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL")) {
				programMap = HomeController.JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(jurisIdString));
			} else if(!macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL")) {
				programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisIdString));
			} 			
		}		
		
		return programMap;
	}
	
	@RequestMapping(value = "/admin/selectLocation", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Integer,String> selectLocation(@RequestParam("macId") final String macIdString,@RequestParam("jurisId") final String jurisIdString,@RequestParam("programId") final String programIdString) {
		
		HashMap<Integer,String> locationMap = new HashMap<Integer,String>();
		
		if(!macIdString.equalsIgnoreCase("") && !jurisIdString.equalsIgnoreCase("") && !programIdString.equalsIgnoreCase("")) {
			if(macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL") && programIdString.equalsIgnoreCase("ALL")) {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			} else if(!macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL") && !programIdString.equalsIgnoreCase("ALL")) {
				locationMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisIdString));
			} else {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			}
		}		
		
		return locationMap;
	}
	
	@RequestMapping(value = "/admin/selectCallSubcategories", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Integer,String> selectCallSubCategories(@RequestParam("categoryId") final Integer categoryId) {
		
		HashMap<Integer,String> subCategorylMap = new HashMap<Integer, String>();	
		
		subCategorylMap  = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(categoryId);
		return subCategorylMap;
	}
	
	
	
}
