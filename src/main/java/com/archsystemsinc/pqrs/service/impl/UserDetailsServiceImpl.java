/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.pqrs.model.Role;
import com.archsystemsinc.pqrs.model.User;
import com.archsystemsinc.pqrs.repository.UserRepository;

/**
 * This is the implementation of the User Details Service for login functionality.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 * 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.debug("--> loadUserByUsername");
        User user = userRepository.findByUsername(username);
        log.debug("user::"+user);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user == null) return new org.springframework.security.core.userdetails.User(" ", " ", grantedAuthorities);
        for (Role role : user.getRoles()){
        	log.debug("role.getName()::"+role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        log.debug("<-- loadUserByUsername");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}