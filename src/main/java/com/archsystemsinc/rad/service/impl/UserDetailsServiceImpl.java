/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.model.User;

/**
 * This is the implementation of the User Details Service for login functionality.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    

	@Value("${radservices.endpoint}")
	String radservicesEndpoint;
	@Value("${radservices.username}")
	String radservicesUserName;
	@Value("${radservices.password}")
	String radservicesPassword;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("--> loadUserByUsername");
		User user = getUser(username);
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


	public User getUser(String userName) {
		log.debug("radservicesUserName::"+radservicesUserName);
		//log.debug("radservicesPassword::"+radservicesPassword);
		log.debug("radservicesEndpoint::"+radservicesEndpoint);
		User user = null;
		try {
			String plainCreds = radservicesUserName + ":" + radservicesPassword;
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/json");
			headers.add("Authorization", "Basic " + base64Creds);
			headers.set("Content-Length", "35");
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<User> exchange = restTemplate.exchange(radservicesEndpoint + "findUser/"+userName, HttpMethod.GET,
					new HttpEntity<String>(headers), User.class);
			user = exchange.getBody();
			log.debug("user::"+user);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return user;
	}
}