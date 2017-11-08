/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.service.UserService;

/**
 * This is the implementation class of the Service interface for user database table.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void save(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
    
    @Override
    public void update(User user) {
        //userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }
    
    @Override
	public User findById(Long id) {		
		return null;
	}
    
    @Override
	public List<User> findAll() {
		return null;
	}
    
    @Override
	public void deleteById(Long id) {		
    	//userRepository.delete(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return null;
	}
	
	@Override
	public Role findRoleById(Long id) {
		return null;
	}
}