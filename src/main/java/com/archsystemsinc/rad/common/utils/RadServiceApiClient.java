/**
 * 
 */
package com.archsystemsinc.rad.common.utils;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.User;

/**
 * @author 
 *
 */
@Service
public class RadServiceApiClient {
	private static final Logger log = Logger.getLogger(RadServiceApiClient.class);
	@Value("${radservices.endpoint}")
	String radservicesEndpoint;
	@Value("${radservices.username}")
	String radservicesUserName;
	@Value("${radservices.password}")
	String radservicesPassword;
	
	/**
	 * 
	 * @return
	 */
	private HttpHeaders createServiceHeaders() {
		log.debug("radservicesUserName::"+radservicesUserName);
		log.debug("radservicesEndpoint::"+radservicesEndpoint);
	
		String plainCreds = radservicesUserName + ":" + radservicesPassword;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("Authorization", "Basic " + base64Creds);
		headers.set("Content-Length", "35");
		return headers;
	}
	
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	public User getUser(String userName)  {
		log.debug("--> getUser");
		User user = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<User> exchange = restTemplate.exchange(radservicesEndpoint + "findUser/"+userName, HttpMethod.GET,
					new HttpEntity<String>(headers), User.class);
			user = exchange.getBody();
			log.debug("user::"+user);
			
		}catch(Exception ex) {
			log.error("Errro while finding user, userName="+userName,ex);
		//	throw new Exception("Errro while finding user, userName="+userName,ex);
		}
		log.debug("<-- getUser");
		return user;
	}

	
	/**
	 * 
	 * @return
	 */
	public List<Role> getRoles() {
		log.debug("--> getRoles");
		List<Role>  roles = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<List> exchange = restTemplate.exchange(radservicesEndpoint + "listRoles", HttpMethod.GET,
					new HttpEntity<String>(headers), List.class);
			roles = exchange.getBody();
			log.debug("roles::"+roles);
			
		}catch(Exception ex) {
			log.error("Errro while fetching roles",ex);
		}
		log.debug("--> getRoles");
		return roles;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getAllUsers() {
		log.debug("--> getAllUsers");
		List<User>  users = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<List> exchange = restTemplate.exchange(radservicesEndpoint + "listUsers", HttpMethod.GET,
					new HttpEntity<String>(headers), List.class);
			users = exchange.getBody();
			log.debug("users::"+users);
			
		}catch(Exception ex) {
			log.error("Errro while fetching users",ex);
		}
		log.debug("--> getAllUsers");
		return users;
	}


	public Object saveUser(User user) throws Exception {
	
		log.debug("--> saveUser");
		ResponseEntity<String> response  = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			
			headers.set("Content-Type", "application/x-www-form-urlencoded");
			

			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("userName", user.getUserName());
			map.add("password", user.getPassword());
			map.add("firstName", user.getFirstName());
			map.add("lastName", user.getLastName());
			map.add("middleName", user.getMiddleName());
			map.add("emailId", user.getEmailId());
			log.debug("user.getRole().getId()::"+user.getRole().getId());
			map.add("role.id", ""+user.getRole().getId());
			map.add("createdBy", user.getCreatedBy());
			map.add("updatedBy", user.getCreatedBy());
			map.add("macId", ""+user.getMacId());
			map.add("jurId", ""+user.getJurId());
			map.add("orgId", ""+user.getOrgId());
			map.add("status", "1");
			

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			response = restTemplate.exchange( radservicesEndpoint + "createUser", HttpMethod.POST,request , String.class );
			log.debug("response:"+response);
		} catch (final HttpClientErrorException httpClientErrorException) {
			log.error("Errro while saving user, httpClientErrorException="+user,httpClientErrorException);
	        throw new Exception("Errro while finding user, userName="+user,httpClientErrorException);
	  } catch (HttpServerErrorException httpServerErrorException) {
		  log.error("Errro while saving user, httpServerErrorException="+user,httpServerErrorException);
	        throw new Exception("Errro while finding user, userName="+user,httpServerErrorException);
	  } catch (Exception exception) {
		  log.error("Errro while saving user, exception="+user,exception);
	        throw new Exception("Errro while finding user, userName="+user,exception);
	    }
		log.debug("<-- saveUser");
		return response;
	}
}
