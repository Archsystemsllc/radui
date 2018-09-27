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
	
	void update(User user) throws Exception;

	User findByUsername(String username);
	
	User findById(final Long id);
	
	List<User> findAll();
	
	void deleteById(final Long id, int status, String deletedBy);
	
	List<Role> findAllRoles();
	
	Role findRoleById(Long id);

	List<User> findUsers(UserFilter userFilter);

	void resetFailAttempts(String name);

	void updateFailAttempts(String name);
	
	boolean isUserLocked(String name);

	User getUserAttempts(String name);
}
