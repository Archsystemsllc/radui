package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.ParameterLookup;
import com.archsystemsinc.pqrs.model.ProviderHypothesis;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.YearLookup;

/**
 * This is the Spring Data JPA Repository interface for provider_Hypothesis database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface ProviderHypothesisUIRepository extends JpaRepository<ProviderHypothesis, Long>{

	/**
	 * 
	 * @param yearLookup
	 * @param reportingOptionLookup
	 * @return
	 */
	List<ProviderHypothesis> findByYearLookupAndReportingOptionLookup(YearLookup yearLookup, ReportingOptionLookup reportingOptionLookup);
	
	/**
	 * 
	 * @param dataAnalysis
	 * @param subDataAnalysis
	 * @param yearLookup
	 * @param reportingOptionLookup
	 * @return
	 */
	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndYearLookupAndReportingOptionLookupAndParameterLookup(DataAnalysis dataAnalysis, SubDataAnalysis subDataAnalysis, YearLookup yearLookup, ReportingOptionLookup reportingOptionLookup, ParameterLookup parameterLookup);
	
	/**
	 * 
	 * @param parameterLookup
	 * @return
	 */
	List<ProviderHypothesis> findByParameterLookup(ParameterLookup parameterLookup);
	
	/**
	 * 
	 * @param dataAnalysis
	 * @param subDataAnalysis
	 * @param parameterLookup
	 * @return
	 */
	List<ProviderHypothesis> findByDataAnalysisAndSubDataAnalysisAndParameterLookup(DataAnalysis dataAnalysis, SubDataAnalysis subDataAnalysis, ParameterLookup parameterLookup);
	
}
