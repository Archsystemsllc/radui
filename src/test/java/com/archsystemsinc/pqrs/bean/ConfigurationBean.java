package com.archsystemsinc.pqrs.bean;

import org.openqa.selenium.WebDriver;

import java.io.Serializable;

/**
 * Created by mkandaswamy on 2/15/2016.
 */
public class ConfigurationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String baseURL;
    private String environment;
    private WebDriver webDriver;
    private String userAccountEmailID;
    private boolean overAllStatus;
    private String errorMessage;
    private boolean isExecutionInCloud;


    public ConfigurationBean() {
    }

    public ConfigurationBean(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getUserAccountEmailID() {
        return userAccountEmailID;
    }

    public void setUserAccountEmailID(String userAccountEmailID) {
        this.userAccountEmailID = userAccountEmailID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isOverAllStatus() {
        return overAllStatus;
    }

    public void setOverAllStatus(boolean overAllStatus) {
        this.overAllStatus = overAllStatus;
    }

    public boolean isExecutionInCloud() {
        return isExecutionInCloud;
    }

    public void setExecutionInCloud(boolean executionInCloud) {
        isExecutionInCloud = executionInCloud;
    }

}
