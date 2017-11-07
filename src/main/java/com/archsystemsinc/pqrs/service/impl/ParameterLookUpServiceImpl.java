/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.ParameterLookup;
import com.archsystemsinc.pqrs.repository.ParameterLookUpRepository;
import com.archsystemsinc.pqrs.service.ParameterLookUpService;

/**
 * This is the Implementation Class of Service interface for parameter_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/21/2017
 * @version 1.1
 * 
 */
@Service
public class ParameterLookUpServiceImpl implements ParameterLookUpService {

	@Autowired
	private ParameterLookUpRepository parameterLookUpRepository;
	
	/* (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.ParameterLookUpService#findByParameterName(java.lang.String)
	 */
	@Override
	public ParameterLookup findByParameterName(String parameterName) {
		return parameterLookUpRepository.findByParameterName(parameterName);
	}

	/* (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.ParameterLookUpService#findAll()
	 */
	@Override
	public List<ParameterLookup> findAll() {
		return parameterLookUpRepository.findAll();
	}
	
	

}
