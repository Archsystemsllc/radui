package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.ParameterLookup;

/**
 * This is the Spring Data JPA Repository interface for parameter_lookup database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface ParameterLookUpRepository extends JpaRepository<ParameterLookup, Long> {
	
	ParameterLookup findById(final int id);
	
	ParameterLookup findByParameterName(final String parameterName);

}
