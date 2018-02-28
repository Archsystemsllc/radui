package com.archsystemsinc.pqrs.executer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;
import com.archsystemsinc.pqrs.helper.IMapExecuterHelper;
import com.archsystemsinc.pqrs.helper.WebDriverHelper;

/**
 * Created by mkandaswamy on 2/15/2016.
 */
public class IMapUIExecuter {
    // TODO: implements

    private static Logger logger = Logger.getLogger(IMapUIExecuter.class);


    public Map<String,Object> executeCreateApplication(String testCaseID_)  throws Exception{

        Map<String, Object> applicationResultHashMap = new HashMap<String, Object>();
        ConfigurationBean configurationBean = null;
        IMapExecuterHelper iMapExecuterHelper = null;

        logger.info("Execution For Test CASE ID::" + testCaseID_);
        try {

        	iMapExecuterHelper = new IMapExecuterHelper();

            configurationBean = setupConfiguration(testCaseID_);

            // Setting the input/output values to applicationResultHashMap Variable to be used by the invoking method
            applicationResultHashMap.put("ConfigurationBean", configurationBean);

            // This perform method invokes all different sections(GetStarted, Family and Household, Income,
            iMapExecuterHelper.perform(configurationBean);

        } catch (Exception exception) { // TODO: Capture appropriate exception
            setupFailureMessage(configurationBean, exception);
            throw exception;
        }

        return applicationResultHashMap;
    }


    private ConfigurationBean setupConfiguration(String testCaseID_) {

        Properties iMapEnvProps = null;
        ConfigurationBean configurationBean = null;
        try {
            //iMapEnvProps = ConfigurationManager.loadProperties("IMAPEnv.properties");

            WebDriverHelper webDriverHelper = new WebDriverHelper();
            WebDriver webDriver = webDriverHelper.initializeLocalWebDriver();
            // Setting the Implicit Wait Time to the WebDriver
            webDriverHelper.setImplicitWaitTime(webDriver);

            configurationBean = new ConfigurationBean(webDriver);
            configurationBean.setBaseURL("http://localhost:8080/imapui");


            // Initializing the OverAll Status as "TRUE"
            configurationBean.setOverAllStatus(true);

            logger.info("baseURL::"+configurationBean.getBaseURL());
            logger.info("webDriver::"+configurationBean.getWebDriver());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return configurationBean;
    }

    private void cleanup(ConfigurationBean configurationBean_) {
        new WebDriverHelper().cleanUpAndCloseWebDriver(configurationBean_);
    }

    private void setupFailureMessage(ConfigurationBean configurationBean_, Exception exception_){
        exception_.printStackTrace();

        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter( writer );
        exception_.printStackTrace( printWriter );
        printWriter.flush();

        configurationBean_.setErrorMessage(writer.toString());
        configurationBean_.setOverAllStatus(false);
    }


}
