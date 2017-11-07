/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.YearLookup;
import com.archsystemsinc.pqrs.repository.YearLookUpRepository;
import com.archsystemsinc.pqrs.service.YearLookUpService;

/**
 * This is the implementation class of the Service interface for year_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 * 
 */
@Service
public class YearLookUpServiceImpl implements YearLookUpService {

	
	@Autowired
	private YearLookUpRepository yearLookUpRepository;
	
	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.YearLookUpservice#findByYearName(java.lang.String)
	 */
	@Override
	public YearLookup findByYearName(String yearName) {
		return yearLookUpRepository.findByYearName(yearName);
	}

	/** (non-Javadoc)
	 * @see com.archsystemsinc.pqrs.service.YearLookUpService#findAll()
	 */
	@Override
	public List<YearLookup> findAll() {
		return yearLookUpRepository.findAll();
	}

}
