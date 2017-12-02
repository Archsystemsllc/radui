package com.archsystemsinc.rad.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.model.CsrUploadForm;

import com.archsystemsinc.rad.model.User;

import org.springframework.ui.Model;

@Controller
public class CsrListController {
	 private static final Logger log = Logger.getLogger(CsrListController.class);
	 
	
	 /**
     * This method provides the functionalities for listing users.
     * 
     * @param 
     * @return
     */
	 
	 @RequestMapping(value = "/admin/csrlist")
	  public String viewCSRList(Model model) {
		  log.debug("--> showAdminDashboard <--");
		  User form = new User();
		  form.setId(Long.valueOf("1"));
		  model.addAttribute("userForm", form);
		  
		 /* CsrUploadForm csrUploadForm = new CsrUploadForm();*/
		  return "csrlist";
	}
	 
	 protected Map referenceData(long epId) {
			log.debug("Enter referenceData");
			Map referenceData = null;
			try {
				referenceData = new HashMap();
				String epMeasureSelectedString = "";
				
				
				
				/*referenceData.put("epMeasuresReported", epBasicInfo.getMeasuresReported());
				referenceData.put("epMeasuresSelectedForPSV", epMeasureSelectedString);*/
			} catch (Exception e) {
				log.error("Error in referenceData:" + e.getMessage());
			}
			log.debug("Exit referenceData");
			return referenceData;
		}
    
    
    
}
