/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.Speciality;
import com.archsystemsinc.pqrs.repository.SpecialityRepository;
import com.archsystemsinc.pqrs.service.SpecialityService;

/**
 * service class for speciality file object  
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 * @version 1.1
 */
@Service
public class SpecialityServiceImpl implements SpecialityService{

	@Autowired
	private SpecialityRepository specialityRepository;
	
	@Override
	public Speciality create(Speciality speciality) {
		// TODO Auto-generated method stub
		return specialityRepository.saveAndFlush(speciality);
	}

	@Override
	public void update(Speciality specialty) {
		// TODO Auto-generated method stub
		specialityRepository.save(specialty);
	}

	@Override
	public Speciality findById(Long id) {
		// TODO Auto-generated method stub
		return specialityRepository.findOne(id);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		specialityRepository.delete(id);
	}

	@Override
	public List<Speciality> findAll() {
		// TODO Auto-generated method stub
		return specialityRepository.findAll();
	}

	@Override
	public List<Speciality> findAllByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
