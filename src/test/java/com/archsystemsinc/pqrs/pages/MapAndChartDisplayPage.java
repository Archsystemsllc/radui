package com.archsystemsinc.pqrs.pages;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.archsystemsinc.pqrs.bean.ConfigurationBean;
import com.archsystemsinc.pqrs.utilities.RegressionBrowserBot;

/**
 * Created by Murugaraj Kandaswamy on 7/10/2016.
 */
public class MapAndChartDisplayPage extends BasePage{

    private static Logger logger = Logger.getLogger(UserLoginPage.class);
    private RegressionBrowserBot regBrowserBot = null;

    public MapAndChartDisplayPage() {
    }

    public MapAndChartDisplayPage(ConfigurationBean configurationBean_) {
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
        	
        	this.openURL();

        	// Bar Chart
        	this.selectCriteriaForBarChart();

        	// Line Chart
        	this.selectCriteriaForLineChart();
        	
        	this.selectCriteriaForLineChart();
        	
        	
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }

        return status;
    }

    public boolean openURL() throws Exception {

        boolean status = true;

        ConfigurationBean configurationBean = super.getConfigurationBean();

        try {
            RegressionBrowserBot regBrowserBot = getRegressionBrowserBot();

            regBrowserBot.goToUrl("http://localhost:8080/imapui/mapandchartdisplay/dataAnalysisId/1/subDataAnalysisId/1");
            regBrowserBot.refreshPage();
            regBrowserBot.maximiseWindow();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return status;
    }
    
    
    private boolean selectCriteriaForBarChart() throws Exception {

        boolean status = true;

        try {
            RegressionBrowserBot regBrowserBot = getRegressionBrowserBot();

            // For Bar Chart
            regBrowserBot.selectValueFromDropDown("id;yearLookUpId", "ALL");
            
            regBrowserBot.selectValueFromDropDown("id;reportingOptionLookupId", "CLAIMS");
            
            regBrowserBot.selectValueFromDropDown("id;reportTypeId", "Bar Chart");
            
            regBrowserBot.clickOnElement("id;displayreport");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return status;
    }

    
    private boolean selectCriteriaForLineChart() throws Exception {

        boolean status = true;

        try {
            RegressionBrowserBot regBrowserBot = getRegressionBrowserBot();

            // For Bar Chart
            
            regBrowserBot.selectValueFromDropDown("id;parameterLookupId", "Mental Health HPSA");
            
            regBrowserBot.selectValueFromDropDown("id;reportTypeId", "Line Chart");
            
            regBrowserBot.clickOnElement("id;displayreport");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return status;
    }
    
    private boolean selectCriteriaForMap() throws Exception {

        boolean status = true;

        try {
            RegressionBrowserBot regBrowserBot = getRegressionBrowserBot();

            // For Bar Chart

           regBrowserBot.selectValueFromDropDown("id;yearLookUpId", "ALL");
            
            regBrowserBot.selectValueFromDropDown("id;reportingOptionLookupId", "CLAIMS");
            
            regBrowserBot.selectValueFromDropDown("id;reportTypeId", "Bar Chart");
            
            regBrowserBot.clickOnElement("id;displayreport");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return status;
    }
    

}
