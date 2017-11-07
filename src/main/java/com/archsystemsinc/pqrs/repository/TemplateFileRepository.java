package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.TemplateFile;

/**
 * interface for template repository class 
 * 
 * @author Grmahun Redda
 * @since 6/15/2017
 */
public interface TemplateFileRepository extends JpaRepository<TemplateFile, Long>{

}
