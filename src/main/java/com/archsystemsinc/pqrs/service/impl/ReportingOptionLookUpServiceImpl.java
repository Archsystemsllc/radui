/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.repository.ReportingOptionLookupRepository;
import com.archsystemsinc.pqrs.service.ReportingOptionLookUpService;

/**
 * This is the implementation class of Service interface for reporting_option_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/21/2017
 * @version 1.1
 * 
 */
@Service
public class ReportingOptionLookUpServiceImpl implements ReportingOptionLookUpService {

	@Autowired
	private ReportingOptionLookupRepository reportingOptionLookupRepository;
	
	/* (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.ReportingOptionLookUpService#findByReportingOptionName(java.lang.String)
	 */
	@Override
	public ReportingOptionLookup findByReportingOptionName(String reportingOptionName) {
		return reportingOptionLookupRepository.findByReportingOptionName(reportingOptionName);
	}

	/* (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.ReportingOptionLookUpService#findAll()
	 */
	@Override
	public List<ReportingOptionLookup> findAll() {
		return reportingOptionLookupRepository.findAll();
	}

}
