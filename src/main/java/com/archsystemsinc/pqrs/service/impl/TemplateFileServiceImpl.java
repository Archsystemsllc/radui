/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.pqrs.model.TemplateFile;
import com.archsystemsinc.pqrs.repository.TemplateFileRepository;
import com.archsystemsinc.pqrs.service.TemplateFileService;

/**
 * service class for template file object  
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 * @version 1.1
 */
@Service
public class TemplateFileServiceImpl implements TemplateFileService{

	@Autowired
	private TemplateFileRepository templateFileRepository;
	
	@Override
	public TemplateFile create(TemplateFile uploadedFile) {		
		return templateFileRepository.save(uploadedFile);
	}
	

	@Override
	public void update(Long id) {		
		templateFileRepository.saveAndFlush(templateFileRepository.getOne(id));
	}

	@Override
	public TemplateFile findById(Long id) {		
		return templateFileRepository.findOne(id);
	}


	@Override
	public void deleteById(Long id) {		
		templateFileRepository.delete(id);
	}


	@Override
	public List<TemplateFile> findAll() {
		return templateFileRepository.findAll();
	}


	@Override
	public List<TemplateFile> findAllByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
