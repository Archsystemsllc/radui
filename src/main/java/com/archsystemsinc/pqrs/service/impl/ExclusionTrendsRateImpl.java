/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.ExclusionTrendsRate;
import com.archsystemsinc.pqrs.repository.ExclusionTrendsRateRepository;
import com.archsystemsinc.pqrs.service.ExclusionTrendsRateService;

/**
 * This is the implementation class of Service interface for exclusion_trends database table.
 * 
 * @author Venkat
 * @since 8/23/2017
 * @version 1.1
 * 
 */
@Service
public class ExclusionTrendsRateImpl implements ExclusionTrendsRateService{

	@Autowired
	private ExclusionTrendsRateRepository exclusionTrendsRateRepository;
	
	@Override
	public List<ExclusionTrendsRate> findAll() {		
		return exclusionTrendsRateRepository.findAll();
	}

	@Override
	public ExclusionTrendsRate findById(int id) {		
		return exclusionTrendsRateRepository.findById(id);
	}

	@Override
	public ExclusionTrendsRate create(ExclusionTrendsRate exclusionTrendsRate) {		
		return exclusionTrendsRateRepository.saveAndFlush(exclusionTrendsRate);
	}

}
