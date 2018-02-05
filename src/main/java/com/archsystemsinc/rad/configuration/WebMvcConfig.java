package com.archsystemsinc.rad.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.archsystemsinc.rad.model.MacInfo;
import com.archsystemsinc.rad.model.MacProgJurisPccMapping;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan({ "com.archsystemsinc.rad" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	
	@Bean
	public TaskScheduler taskScheduler() {
		
	    return new ConcurrentTaskScheduler(); //single threaded by default
	}
	
	@Bean
   	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		//Development Environment
    	//String activeProfile = System.getProperty("spring.profiles.active",	"local");
    	
    	//Test Environment
    	String activeProfile = System.getProperty("spring.profiles.active", "test");
    	
    	//UAT Environment
    	//String activeProfile = System.getProperty("spring.profiles.active", "uat");
    	
    	//Prod Environment
    	//String activeProfile = System.getProperty("spring.profiles.active", "prod");
    	
   		String propertiesFilename = "application-" + activeProfile	+ ".properties";
   		System.out.println("propertiesFilename:" + propertiesFilename);
   		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
   		configurer.setLocation(new ClassPathResource(propertiesFilename));
   		
   		return configurer;
   	}
	@Bean
	public ViewResolver viewResolver() {
		final UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "multipartResolver")
    public StandardServletMultipartResolver resolver() {
		
        return new StandardServletMultipartResolver();
    } 
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}
	
	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages/messages");
		return messageSource;
	}

}