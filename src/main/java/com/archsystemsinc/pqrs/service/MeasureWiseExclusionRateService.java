/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.MeasureWiseExclusionRate;

/**
 * This is the Service interface for measure_exclusion_rate database table.
 * @author Grmahun Redda
 * @version 1.1
 */
public interface MeasureWiseExclusionRateService {
	
	List<MeasureWiseExclusionRate> findAll();
	MeasureWiseExclusionRate findById(final int id);
	MeasureWiseExclusionRate create(final MeasureWiseExclusionRate measureWiseExclusionRate);
}
