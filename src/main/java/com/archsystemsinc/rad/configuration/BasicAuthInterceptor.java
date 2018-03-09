package com.archsystemsinc.rad.configuration;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;

public class BasicAuthInterceptor implements ClientHttpRequestInterceptor {

    private String username;
    private String password;
    
    private static String username1;
    public BasicAuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
        username1 =username;
    }    

    public static String encodeCredentialsForBasicAuth(String username, String password) {
        return "Basic " + new Base64().encodeToString((username + ":" + password).getBytes());
    }

	@Override
	public ClientHttpResponse intercept(org.springframework.http.HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
		HttpHeaders headers = httpRequest.getHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, encodeCredentialsForBasicAuth(username, password));

        return clientHttpRequestExecution.execute(httpRequest, bytes);
	}
	
	 public static boolean isUserLogged() {
	        try {
	            return !SecurityContextHolder.getContext().getAuthentication().getName().equals(username1);
	        } catch (Exception e) {
	            return false;
	        }
	    }
}
