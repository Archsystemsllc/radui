/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.CategoryLookup;

/**
 * This is the Service interface for category_lookup database table.
 * 
 * @author Grmahun Redda
 * @version 1.1
 * 
 */
public interface CategoryLookupService {
	
	List<CategoryLookup> findAll();
	CategoryLookup findById(final int id);
	CategoryLookup findByName(final String name);
}
