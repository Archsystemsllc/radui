/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.ProviderHypothesis;

/**
 * This is the Service interface for provider_hypothesis database table.
 * 
 * @author Murugaraj Kandaswamy, Grmahun Redda
 * @since 6/21/2017
 * @version 1.1
 * 
 */
public interface ProviderHypothesisServiceUI {
	
	List<ProviderHypothesis> findByYearLookupAndReportingOptionLookup(String year, String reportingOption);
	
	List<ProviderHypothesis> findByParameterLookup(String parameterName);
	
	ProviderHypothesis create(final ProviderHypothesis providerHypothesis);	
	
	void update(final Long id);
	
	ProviderHypothesis findById(final Long id);
	
	void deleteById(final Long id);
	
	List<ProviderHypothesis> findAll();    
    
	List<ProviderHypothesis> findAllByUserId(Long id);  
	
	List<String> getUniqueYearsForLineChart();
	
	boolean setRPPercentValue(List<ProviderHypothesis> providerHypothesisList, List<Double> claimsPercents, List<Double> ehrPercents, List<Double> registryPercents, List<Double> gprowiPercents, List<Double> qcdrPercents);

//	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookup(int dataAnalysisId, int subDataAnalysisId, int yearId, int reportingOptionId);
	
	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookupAndParameterLookup(int dataAnalysisId, int subDataAnalysisId, int yearId, int reportingOptionId, int parameterId);
	
	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndParameterLookup(int dataAnalysisId, int subDataAnalysisId, int parameterId);
	
}
