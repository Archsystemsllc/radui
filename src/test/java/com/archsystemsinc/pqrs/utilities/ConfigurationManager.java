package com.archsystemsinc.pqrs.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.PropertyResourceBundle;

/**
 * This Class implements out the helper methods to load any properties file, load any excel file.
 *
 * @author Murugaraj Kandaswamy
 * @version 1.0
 * @since 1/7/2016
 */
public class ConfigurationManager {
 
    /**
     * This method loads the properties file that is needed for the MTST Automation Application.
     *
     * @param propertiesFileName_ propertiesFileName_ of the properties file to load
     * @return returns a Properties object
     */
    public static Properties loadProperties(String propertiesFileName_) {
        Properties properties = new Properties();
        InputStream in = ConfigurationManager.class.getClassLoader().getResourceAsStream(propertiesFileName_);
        try {
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    /**
     * This method loads the properties file that is needed for the MTST Automation Application.
     *
     * @param propertiesFileName_ propertiesFileName_ of the properties file to load
     * @return returns a Properties object
     */
    public static PropertyResourceBundle loadResourcesBundle(String propertiesFileName_) {
    	PropertyResourceBundle propertyResourceBundle = null;
        try {
        	FileInputStream fis = loadFileInputStream(propertiesFileName_);
        	propertyResourceBundle=new PropertyResourceBundle(fis); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyResourceBundle;
    }
    
    
    /**
     * This method loads the properties file that is needed for the MTST Automation Application.
     *
     * @param fileName_ propertiesFileName_ of the properties file to load
     * @return returns a Properties object
     */
    public static FileInputStream loadFileInputStream(String fileName_) {
        URL url = ConfigurationManager.class.getClassLoader().getResource(fileName_);
        File file = null;
        FileInputStream fileInputStream = null;
		try {
			file = new File(url.toURI());
			fileInputStream = new FileInputStream(file); 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return fileInputStream;
        
    }
    
    
    
}
