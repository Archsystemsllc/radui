/**
 * 
 */
package com.archsystemsinc.rad.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;
import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.controller.HomeController;
import com.archsystemsinc.rad.model.MacAssignmentObject;
import com.archsystemsinc.rad.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Abdul Nissar, Shaik
 *
 */
@Component
public class CsrLogScheduler {
	
	private static final Logger log = Logger.getLogger(CsrLogScheduler.class);
	
	
	//@Value("${useractivity.scheduling.job.cron}")
    //public static final String RAD_WS_URI2;
	
	@Autowired
	private RadServiceApiClient radServiceApiClient;
	
	//Every 10 Seconds
	//@Scheduled(cron ="* */5 * * * *",zone="EST")
    public void updateStatus() {
        log.debug("*****************updateStatus Start*******************");
        System.out.println("Schedule Run Testing:");
        //radServiceApiClient.updateStatusForAll();
        log.debug("*****************updateStatus Done*******************");
        log.debug("--> getRebuttalList Screen <--");
		
		HashMap<Integer,MacAssignmentObject> resultsMap = new HashMap<Integer,MacAssignmentObject>();
		
		List<Object[]> macAssignmentObjectTempList = null;
		
		
		ArrayList<MacAssignmentObject> resultsList= new ArrayList<MacAssignmentObject>();
		try {			
			
			Date today = new Date(); 			
			SimpleDateFormat mdyFormat = new SimpleDateFormat("MM_yyyy");
			String currentMonthYear = mdyFormat.format(today);
			MacAssignmentObject macAssignmentSearchObject = new MacAssignmentObject();
			macAssignmentSearchObject.setAssignedMonthYear(currentMonthYear);
						
			
			BasicAuthRestTemplate basicAuthRestTemplate = new BasicAuthRestTemplate("qamadmin", "123456");
			String ROOT_URI = new String(HomeController.RAD_WS_URI + "macAssignmentList");
			ResponseEntity<List> responseEntity = basicAuthRestTemplate.postForEntity(ROOT_URI, macAssignmentSearchObject, List.class);
			ObjectMapper mapper = new ObjectMapper();
			macAssignmentObjectTempList = responseEntity.getBody();
			
			List<Object[]> macAssignmentObjectList = mapper.convertValue(macAssignmentObjectTempList, new TypeReference<List<Object[]> >() { });
			
			MacAssignmentObject macAssignmentObject = null;
			for (Object singleObject: macAssignmentObjectList) {
				Object[] singleObjectArray =  (Object[]) singleObject;
				macAssignmentObject = new MacAssignmentObject();
				macAssignmentObject.setAssignedMonthYear(singleObjectArray[0]+"_"+singleObjectArray[1]);
				resultsList.add(macAssignmentObject);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
    }
}
