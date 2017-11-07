package com.archsystemsinc.pqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archsystemsinc.pqrs.model.Role;

/**
 * This is the Spring Data JPA Repository interface for role database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
	
}
