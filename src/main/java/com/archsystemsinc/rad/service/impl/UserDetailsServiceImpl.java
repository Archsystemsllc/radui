/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;

/**
 * This is the implementation of the User Details Service for login functionality.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.debug("--> loadUserByUsername");
        User user = getUser(username);
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
    
    public User getUser(String userName) {
    	User  user = new User();
    	user.setUsername("Test007");
    	BCryptPasswordEncoder b = new BCryptPasswordEncoder();
    	user.setPassword(b.encode("1234"));
    	Role role = new Role();
    	role.setName("Administrator");
    	
    	user.getRoles().add(role);
    	return user;
    }
}