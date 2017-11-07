/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import com.archsystemsinc.pqrs.model.Role;
import com.archsystemsinc.pqrs.model.TemplateFile;
import com.archsystemsinc.pqrs.model.User;
import com.archsystemsinc.pqrs.repository.RoleRepository;
import com.archsystemsinc.pqrs.repository.UserRepository;
import com.archsystemsinc.pqrs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is the implementation class of the Service interface for user database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 * 
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
	public User findById(Long id) {		
		return userRepository.findOne(id);
	}
    
    @Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
    
    @Override
	public void deleteById(Long id) {		
    	userRepository.delete(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return (List<Role>)roleRepository.findAll();
	}
	
	@Override
	public Role findRoleById(Long id) {
		return (Role)roleRepository.findOne(id);
	}
}