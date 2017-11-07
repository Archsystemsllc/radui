package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.ReportingOptionLookup;

/**
 * This is the Spring Data JPA Repository interface for reporting_option_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface ReportingOptionLookupRepository extends JpaRepository<ReportingOptionLookup, Long> {

	ReportingOptionLookup findById(final int id);
	
	ReportingOptionLookup findByReportingOptionName(final String reportingOptionName);
	
}
