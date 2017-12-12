package com.archsystemsinc.rad.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.archsystemsinc.rad.model.User;

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
		//model.addAttribute("referenceData", referenceData());
		//testRestClient();
		/* CsrUploadForm csrUploadForm = new CsrUploadForm(); */
		return "csrlist";
	}

	/*public static void main(String args[]) {
		testRestClient();
	}
	
	public  void testRestClient() {
		String plainCreds = "qamadmin:123456";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
        
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("Authorization", "Basic " + base64Creds);

		headers.set("Content-Length", "35");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> exchange = restTemplate.exchange(
				REST_SERVICE_URI + "macList", HttpMethod.GET,
				new HttpEntity<String>(headers), String.class);
		
		
		
	}
	public static final String REST_SERVICE_URI = "http://radservices.us-east-1.elasticbeanstalk.com/api/";*/
/*
	protected Map referenceData() {
		log.debug("--> Enter referenceData <--");
		Map referenceData = null;
		try {
			referenceData = new HashMap();

			ResponseEntity<MacInfo> getMacResponse = restTemplate.getForEntity(REST_SERVICE_URI + "macList",
					MacInfo.class);
			if (getMacResponse.getBody() != null) {
				System.out.println("Response for Get Request: " + getMacResponse.getBody().toString());
			} else {
				System.out.println("Response for Get Request: NULL");
			}

			ResponseEntity<JurisdictionInfo> getJuriditionResponse = restTemplate
					.getForEntity(REST_SERVICE_URI + "jurisdictionListchck csrlist controller"
							+ "", JurisdictionInfo.class);
			if (getJuriditionResponse.getBody() != null) {
				System.out.println("Response for Get Request: " + getJuriditionResponse.getBody().toString());
			} else {
				System.out.println("Response for Get Request: NULL");
			}

			
			 * referenceData.put("epMeasuresReported", epBasicInfo.getMeasuresReported());
			 * referenceData.put("epMeasuresSelectedForPSV", epMeasureSelectedString);
			 
		} catch (Exception e) {
			log.error("Error in referenceData:" + e.getMessage());
		}
		log.debug("--> Exit referenceData <--");
		return referenceData;
	}*/

}
