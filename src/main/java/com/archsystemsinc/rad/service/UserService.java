/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service;

import java.util.List;

import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.model.UserFilter;

/**
 * This is the Service interface for user database table.
 */
public interface UserService {

	void save(User user) throws Exception;
	
	void update(User user);

	User findByUsername(String username);
	
	User findById(final Long id);
	
	List<User> findAll();
	
	void deleteById(final Long id);
	
	List<Role> findAllRoles();
	
	Role findRoleById(Long id);

	List<User> findUsers(UserFilter userFilter);
}
