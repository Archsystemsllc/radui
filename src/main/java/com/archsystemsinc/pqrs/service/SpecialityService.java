/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.Speciality;

/**
 * interface for template service class 
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 * @version 1.1
 */
public interface SpecialityService {
	Speciality create(final Speciality specialty);	
	void update(final Speciality specialty);
	Speciality findById(final Long id);
	void deleteById(final Long id);
	List<Speciality> findAll();    
    List<Speciality> findAllByUserId(Long id);

}
