package com.archsystemsinc.rad.configuration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.archsystemsinc.rad.service.UserService;



@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

 @Autowired 
 private UserService userService;
 
 @Bean
 public BCryptPasswordEncoder bCryptPasswordEncoder() {
     return new BCryptPasswordEncoder();
 }
 
 @Autowired
 @Qualifier("customUserDetailsService")
 @Override
 public void setUserDetailsService(UserDetailsService userDetailsService) {
	 super.setUserDetailsService(userDetailsService);
	 super.setPasswordEncoder(bCryptPasswordEncoder());
 }
 

 @Override
 public Authentication authenticate(Authentication authentication) 
 throws AuthenticationException {

	 try {
	
	 Authentication auth = super.authenticate(authentication);
	 
	 Boolean isUserLocked = userService.isUserLocked(authentication.getName());
	 
	 if(isUserLocked) {
		 throw new LockedException("User account is locked!");
	 } else {
		 //if reach here, means login success, else an exception will be thrown
		 //reset the user_attempts
		 userService.resetFailAttempts(authentication.getName());
	 }
	
	 
	 return auth;
	 
	 } catch (BadCredentialsException e) { 
	 
	 //invalid login, update to user_attempts
	 userService.updateFailAttempts(authentication.getName());
	 throw e;
	 
	 } catch (LockedException e){
	 
	 //this user is locked!
	 String	 error = "User account is locked! <br><br>Username : " + authentication.getName() ;
	 //UserAttempts userAttempts = userService.getUserAttempts(authentication.getName());
	 /*if(userAttempts!=null){
	 Date lastAttempts = userAttempts.getLastModified();
	 error = "User account is locked! <br><br>Username : " + authentication.getName() + "<br>Last Attempts : " + lastAttempts;
	 }else{
	 error = e.getMessage();
	 }*/
	 
	 throw new LockedException(error);
	 }
	
	 }
 
}