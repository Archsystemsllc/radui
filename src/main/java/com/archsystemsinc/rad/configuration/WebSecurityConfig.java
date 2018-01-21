package com.archsystemsinc.rad.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    private UserDetailsService userDetailsService;
    @Autowired
    CustomSuccessHandler customSuccessHandler;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests() 
    	.antMatchers("/user/**").access("hasAuthority('Report Viewer')")
        .antMatchers("/admin/**").access("hasAuthority('Administrator')")
        .antMatchers("/resources/**").permitAll()
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").permitAll().successHandler(customSuccessHandler)        
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied")
        .and().logout().logoutSuccessUrl("/login?logout");
    } 

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
