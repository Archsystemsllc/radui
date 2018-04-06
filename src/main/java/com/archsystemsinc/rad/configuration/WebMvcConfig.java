package com.archsystemsinc.rad.configuration;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


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
		///Local Development Environment
    	//String activeProfile = System.getProperty("spring.profiles.active",	"local");
    	
    	//AWS Development Environment
    	//String activeProfile = System.getProperty("spring.profiles.active",	"development");
    	
    	//AWS UAT Environment
    	//String activeProfile = System.getProperty("spring.profiles.active", "uat");
    	
    	//AWS Prod Environment
    	String activeProfile = System.getProperty("spring.profiles.active", "prod");
    	
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
	
    
    @Bean(name="multipartResolver")
    public StandardServletMultipartResolver resolver(){
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