package com.archsystemsinc.rad.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archsystemsinc.rad.common.utils.RadServiceApiClient;




@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {


 
 private static final Logger log = Logger.getLogger(CustomUserDetailsService.class);
 
 @Autowired
	private RadServiceApiClient radServiceApiClient;
 
 	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("--> loadUserByUsername");
		com.archsystemsinc.rad.model.User user = radServiceApiClient.getUser(username);
		log.debug("user::" + user);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if (user == null || user.getStatus() != 1)
			return new org.springframework.security.core.userdetails.User(" ", " ", grantedAuthorities);
		if(user.getRole() != null) {
			log.debug("role.getName()::" + user.getRole().getRoleName());
			radServiceApiClient.updateUserLastLoginDate(user.getId());
			grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
		}
		log.debug("<-- loadUserByUsername");
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				grantedAuthorities);
	}
 
	/* @Transactional(readOnly=true)
	 @Override
	 public UserDetails loadUserByUsername(final String username) 
	 throws UsernameNotFoundException {
	
			 com.archsystemsinc.rad.model.User user = userService.findByUsername(username);
		 List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
		
		 return buildUserForAuthentication(user, authorities);
	 
	 }

	 // Converts com.tutorialsdesk.model.User user to
	 // org.springframework.security.core.userdetails.User
	 private User buildUserForAuthentication(com.archsystemsinc.rad.model.User user, 
		 List<GrantedAuthority> authorities) {
		 
		 // boolean enabled = true;
		 boolean accountNotExpired = true;
		 boolean credentialsNotExpired = true;
		  boolean accountNotLocked = true;
		 
		 return new User(user.getUserName(), user.getPassword(), 
		 user.getEnabled(), accountNotExpired, credentialsNotExpired, accountNotLocked, authorities);
	 }

	 private List<GrantedAuthority> buildUserAuthority(Role userRole) {
	
		 Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		
		 // Build user's authorities
		
		 setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		 
		
		 List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		
		 return Result;
	}*/
 
}
