/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.model.MeasureWisePerformanceAndReportingRate;
import com.archsystemsinc.pqrs.model.ReportingOptionLookup;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.MeasureWisePerformanceAndReportingRateRepository;
import com.archsystemsinc.pqrs.service.MeasureWisePerformanceAndReportingRateService;

/**
 * This is the implementation class of Service interface for Measure_Wise_Performance_And_Reporting_Rate database table.
 * 
 * @author Venkat
 * @since 8/27/2017
 * @version 1.1
 * 
 */
@Service
public class MeasureWisePerformanceAndReportingRateImpl implements MeasureWisePerformanceAndReportingRateService{

	@Autowired
	private MeasureWisePerformanceAndReportingRateRepository measureWisePerformanceAndReportingRateRepository;
	
	@Override
	public List<MeasureWisePerformanceAndReportingRate> findAll() {		
		return measureWisePerformanceAndReportingRateRepository.findAll();
	}

	@Override
	public MeasureWisePerformanceAndReportingRate findById(int id) {		
		return measureWisePerformanceAndReportingRateRepository.findById(id);
	}

	@Override
	public MeasureWisePerformanceAndReportingRate create(MeasureWisePerformanceAndReportingRate measureWisePerformanceAndReportingRate) {		
		return measureWisePerformanceAndReportingRateRepository.saveAndFlush(measureWisePerformanceAndReportingRate);
	}

	@Override
	public List<MeasureWisePerformanceAndReportingRate> findByDataAnalysisAndSubDataAnalysis(DataAnalysis dataAnalysis,
			SubDataAnalysis SubDataAnalysis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MeasureWisePerformanceAndReportingRate> findByMeasureLookupAndDataAnalysisAndSubDataAnalysisAndReportingOptionLookup(
			MeasureLookup measureLookup, DataAnalysis dataAnalysis, SubDataAnalysis SubDataAnalysis,
			ReportingOptionLookup reportingOptionLookup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setExclusionValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList,
			int reportingOptionId, List<Double> measure1Data, List<Double> measure2Data, List<Double> measure3Data,
			List<Double> measure4Data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setFreqValue(int dataAnalysisId, int subdataAnalysisId, List<Integer> measureLookupIdList,
			int reportingOptionId, List<Integer> measure1Data, List<Integer> measure2Data, List<Integer> measure3Data,
			List<Integer> measure4Data) {
		// TODO Auto-generated method stub
		return false;
	}

}
