/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;
import com.archsystemsinc.pqrs.repository.MeasureWiseExclusionRateRepository;
import com.archsystemsinc.pqrs.service.MeasureWiseExclusionRateService;

/**
 * This is the implementation class of Service interface for measure_wise_exclusion_rate database table.
 * 
 * @author Grmahun Redda
 * @version 1.1
 * 
 */
@Service
public class MeasureWiseExclusionRateImpl implements MeasureWiseExclusionRateService{

	@Autowired
	private MeasureWiseExclusionRateRepository measureWiseExclusionRateRepository;
	
	@Override
	public List<MeasureWiseExclusionRate> findAll() {		
		return measureWiseExclusionRateRepository.findAll();
	}

	@Override
	public MeasureWiseExclusionRate findById(int id) {		
		return measureWiseExclusionRateRepository.findById(id);
	}

	@Override
	public MeasureWiseExclusionRate create(MeasureWiseExclusionRate measureWiseExclusionRate) {		
		return measureWiseExclusionRateRepository.saveAndFlush(measureWiseExclusionRate);
	}

}
