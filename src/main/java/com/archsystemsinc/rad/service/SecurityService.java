/**
* Copyright (c) 2017, Archsystems Inc and/or its affiliates. All rights reserved.
*/

package com.archsystemsinc.rad.service;

/**
 * This is the Service interface for Spring Security for Login.
 */
public interface SecurityService {

	String findLoggedInUsername();

	void autologin(String username, String password);

}
