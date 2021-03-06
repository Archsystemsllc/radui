package com.archsystemsinc.rad.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This is the Configuration class for the Web Security.
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    CustomSuccessHandler customSuccessHandler;
   
    
    @Autowired
    @Qualifier("authenticationProvider")
    AuthenticationProvider authenticationProvider;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider);
        
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()     	
    	.antMatchers("/resources/**", "/login").permitAll()  
    	 .antMatchers("/administrator/**").access("hasAuthority('Administrator')")
        .antMatchers("/quality_manager/**").access("hasAuthority('Quality Manager')")
        .antMatchers("/cms_user/**").access("hasAuthority('CMS User')")
        .antMatchers("/mac_admin/**").access("hasAuthority('MAC Admin')")
        .antMatchers("/quality_monitor/**").access("hasAuthority('Quality Monitor')")
        .antMatchers("/mac_user/**").access("hasAuthority('MAC User')")
        .and().formLogin().loginPage("/login").permitAll().successHandler(customSuccessHandler)        
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied")
        .and().logout().logoutSuccessUrl("/login?logout")
        .and().sessionManagement().maximumSessions(2).expiredUrl("/login?logout")
        ;    	
    	
    } 
    
  
}
