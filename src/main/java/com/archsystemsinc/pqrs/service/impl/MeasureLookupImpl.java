/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.MeasureLookup;
import com.archsystemsinc.pqrs.repository.MeasureLookupRepository;
import com.archsystemsinc.pqrs.service.MeasureLookupService;

/**
 * This is the implementation class of Service interface for measure_lookup database table.
 * 
 * @author Grmahun Redda
 * @since 8/23/2017
 * @version 1.1
 * 
 */
@Service
public class MeasureLookupImpl implements MeasureLookupService{
	
	@Autowired
	private MeasureLookupRepository measureLookupRepository;

	@Override
	public List<MeasureLookup> findAll() {		
		return measureLookupRepository.findAll();
	}

	@Override
	public MeasureLookup findById(int id) {		
		return measureLookupRepository.findById(id);
	}

	@Override
	public MeasureLookup findByMeasureId(String id) {		
		return measureLookupRepository.findByMeasureId(id);
	}

	@Override
	public MeasureLookup Save(MeasureLookup measureLookup) {		
		return measureLookupRepository.saveAndFlush(measureLookup);
	}

}
