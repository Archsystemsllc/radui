/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.DataAnalysis;
import com.archsystemsinc.pqrs.repository.DataAnalaysisRepository;
import com.archsystemsinc.pqrs.service.DataAnalysisService;
/**
 * This is the implementation class of Service interface for data_analysis database table.
 * 
 * @author Grmahun Redda
 * @since 8/23/2017
 * 
 */
@Service
public class DataAnalysisServiceImpl implements DataAnalysisService {
	
	@Autowired
	private DataAnalaysisRepository dataAnalaysisRepository;

	@Override
	public List<DataAnalysis> findAll() {
		return dataAnalaysisRepository.findAll();
	}

	@Override
	public DataAnalysis findById(int id) {
		return dataAnalaysisRepository.findById(id);
	}

	@Override
	public DataAnalysis findByDataAnalysisName(String dataAnalysisName) {
		return dataAnalaysisRepository.findByDataAnalysisName(dataAnalysisName);
	}

}
