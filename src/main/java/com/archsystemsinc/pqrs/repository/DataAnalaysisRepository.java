/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;

/**
 * This is the Spring Data JPA Repository interface for data_analysis database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/23/2017
 *
 */
public interface DataAnalaysisRepository extends JpaRepository<DataAnalysis, Long> {
    
	DataAnalysis findById(final int id);
	
	DataAnalysis findByDataAnalysisName(final String dataAnalysisName);	

}
