/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.StatewiseStatistic;
import com.archsystemsinc.pqrs.repository.StatewiseStatisticRepository;
import com.archsystemsinc.pqrs.service.StatewiseStatisticService;

/**
 * service class for speciality file object  
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 * @version 1.1
 */
@Service
public class StatewiseStatisticServiceImpl implements StatewiseStatisticService{

	@Autowired
	private StatewiseStatisticRepository statewiseStatisticRepository;
	
	@Override
	public StatewiseStatistic create(StatewiseStatistic statewiseStatistic) {		
		return statewiseStatisticRepository.saveAndFlush(statewiseStatistic);
	}

	@Override
	public void update(StatewiseStatistic statewiseStatistic) {		
		statewiseStatisticRepository.save(statewiseStatistic);
	}

	@Override
	public StatewiseStatistic findById(Long id) {		
		return statewiseStatisticRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		statewiseStatisticRepository.delete(id);
		
	}

	@Override
	public List<StatewiseStatistic> findAll() {		
		return statewiseStatisticRepository.findAll();
	}

	@Override
	public List<StatewiseStatistic> findAllByUserId(Long id) {		
		return null;
	}

}
