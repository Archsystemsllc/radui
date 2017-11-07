/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.pqrs.service;

/**
 * This is the Service interface for Spring Security for Login.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * @version 1.1
 * 
 */
public interface SecurityService {

	String findLoggedInUsername();

	void autologin(String username, String password);

}
