/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.CategoryLookup;
import com.archsystemsinc.pqrs.repository.CategoryLookupRepository;
import com.archsystemsinc.pqrs.service.CategoryLookupService;

/**
 * This is the implementation class of Service interface for category_lookup database table.
 * 
 * @author Grmahun Redda
 * @since 8/23/2017
 * @version 1.1
 * 
 */
@Service
public class CategoryLookupImpl implements CategoryLookupService{
	
	@Autowired
	private CategoryLookupRepository categoryLookupRepository;

	@Override
	public List<CategoryLookup> findAll() {		
		return categoryLookupRepository.findAll();
	}

	@Override
	public CategoryLookup findById(int id) {		
		return categoryLookupRepository.findById(id);
	}

	@Override
	public CategoryLookup findByName(String name) {		
		return categoryLookupRepository.findByName(name);
	}
}
