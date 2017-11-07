package com.archsystemsinc.pqrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.model.YearLookup;

/**
 * @author Grmahun Redda
 *
 */
public interface MeasureWiseExclusionRateRepository extends JpaRepository<MeasureWiseExclusionRate, Long>{
	MeasureWiseExclusionRate findById(final int id);
	List<MeasureWiseExclusionRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysis(MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis);
}
