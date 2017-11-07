/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.model.SubDataAnalysis;
import com.archsystemsinc.pqrs.repository.SubDataAnalysisRepository;
import com.archsystemsinc.pqrs.service.SubDataAnalysisService;

/**
 * This is the implementation class of Service interface for sub_data_analysis database table.
 * @author MurugarajKandaswam
 * @version 1.1
 *
 */
@Service
public class SubDataAnalysisServiceImpl implements SubDataAnalysisService {

	@Autowired
	private SubDataAnalysisRepository subDataAnalysisRepository;
	
	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.SubDataAnalysisService#findAll()
	 */
	@Override
	public List<SubDataAnalysis> findAll() {
		return subDataAnalysisRepository.findAll();
	}

	@Override
	public List<SubDataAnalysis> findByDataAnalysis(DataAnalysis dataAnalysis) {
		return subDataAnalysisRepository.findByDataAnalysis(dataAnalysis);
	}

	@Override
	public SubDataAnalysis findById(int id) {		
		return subDataAnalysisRepository.findById(id);
	}

	@Override
	public SubDataAnalysis findByDataAnalysisAndSubDataAnalysisName(DataAnalysis dataAnalysis,
			String subDataAnalysisName) {
		return subDataAnalysisRepository.findByDataAnalysisAndSubDataAnalysisName(dataAnalysis, subDataAnalysisName);
	}

	@Override
	public SubDataAnalysis findBySubDataAnalysisName(String subDataAnalysisName) {		
		return subDataAnalysisRepository.findBySubDataAnalysisName(subDataAnalysisName);
	}
	
}
