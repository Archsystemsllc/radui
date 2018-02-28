package com.archsystemsinc.pqrs.pages;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;
import com.archsystemsinc.pqrs.utilities.BrowserBot;
import com.archsystemsinc.pqrs.utilities.ConfigurationManager;
import com.archsystemsinc.pqrs.utilities.RegressionBrowserBot;

/**
 * Created by mkandaswamy on 2/15/2016.
 */
public class UserLoginPage extends BasePage{

    private static Logger logger = Logger.getLogger(UserLoginPage.class);
    private Properties userRegPageProperties;
    private RegressionBrowserBot regBrowserBot = null;

    public UserLoginPage() {
    }

    public UserLoginPage(ConfigurationBean configurationBean_) {
        super(configurationBean_);
    }

    private RegressionBrowserBot getRegressionBrowserBot() {
        if (regBrowserBot == null) {
            regBrowserBot = new RegressionBrowserBot(configurationBean.getWebDriver());
        }
        return regBrowserBot;
    }

    @Override
    public boolean perform() throws Exception{

    	//userRegPageProperties = ConfigurationManager.loadProperties("objectrepos/UserRegistrationPage.properties");
        boolean status = true;
        try {

            this.openWebsite();
       //     this.provideUserCredentials();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }

        return status;
    }


    public boolean openWebsite() throws Exception {

        boolean status = true;

        ConfigurationBean configurationBean = super.getConfigurationBean();

        try {
            RegressionBrowserBot regBrowserBot = getRegressionBrowserBot();

            regBrowserBot.goToUrl(configurationBean.getBaseURL());
            regBrowserBot.refreshPage();
            regBrowserBot.maximiseWindow();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return status;
    }


}
