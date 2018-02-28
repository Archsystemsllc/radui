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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.archsystemsinc.rad.configuration.BasicAuthRestTemplate;
import com.archsystemsinc.rad.controller.HomeController;
import com.archsystemsinc.rad.model.Role;
import com.archsystemsinc.rad.model.ScoreCard;
import com.archsystemsinc.rad.model.User;
import com.archsystemsinc.rad.model.UserFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			log.error("Error while fetching users",ex);
		}
		log.debug("--> getAllUsers");
		return users;
	}


	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
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
			map.add("organizationLookup.id", ""+user.getOrganizationLookup().getId());
			map.add("pccId", ""+user.getPccId());
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


	/**
	 * 
	 * @param userFilter
	 * @return
	 */
	public List<User> findUsers(UserFilter userFilter) {
		log.debug("--> getUser");
		List<User> users = null;
		List<User> usersTempObject = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<List> exchange = restTemplate.exchange(radservicesEndpoint + "findUser/"+userFilter.getLastName()+"/"+userFilter.getRoleId()+"/"+userFilter.getOrgId()+"/"+userFilter.getMacId()+"/"+userFilter.getJurisId(), HttpMethod.GET,
					new HttpEntity<String>(headers), List.class);
			usersTempObject = exchange.getBody();
			ObjectMapper mapper = new ObjectMapper();
			users = mapper.convertValue(usersTempObject, new TypeReference<List<User>>() { });
			log.debug("users::"+users);
			
		}catch(Exception ex) {
			log.error("Errro while finding user, userFilter="+userFilter,ex);
		//	throw new Exception("Errro while finding user, userName="+userName,ex);
		}
		log.debug("<-- getUser");
		return users;
	}


	/**
	 * 
	 * @param id
	 * @param status
	 * @param deletedBy
	 */
	public void deleteById(Long id, int status, String deletedBy) {
		log.debug("--> deleteById");
		ResponseEntity<String> response  = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			headers.set("Content-Type", "application/x-www-form-urlencoded");
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("userId", ""+id);
			map.add("status", ""+status);
			map.add("updatedBy", deletedBy);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			response = restTemplate.exchange( radservicesEndpoint + "updateStatus", HttpMethod.POST,request , String.class );
			log.debug("response:"+response);
		} catch (final HttpClientErrorException httpClientErrorException) {
			log.error("Errro while saving user, httpClientErrorException="+id,httpClientErrorException);
	        //throw new Exception("Errro while finding user, userName="+id,httpClientErrorException);
	  } catch (HttpServerErrorException httpServerErrorException) {
		  log.error("Errro while saving user, httpServerErrorException="+id,httpServerErrorException);
	        //throw new Exception("Errro while finding user, userName="+id,httpServerErrorException);
	  } catch (Exception exception) {
		  log.error("Errro while saving user, exception="+id,exception);
	        //throw new Exception("Errro while finding user, userName="+id,exception);
	    }
		log.debug("<-- deleteById");
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		log.debug("--> getUser");
		User user = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<User> exchange = restTemplate.exchange(radservicesEndpoint + "findUserById/"+id, HttpMethod.GET,
					new HttpEntity<String>(headers), User.class);
			user = exchange.getBody();
			log.debug("user::"+user);
			
		}catch(Exception ex) {
			log.error("Errro while finding user, id="+id,ex);
		//	throw new Exception("Errro while finding user, userName="+userName,ex);
		}
		log.debug("<-- getUser");
		return user;

	}


	public void updateUserLastLoginDate(Long id) {
		log.debug("--> updateUserLastLoginDate");
		String updateCount = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			headers.set("Content-Type", "application/x-www-form-urlencoded");
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("userId", ""+id);
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<String> response = restTemplate.exchange( radservicesEndpoint + "updateUserLastLoginDate", HttpMethod.POST,request , String.class );
			
			updateCount = response.getBody();
			log.debug("updateCount::"+updateCount);
			
		}catch(Exception ex) {
			log.error("Errro while updating user last logged in date, id="+id,ex);
		}
		log.debug("<-- updateUserLastLoginDate");

	}

	public void updateStatusForAll() {
		log.debug("--> updateStatusForAll");
		String updateCount = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			headers.set("Content-Type", "application/x-www-form-urlencoded");
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("status", "0");
			map.add("updatedBy", "UserInactiveJob");
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<String> response = restTemplate.exchange( radservicesEndpoint + "updateStatusForAll", HttpMethod.POST,request , String.class );
			
			updateCount = response.getBody();
			log.debug("updateCount::"+updateCount);
			
		}catch(Exception ex) {
			log.error("Errro while updating user status",ex);
		}
		log.debug("<-- updateStatusForAll");

	}
	
	public void updateUser(User user) throws Exception {
		log.debug("--> updateUser");
		ResponseEntity<String> response  = null;
		try {
			HttpHeaders headers = createServiceHeaders();
			headers.set("Content-Type", "application/x-www-form-urlencoded");
					RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("id", ""+user.getId());
			map.add("password", user.getPassword());
			map.add("firstName", user.getFirstName());
			map.add("lastName", user.getLastName());
			map.add("middleName", user.getMiddleName());
			map.add("emailId", user.getEmailId());
			log.debug("user.getRole().getId()::"+user.getRole().getId());
			map.add("role.id", ""+user.getRole().getId());
			map.add("updatedBy", user.getUpdatedBy());
			map.add("macId", ""+user.getMacId());
			map.add("jurId", ""+user.getJurId());
			map.add("organizationLookup.id", ""+user.getOrganizationLookup().getId());
			map.add("pccId", ""+user.getPccId());
			//map.add("status", ""+user.getStatus());
					HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			response = restTemplate.exchange( radservicesEndpoint + "updateUser", HttpMethod.POST,request , String.class );
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
		log.debug("<-- updateUser");
		
	}
}
