/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.ReportingOptionLookup;

/**
 * This is the Service interface for reporting_option_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/21/2017
 * @version 1.1
 * 
 */
public interface ReportingOptionLookUpService {

	ReportingOptionLookup findByReportingOptionName(final String reportingOptionName);
	
	List<ReportingOptionLookup> findAll();
	
}
