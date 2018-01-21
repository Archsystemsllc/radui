/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

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

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;
import com.archsystemsinc.rad.model.User;

/**
 * This is the implementation of the User Details Service for login functionality.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    
	@Autowired
	private RadServiceApiClient radServiceApiClient;
	

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("--> loadUserByUsername");
		User user = radServiceApiClient.getUser(username);
		log.debug("user::" + user);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if (user == null)
			return new org.springframework.security.core.userdetails.User(" ", " ", grantedAuthorities);
		if(user.getRole() != null) {
			log.debug("role.getName()::" + user.getRole().getRoleName());
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
		}
		log.debug("<-- loadUserByUsername");
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				grantedAuthorities);
	}


	
}