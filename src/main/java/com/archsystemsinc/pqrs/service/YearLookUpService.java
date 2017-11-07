/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.YearLookup;

/**
 * This is the Service interface for year_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 */
public interface YearLookUpService {
	
	YearLookup findByYearName(final String yearName);
	
	List<YearLookup> findAll();

}
