/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.StatewiseStatistic;

/**
 * interface for template service class 
 * 
 * @author Grmahun Redda
 * @since 6/21/2017
 * @version 1.1
 */
public interface StatewiseStatisticService {
	StatewiseStatistic create(final StatewiseStatistic statewiseStatistic);	
	void update(final StatewiseStatistic statewiseStatistic);
	StatewiseStatistic findById(final Long id);
	void deleteById(final Long id);
	List<StatewiseStatistic> findAll();    
    List<StatewiseStatistic> findAllByUserId(Long id); 

}
