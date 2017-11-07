/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.ExclusionTrendsRate;

/**
 * This is the Service interface for exclusion_trends database table.
 * 
 * @author Venkat Challa
 * @since 8/23/2017
 * @version 1.1
 * 
 */
public interface ExclusionTrendsRateService {
	
	List<ExclusionTrendsRate> findAll();
	ExclusionTrendsRate findById(final int id);
	ExclusionTrendsRate create(final ExclusionTrendsRate exclusionTrendsRate);
}
