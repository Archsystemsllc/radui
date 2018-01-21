/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;
import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.UserService;

/**
 * This is the implementation class of the Service interface for user database table.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private RadServiceApiClient radServiceApiClient;
	
    @Override
    public void save(User user) throws Exception {
    
    	radServiceApiClient.saveUser(user);
    }
    
    @Override
    public void update(User user) {
    }

    @Override
    public User findByUsername(String userName) {
        return radServiceApiClient.getUser(userName);
    }
    
    @Override
	public User findById(Long id) {		
	return null;
	}
    
    @Override
	public List<User> findAll() {
		return radServiceApiClient.getAllUsers();
	}
    
    @Override
	public void deleteById(Long id) {		
    	//userRepository.delete(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return radServiceApiClient.getRoles();
	}
	
	@Override
	public Role findRoleById(Long id) {
		return null;
	}
}