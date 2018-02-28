package com.archsystemsinc.pqrs.helper;

import org.apache.log4j.Logger;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;
import com.archsystemsinc.pqrs.pages.MapAndChartDisplayPage;
import com.archsystemsinc.pqrs.pages.UserLoginPage;

/**
 * Created by mkandaswamy on 5/11/2016.
 */
public class IMapExecuterHelper {

    private static Logger logger = Logger.getLogger(IMapExecuterHelper.class);

    public IMapExecuterHelper() {
    }

    public void perform(ConfigurationBean configurationBean) throws Exception {

        logger.info("perform(ConfigurationBean configurationBean)--->");

        UserLoginPage userLoginPage = new UserLoginPage(configurationBean);
        userLoginPage.perform();
        
        //MapAndChartDisplayPage mapAndChartDisplayPage = new MapAndChartDisplayPage(configurationBean);
        //mapAndChartDisplayPage.perform();


        logger.info("perform(ConfigurationBean configurationBean)<---");
    }

}
