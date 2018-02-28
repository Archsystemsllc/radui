package com.archsystemsinc.pqrs.pages;

import org.apache.commons.lang3.StringUtils;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;

/**
 * Created by Murugaraj Kandaswamy on 7/7/2017.
 */
public abstract class BasePage {

    protected ConfigurationBean configurationBean;

    public BasePage() {
    }

    public BasePage(ConfigurationBean configurationBean) {
        this.configurationBean = configurationBean;
    }

    // Subclass Exception
    public abstract boolean perform () throws  Exception;

    public ConfigurationBean getConfigurationBean() {
        return configurationBean;
    }

    public String replaceToken(String elementStr, String token, int substitude) {
        return StringUtils.replace(elementStr, token, substitude + "");
    }

    public boolean isNoSuchElementExceptionExist(Exception exception_) {
        if (exception_.getMessage().contains("Unable to locate element")){
            // Suppressing the Expression. Since this question may or may not show; based on applicant data.
            return true;
        }
        return false;
    }

    public void wait(int waitTimeOutInSec) throws InterruptedException {Thread.sleep((long)waitTimeOutInSec*1000);}

}