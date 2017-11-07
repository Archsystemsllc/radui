/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

import java.util.List;
import java.util.Set;

import com.archsystemsinc.pqrs.model.Role;
import com.archsystemsinc.pqrs.model.TemplateFile;
import com.archsystemsinc.pqrs.model.User;

/**
 * This is the Service interface for user database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 */
public interface UserService {

	void save(User user);
	
	void update(User user);

	User findByUsername(String username);
	
	User findById(final Long id);
	
	List<User> findAll();
	
	void deleteById(final Long id);
	
	List<Role> findAllRoles();
	
	Role findRoleById(Long id);
}
