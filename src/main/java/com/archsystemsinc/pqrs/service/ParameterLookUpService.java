/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List; 

import com.archsystemsinc.pqrs.model.ParameterLookup;

/**
 * This is the Service interface for parameter_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/21/2017
 * @version 1.1
 * 
 */
public interface ParameterLookUpService { 

	ParameterLookup findByParameterName(final String parameterName);
	
	List<ParameterLookup> findAll();
	
}
