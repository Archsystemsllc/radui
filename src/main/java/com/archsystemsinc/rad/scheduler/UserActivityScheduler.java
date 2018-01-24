/**
 * 
 */
package com.archsystemsinc.rad.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;

/**
 * @author Prakasa
 *
 */
@Component
public class UserActivityScheduler {
	
	private static final Logger log = Logger.getLogger(UserActivityScheduler.class);
	
	
	@Autowired
	private RadServiceApiClient radServiceApiClient;
	
	@Scheduled(cron ="${useractivity.scheduling.job.cron}",zone="EST")
    public void updateStatus() {
        log.debug("*****************updateStatus Start*******************");
        radServiceApiClient.updateStatusForAll();
        log.debug("*****************updateStatus Done*******************");
    }
}
