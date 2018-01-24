/**
 * 
 */
package com.archsystemsinc.rad.configuration;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final Logger log = Logger.getLogger(CustomSuccessHandler.class);
    
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);
 
        if (response.isCommitted()) {
            log.debug("Can't redirect");
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
    	log.debug("--> determineTargetUrl");
        String url = "";
 
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        log.debug("authorities:"+authorities);
        List<String> roles = new ArrayList<String>();
 
        for (GrantedAuthority a : authorities) {
        	log.debug("a.getAuthority()::"+a.getAuthority());
            roles.add(a.getAuthority());
        }
 
        if (isAdmin(roles)) {
            url = "/admin/dashboard";
            
            
        } else if (isUser(roles)) {
            /*url = "/reportingHome";*/
        	url = "/user/dashboard";
        } else {
            url = "/accessDenied";
        }
        log.debug("<-- determineTargetUrl-url:"+url);
        return url;
    }
 
    private boolean isUser(List<String> roles) {
        if (roles.contains("Report Viewer")) {
            return true;
        }
        return false;
    }
 
    private boolean isAdmin(List<String> roles) {
        if (roles.contains("Administrator")) {
            return true;
        }
        return false;
    }
 
   
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
 
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
 
}