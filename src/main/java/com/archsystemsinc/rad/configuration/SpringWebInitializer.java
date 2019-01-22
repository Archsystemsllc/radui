package com.archsystemsinc.rad.configuration;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.displaytag.filter.ResponseOverrideFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebMvcConfig.class, WebSecurityConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    protected Filter[] getServletFilters() {
    	Filter [] singleton = { new ResponseOverrideFilter() };
    	return singleton;
	}
 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
              super.onStartup(servletContext);
        servletContext.addListener(new SessionListener());
    }
    
    @Value("${rad.uploadfile.server.location}")
    public static String SERVER_UPLOAD_FILE_LOCATION;
    
    private static final String LOCATION = SERVER_UPLOAD_FILE_LOCATION;
    private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
                                                       // Beyond that size spring will throw exception.
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
    
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }
 
    private MultipartConfigElement getMultipartConfigElement(){
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }
    
}