/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;
import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.model.UserFilter;
import com.archsystemsinc.rad.service.UserService;

/**
 * This is the implementation class of the Service interface for user database table.
 */
@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	private RadServiceApiClient radServiceApiClient;
	
	@Override
	public void resetFailAttempts(String name) {
		radServiceApiClient.resetFailAttempts(name);
		
	}

	@Override
	public void updateFailAttempts(String name) {
		radServiceApiClient.updateFailAttempts(name);
		
	}

	@Override
	public User getUserAttempts(String name) {
		return radServiceApiClient.getUserAttempts(name);		
	}

	
    @Override
    public void save(User user) throws Exception {
    
    	radServiceApiClient.saveUser(user);
    }
    
    @Override
    public void update(User user) throws Exception {
		if(user.getPasswordFromdb().equals(user.getPassword())){
			log.debug("No need encrypt Password!!");
		}else{
			BCryptPasswordEncoder b = new BCryptPasswordEncoder();
			String userPwd = b.encode(user.getPassword());
			user.setPassword(userPwd);
		}
    	radServiceApiClient.updateUser(user);
    }
    
    

    @Override
    public User findByUsername(String userName) {
        return radServiceApiClient.getUser(userName);
    }
    
    @Override
	public User findById(Long id) {		
    	return radServiceApiClient.getUser(id);
	}
    
    @Override
	public List<User> findAll() {
		return radServiceApiClient.getAllUsers();
	}
    
    @Override
	public void deleteById(Long id, int status, String deletedBy) {		
    	radServiceApiClient.deleteById(id, status, deletedBy);
	}

	@Override
	public List<Role> findAllRoles() {
		return radServiceApiClient.getRoles();
	}
	
	@Override
	public Role findRoleById(Long id) {
		return null;
	}

	@Override
	public List<User> findUsers(UserFilter userFilter) {
		return radServiceApiClient.findUsers(userFilter);
	}

	@Override
	public boolean isUserLocked(String name) {
		return radServiceApiClient.isUserLocked(name);	
	}

	
	
}