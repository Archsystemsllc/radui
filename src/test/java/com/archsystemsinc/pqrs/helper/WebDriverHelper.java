 package com.archsystemsinc.pqrs.helper;

 import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;

/**
 * Created by Murugaraj Kandaswamy on 7/6/2017.
 */
public class WebDriverHelper {

    private static Logger logger = Logger.getLogger(WebDriverHelper.class);

    public WebDriverHelper() {
    }

    public WebDriver initializeLocalWebDriver()  throws Exception
    {
        WebDriver webDriver = null;
        try {
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
            firefoxProfile.setPreference("browser.download.dir", "C:\\Users\\");
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
            firefoxProfile.setPreference("pdfjs.disabled", true);
            firefoxProfile.setPreference("plugin.scan.Acrobat", "99.0");
            firefoxProfile.setPreference("plugin.scan.plid.all", false);

            webDriver = new FirefoxDriver(firefoxProfile);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return webDriver;
    }

    public void setImplicitWaitTime(WebDriver webDriver_) throws Exception {
        logger.info("setImplicitWaitTime(WebDriver webDriver_)--->");
        // Setting Default Implicit wait time of 10 Seconds while executing in Local Machine.
        int timeOutInSeconds = 10;
        // Setting the Implicit Wait Time to the WebDriver
        webDriver_.manage().timeouts().implicitlyWait((long)timeOutInSeconds, TimeUnit.SECONDS);
        logger.info("setImplicitWaitTime(WebDriver webDriver_)<---");

    }

    public void cleanUpAndCloseWebDriver(ConfigurationBean configurationBean_) {

        configurationBean_.getWebDriver().close();
        configurationBean_.getWebDriver().quit();
    }

}
