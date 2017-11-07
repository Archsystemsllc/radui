/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.MeasureLookup;

/**
 * This is the Service interface for measure_lookup database table.
 * @author Grmahun Redda
 * @version 1.1
 */
public interface MeasureLookupService {
	
	List<MeasureLookup> findAll();
	MeasureLookup findById(final int id);
	MeasureLookup findByMeasureId(final String id);
	MeasureLookup Save(final MeasureLookup measureLookup);
}
