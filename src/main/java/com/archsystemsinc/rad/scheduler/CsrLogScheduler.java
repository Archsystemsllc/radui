/**
 * 
 */
package com.archsystemsinc.rad.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;

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
	//@Scheduled(cron ="*/10 * * * * *",zone="EST")
    public void updateStatus() {
        log.debug("*****************updateStatus Start*******************");
        System.out.println("Schedule Run Testing:");
        //radServiceApiClient.updateStatusForAll();
        log.debug("*****************updateStatus Done*******************");
    }
}
