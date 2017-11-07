/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;

import com.archsystemsinc.pqrs.model.TemplateFile;

/**
 * interface for template service class 
 * 
 * @author Grmahun Redda
 * @since 6/16/2017
 * @version 1.1
 */
public interface TemplateFileService {
	TemplateFile create(final TemplateFile uploadedFile);	
	void update(final Long id);
	TemplateFile findById(final Long id);
	void deleteById(final Long id);
	List<TemplateFile> findAll();    
    List<TemplateFile> findAllByUserId(Long id);  

}
