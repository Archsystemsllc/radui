/**
 * 
 */
package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;

/**
 * This is the Spring Data JPA Repository interface for data_analysis database table.
 * 
 * @author MurugarajKandaswam
 *
 */
public interface SubDataAnalysisRepository extends JpaRepository<SubDataAnalysis, Long> {

	List<SubDataAnalysis> findByDataAnalysis(DataAnalysis dataAnalysis);
	
	SubDataAnalysis findById(final int id);	

	SubDataAnalysis findBySubDataAnalysisName(String subDataAnalysisName);
	
	SubDataAnalysis findByDataAnalysisAndSubDataAnalysisName(DataAnalysis dataAnalysis, String subDataAnalysisName);
	
}
