/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${config.file.path}")
	String configFilePath;
	
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
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public @ResponseBody String config() {
		log.debug("<--config-" + configFilePath);
		String contents = "ERROR";
		try {
			contents = new String(Files.readAllBytes(Paths.get(configFilePath)));
			//contents = new String(Files.readAllBytes(Paths.get(getClass().getResource("/"+configFilePath).toURI())));	
		} catch (IOException e) {
			log.error("Error while reading config:" + configFilePath);
			e.printStackTrace();
		}
		log.debug("-->config-" + contents);
		return contents;

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
				Integer macIdIntegerValue = Integer.valueOf(macIdString);
				jurisFinalMap  = HomeController.MAC_JURISDICTION_MAP.get(macIdIntegerValue);
			}			
		}		
		
		return jurisFinalMap;
	}
	
	@RequestMapping(value = "/admin/selectProgram", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<Integer,String> selectProgram(@RequestParam("macId") final Integer macId,@RequestParam("jurisId") final Integer jurisId) {
		
		HashMap<Integer,String> programMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(macId+"_"+jurisId);
		return programMap;
	}
	
	
}
