/**
 * 
 */
package com.archsystemsinc.rad.controller;

import java.util.HashMap;
import java.util.Iterator;

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
	
	@RequestMapping(value ={"/admin/selectJuris", "/quality_manager/selectJuris", "/cms_user/selectJuris",
			 "/mac_admin/selectJuris","/mac_user/selectJuris"}, method = RequestMethod.GET)	
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
	
	@RequestMapping(value ={"/admin/selectProgram", "/quality_manager/selectProgram", "/cms_user/selectProgram",
			 "/mac_admin/selectProgram","/mac_user/selectProgram"}, method = RequestMethod.GET)	
	@ResponseBody
	public HashMap<Integer,String> selectProgram(@RequestParam("macId") final String macIdString,@RequestParam("jurisId") final String jurisIdString) {
		
		HashMap<Integer,String> programMap = new HashMap<Integer,String>();
		HashMap<Integer,String> programTempMap = new HashMap<Integer,String>();
		
		if(!macIdString.equalsIgnoreCase("") && !jurisIdString.equalsIgnoreCase("")) {
			if(macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL")) {
				programMap = HomeController.ALL_PROGRAM_MAP;
			} else if(macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL")) {
				String[] jurisIds = jurisIdString.split(",");
				for(String jurisSingleValue: jurisIds) {
					if(!jurisSingleValue.equalsIgnoreCase("")) {
						
						if(jurisSingleValue.equalsIgnoreCase("ALL")) {
							programMap = HomeController.ALL_PROGRAM_MAP;
							break;
						}
						Integer jurisIdIntegerValue = Integer.valueOf(jurisSingleValue);
						programTempMap = HomeController.JURISDICTION_PROGRAM_MAP.get(jurisIdIntegerValue);
						programMap.putAll(programTempMap);
						programTempMap = null;
					}				
				}
				
				
			} else if(!macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL")) {
				HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
				
				for(Object jurisKey: jurisIdMap.keySet()) {
					HashMap programMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey);
					if (programMapTemp == null) continue;
					programMap.putAll(programMapTemp);
					programMapTemp = null;
				}
				
			} else if(!macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL")) {
				String[] jurisIds = jurisIdString.split(",");
				for(String jurisSingleValue: jurisIds) {
					if(!jurisSingleValue.equalsIgnoreCase("")) {
						
						if(jurisSingleValue.equalsIgnoreCase("ALL")) {
							HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
							programMap = new HashMap<Integer,String>();
							for(Object jurisKey: jurisIdMap.keySet()) {
								programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey);
								if (programTempMap == null) continue;
								programMap.putAll(programTempMap);
								programTempMap = null;
							}
							break;
						} else {
							programTempMap = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisSingleValue));					
							programMap.putAll(programTempMap);
							programTempMap = null;
						}	
						
					}				
				}
				
				
			} 			
		}		
		
		return programMap;
	}
	
	@RequestMapping(value ={"/admin/selectLocation", "/quality_manager/selectLocation", "/cms_user/selectLocation",
			 "/mac_admin/selectLocation","/mac_user/selectLocation"}, method = RequestMethod.GET)	
	@ResponseBody
	public HashMap<Integer,String> selectLocation(@RequestParam("macId") final String macIdString,@RequestParam("jurisId") final String jurisIdString,@RequestParam("programId") final String programIdString) {
		
		HashMap<Integer,String> locationMap = new HashMap<Integer,String>();
		HashMap<Integer,String> locationTempMap = new HashMap<Integer,String>();
		
		if(!macIdString.equalsIgnoreCase("") && !jurisIdString.equalsIgnoreCase("") && !programIdString.equalsIgnoreCase("")) {
			// ALL ALL ALL
			if(macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL") && programIdString.equalsIgnoreCase("ALL")) {
				locationMap = HomeController.ALL_PCC_LOCATION_MAP;
			} 
			//Value Value Value
			else if(!macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL") && !programIdString.equalsIgnoreCase("ALL")) {
				
				
				String[] jurisIds = jurisIdString.split(",");
				for(String jurisSingleValue: jurisIds) {
					if(!jurisSingleValue.equalsIgnoreCase("")) {
						
						if(jurisSingleValue.equalsIgnoreCase("ALL")) {
							HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
							
							for(Object jurisKey: jurisIdMap.keySet()) {
								
								locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey+"_"+Integer.valueOf(programIdString));
								if (locationTempMap == null) continue;
								locationMap.putAll(locationTempMap);
								locationTempMap = null;
							}
							break;
						} else {
							locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisSingleValue)+"_"+Integer.valueOf(programIdString));
							
							locationMap.putAll(locationTempMap);
							locationTempMap = null;
						}	
						
					}				
				}
				
				
			} 
			//Value All All
			else if(!macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL") && programIdString.equalsIgnoreCase("ALL")) {
				HashMap jurisIdMapTemp = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
				locationMap = new HashMap<Integer,String>();
				
				for(Object jurisKey: jurisIdMapTemp.keySet()) {
					HashMap programMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey);
					if (programMapTemp == null) continue;
					for(Object programKey: programMapTemp.keySet()) {
						HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey+"_"+programKey);
						if (locationMapTemp == null) continue;
						locationMap.putAll(locationMapTemp);
						
					}					
					
				}			
			} 
			//ALL ALL Value
			else if(macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL") && !programIdString.equalsIgnoreCase("ALL")) {				
				HashMap macIdMap = HomeController.MAC_ID_MAP;				
				locationMap = new HashMap<Integer,String>();
				for(Object macKey: macIdMap.keySet()) {					
					HashMap jurisIdMapTemp = HomeController.MAC_JURISDICTION_MAP.get(macKey);
					if (jurisIdMapTemp == null) continue;
					for(Object jurisKey: jurisIdMapTemp.keySet()) {
						HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(macKey+"_"+jurisKey+"_"+Integer.valueOf(programIdString));
						if (locationMapTemp == null) continue;
						locationMap.putAll(locationMapTemp);
											
					}					
				}			
			} 
			//All Value All
			else if(macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL") && programIdString.equalsIgnoreCase("ALL")) {
				HashMap macIdMap = HomeController.MAC_ID_MAP;				
				locationMap = new HashMap<Integer,String>();
				for(Object macKey: macIdMap.keySet()) {	
					
					String[] jurisIds = jurisIdString.split(",");
					for(String jurisSingleValue: jurisIds) {
						if(!jurisSingleValue.equalsIgnoreCase("")) {
							
							if(jurisSingleValue.equalsIgnoreCase("ALL")) {
								HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
								
								for(Object jurisKey: jurisIdMap.keySet()) {
									
									HashMap programMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(macKey+"_"+jurisKey);	
									if (programMapTemp == null) continue;
									for(Object programKey: programMapTemp.keySet()) {
										locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(macKey+"_"+jurisKey+"_"+programKey);
										if (locationTempMap == null) continue;
										locationMap.putAll(locationTempMap);
										locationTempMap = null;
									}
								}
								break;
							} else {
								
								HashMap programMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(macKey+"_"+jurisSingleValue);	
								if (programMapTemp == null) continue;
								for(Object programKey: programMapTemp.keySet()) {
									locationTempMap = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(macKey+"_"+jurisSingleValue+"_"+programKey);									
									if (locationTempMap == null) continue;
										locationMap.putAll(locationTempMap);
									locationTempMap = null;
								}		
								
							}						
						}				
					}
						
				}
				
			} 
			//Value Value All
			else if(!macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL") && programIdString.equalsIgnoreCase("ALL")) {
				HashMap programMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisIdString));	
				locationMap = new HashMap<Integer,String>();
				
				for(Object programKey: programMapTemp.keySet()) {
					
					String[] jurisIds = jurisIdString.split(",");
					for(String jurisSingleValue: jurisIds) {
						if(!jurisSingleValue.equalsIgnoreCase("")) {
							
							if(jurisSingleValue.equalsIgnoreCase("ALL")) {
								HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));
								
								for(Object jurisKey: jurisIdMap.keySet()) {									
									
									HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey+"_"+programKey);
									if (locationMapTemp == null) continue;
									locationMap.putAll(locationMapTemp);
									
								}
								break;
							} else {
								
								HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+Integer.valueOf(jurisSingleValue)+"_"+programKey);
								if (locationMapTemp == null) continue;
								locationMap.putAll(locationMapTemp);
								
							}						
						}				
					}					
				}					
				programMapTemp = null;
			
			} 
			//Value All Value
			else if(!macIdString.equalsIgnoreCase("ALL") && jurisIdString.equalsIgnoreCase("ALL") && !programIdString.equalsIgnoreCase("ALL")) {
				HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(Integer.valueOf(macIdString));			
				locationMap = new HashMap<Integer,String>();
				
					for(Object jurisKey: jurisIdMap.keySet()) {
						HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(Integer.valueOf(macIdString)+"_"+jurisKey+"_"+Integer.valueOf(programIdString));
						if (locationMapTemp == null) continue;
						locationMap.putAll(locationMapTemp);					
					}					
						
			
			} 
			//All Value Value
			else if(macIdString.equalsIgnoreCase("ALL") && !jurisIdString.equalsIgnoreCase("ALL") && !programIdString.equalsIgnoreCase("ALL")) {
				
				HashMap macIdMap = HomeController.MAC_ID_MAP;				
				locationMap = new HashMap<Integer,String>();
				for(Object macKey: macIdMap.keySet()) {
					
					String[] jurisIds = jurisIdString.split(",");
					for(String jurisSingleValue: jurisIds) {
						if(!jurisSingleValue.equalsIgnoreCase("")) {
							
							if(jurisSingleValue.equalsIgnoreCase("ALL")) {
								HashMap jurisIdMap = HomeController.MAC_JURISDICTION_MAP.get(macKey);
								
								for(Object jurisKey: jurisIdMap.keySet()) {
									
									HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(macKey+"_"+jurisKey+"_"+Integer.valueOf(programIdString));
									if (locationMapTemp == null) continue;
										locationMap.putAll(locationMapTemp);
										locationMapTemp = null;
								}
								break;
							} else {
								
								HashMap locationMapTemp = HomeController.MAC_JURISDICTION_PROGRAM_PCC_MAP.get(macKey+"_"+Integer.valueOf(jurisSingleValue)+"_"+Integer.valueOf(programIdString));
								if (locationMapTemp == null) continue;
								locationMap.putAll(locationMapTemp);
								locationMapTemp = null;
								
							}						
						}				
					}
				}
			} 
		}		
		
		return locationMap;
	}
	
	@RequestMapping(value ={"/admin/selectCallSubcategories", "/quality_manager/selectCallSubcategories", "/cms_user/selectCallSubcategories",
			 "/mac_admin/selectCallSubcategories","/mac_user/selectCallSubcategories"}, method = RequestMethod.GET)	
	@ResponseBody
	public HashMap<Integer,String> selectCallSubCategories(@RequestParam("categoryId") final Integer categoryId) {
		
		HashMap<Integer,String> subCategorylMap = new HashMap<Integer, String>();	
		
		subCategorylMap  = HomeController.CALL_CATEGORY_SUB_CATEGORY_MAP.get(categoryId);
		return subCategorylMap;
	}
	
	
	
}
