package com.archsystemsinc.pqrs.utilities;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by mkandaswamy on 3/7/2016.
 */
public class RegressionBrowserBot extends BrowserBot {

    private static Logger logger = Logger.getLogger(RegressionBrowserBot.class);

    public RegressionBrowserBot(WebDriver webDriver_) {
        super(webDriver_);
    }

    public boolean clickOnElementJSExecuterOption(String locator_) throws Exception {
        boolean status = true;
        try {
            super.clickOnElement(locator_);
        } catch (WebDriverException webDriverException) {
            if (webDriverException.getMessage().contains("Element is not clickable at point")){
                clickOnElementUsingJavaScriptExecuter(locator_);
                logger.info("Clicked on element(Re-try option of using Java Script Executer): " + locator_);
            } else {
                throw webDriverException;
            }
        }
        return status;
    }

    private void clickOnElementUsingJavaScriptExecuter(String locator_) throws Exception {
        WebElement webElement = super.findElement(locator_);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
        jsExecutor.executeScript("arguments[0].click();", webElement);
    }

    public boolean sendKeysJSExecuterOption(String text_, String elementLocator_) throws Exception {
        boolean status = true;
        BrowserBot browserBot = null;
        try {
            browserBot = new BrowserBot(getWebDriver());
            browserBot.sendKeys(text_, elementLocator_);
        } catch (WebDriverException webDriverException) {
            if (webDriverException.getMessage().contains("Element is not clickable at point")){
                sendKeysUsingJavaScriptExecuter(text_, elementLocator_);
                logger.info("Clicked on element(Re-try option of using Java Script Executer): " + elementLocator_);
            } else {
                throw webDriverException;
            }
        }
        return status;
    }

    public void sendKeysUsingJavaScriptExecuter(String text_, String elementLocator_) throws Exception {
        BrowserBot browserBot = new BrowserBot(getWebDriver());;
        WebElement webElement = browserBot.findElement(elementLocator_);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();
//        jsExecutor.executeScript("arguments[0].clear();", webElement);
        jsExecutor.executeScript("arguments[0].value=arguments[1]", webElement, text_);
    }

    public boolean selectValueFromDropDownJSExecuterOption(String elementLocator_, String visibleText_) throws Exception {
        boolean status = true;
        BrowserBot browserBot = null;
        try {
            browserBot = new BrowserBot(getWebDriver());
            browserBot.selectValueFromDropDown(elementLocator_, visibleText_);
        } catch (WebDriverException webDriverException) {
            if (webDriverException.getMessage().contains("Element is not clickable at point")){
                selectValueFromDropDownUsingActions(elementLocator_,visibleText_);
            } else {
                throw webDriverException;
            }
        }
        return status;
    }

/*    public boolean selectValueFromDropDownByOptionValue(String elementLocator_, String optionValue_) throws Exception {
        boolean status = true;
        BrowserBot browserBot = null;
        try {
            browserBot = new BrowserBot(getWebDriver());
            browserBot.selectValueFromDropDownByOptionValue(elementLocator_, optionValue_);
        } catch (WebDriverException webDriverException) {
            throw webDriverException;
        }
        return status;
    }*/

    public boolean selectValueFromDropDownJSExecuterOption(String elementLocator_, int index_) throws Exception {
        boolean status = true;
        BrowserBot browserBot = null;
        try {
            browserBot = new BrowserBot(getWebDriver());
            browserBot.selectValueFromDropDown(elementLocator_, index_);
        } catch (WebDriverException webDriverException1) {
            if (webDriverException1.getMessage().contains("Element is not clickable at point")){
                try {
                    selectValueFromDropDownUsingActions(elementLocator_, index_);
                } catch (WebDriverException webDriverException2) {
                    if (webDriverException2.getMessage().contains("Element is not clickable at point")){
                        logger.info("Element is not clickable at point:with both Actions and JSExecuter Option");
                        try {
                            selectValueFromDropDownUsingJavaScriptExecutor(elementLocator_, index_);
                        } catch (Exception excpetion){
                            throw excpetion;
                        }

                    }
                }

            } else {
                throw webDriverException1;
            }
        }
        return status;
    }

    public int getIndexOfVisibleTextFromDropDown(String elementLocator_, String visibleText_) throws WebDriverException{
        logger.info("getIndexOfVisibleTextFromDropDown(String elementLocator_, String visibleText_)-->");
        int index = 0;
        BrowserBot browserBot = new BrowserBot(getWebDriver());
        try {
            List<WebElement> dropDownWebElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
            for (WebElement dropDownWebElement : dropDownWebElementList) {
                String dropDownElementText = dropDownWebElement.getText();
                if (visibleText_!=null && visibleText_.trim().equalsIgnoreCase(dropDownElementText.trim())){
                    break;
                } else {
                    index = index + 1;
                }
            }
        } catch (WebDriverException webDriverException) {
            logger.info("WebDriverException::"+webDriverException.getMessage());
            throw webDriverException;
        }
        logger.info("getIndexOfVisibleTextFromDropDown(String elementLocator_, String visibletext_)<--::index:"+index);
        return index;
    }


    public String getAttributeValueOfVisibleTextFromDropDown(String elementLocator_, String visibleText_) throws WebDriverException{
        logger.info("getIndexOfVisibleTextFromDropDown(String elementLocator_, String visibleText_)-->");
        String attributeValue = null;
        BrowserBot browserBot = new BrowserBot(getWebDriver());
        try {
            List<WebElement> dropDownWebElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
            for (WebElement dropDownWebElement : dropDownWebElementList) {
                String dropDownElementText = dropDownWebElement.getText();
                if (visibleText_!=null && visibleText_.trim().equalsIgnoreCase(dropDownElementText.trim())){
                    attributeValue = dropDownWebElement.getAttribute("value");
                    break;
                }
            }
        } catch (WebDriverException webDriverException) {
            logger.info("WebDriverException::"+webDriverException.getMessage());
            throw webDriverException;
        }
        logger.info("getIndexOfVisibleTextFromDropDown(String elementLocator_, String visibletext_)<--::attributeValue:"+attributeValue);
        return attributeValue;
    }

    private void selectValueFromDropDownUsingActions(String elementLocator_, String visibleText_) throws Exception{
        logger.info("selectValueFromDropDownUsingActions(String elementLocator_, String visibleText_)-->");
        BrowserBot browserBot = new BrowserBot(getWebDriver());
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement(browserBot.findElement(elementLocator_)).click().perform();
        List<WebElement> dropDownWebElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
        for (WebElement dropDownWebElement : dropDownWebElementList) {
            actions.sendKeys(Keys.DOWN).click();
            String optionString = dropDownWebElement.getText();
            if (visibleText_.trim().equalsIgnoreCase(optionString)) {
                actions.moveToElement(dropDownWebElement).click().perform();
                logger.info("Clicked on element(Re-try option of Actions): " + elementLocator_);
                break;
            }else {
            }
        }
        logger.info("selectValueFromDropDownUsingActions(String elementLocator_, String visibleText_)<--");
    }

    private void selectValueFromDropDownUsingActions(String elementLocator_, int index_) throws Exception{
        logger.info("selectValueFromDropDownUsingActions(String elementLocator_, int index_)-->");
        try {
            BrowserBot browserBot = new BrowserBot(getWebDriver());
            Actions actions = new Actions(getWebDriver());
            actions.moveToElement(browserBot.findElement(elementLocator_)).click().perform();
            List<WebElement> dropDownWebElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
            for (int i = 0 ; i < dropDownWebElementList.size() ; i++) {
                actions.sendKeys(Keys.DOWN).click();
                if (i == index_){
                    actions.moveToElement(dropDownWebElementList.get(i)).click().perform();
                    logger.info("Clicked on element(Re-try option of using Actions): " + elementLocator_);
                    break;
                }
            }
        } catch (Exception exception) {
            throw exception;
        }

        logger.info("selectValueFromDropDownUsingActions(String elementLocator_, int index_)<--");
    }


    private void selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, String visibleText_) throws Exception{
        logger.info("selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, String visibleText_)-->");
        BrowserBot browserBot = new BrowserBot(getWebDriver());
        List<WebElement> dropDownWebElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
        for (WebElement dropDownWebElement : dropDownWebElementList) {
            if (dropDownWebElement.getText().equals(visibleText_)) {
                JavascriptExecutor jsExecuter = (JavascriptExecutor) getWebDriver();
                jsExecuter.executeScript("arguments[0].click();", dropDownWebElement);
                logger.info("Clicked on element(Re-try option of using Java Script Executer): " + elementLocator_);
                break;
            }
        }
        logger.info("selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, String visibleText_)<--");
    }

    private void selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, int index_) throws Exception{
        logger.info("selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, int index_)-->");
        try {
            BrowserBot browserBot = new BrowserBot(getWebDriver());
            List<WebElement> webElementList = new Select(browserBot.findElement(elementLocator_)).getOptions();
            for (int i = 0 ; i < webElementList.size() ; i++) {
                if (i == index_){
                    JavascriptExecutor jsExecuter = (JavascriptExecutor) getWebDriver();
                    jsExecuter.executeScript("arguments[0].selectedIndex=arguments[1]", webElementList.get(i), index_);
                    logger.info("Clicked on element(Re-try option of using Java Script Executer): " + elementLocator_);
                    break;
                }
            }
        } catch (Exception exception) {
            throw exception;
        }

        logger.info("selectValueFromDropDownUsingJavaScriptExecutor(String elementLocator_, int index_)<--");
    }

/*
    public void enterDOBUsingJavaScriptExecuter(String text_, String locator_) {
        BrowserBot browserBot = new BrowserBot(configurationBean.getWebDriver());
        if (browserBot.isElementPresent(locator_, waitTimeOutInSec))
            browserBot.clearTextBox(locator_);
        if (browserBot.isElementPresent(locator_, waitTimeOutInSec)) {
            JavascriptExecutor jsExecuter = (JavascriptExecutor) configurationBean.getWebDriver();
            jsExecuter.executeScript("arguments[0].value=arguments[1]", browserBot.findElement(locator_), text_);
        }
    }
*/

    public boolean isElementPresent(String propKey) {
        By locator = ElementLocator.getBySelector(propKey);
        if (isElementPresent(locator) && findElement(propKey).isDisplayed()) {
            return true;
        }
        return false;
    }


    public void sendKeysWithDelay(String text_, String elementLocator_, int delayInSeconds_) throws Exception {
        BrowserBot browserBot = null;
        try {
            browserBot = new BrowserBot(getWebDriver());

            browserBot.clickOnElement(elementLocator_);
            browserBot.clearTextBox(elementLocator_);

            // Added a Delay before entering Text Value.
            Thread.sleep(delayInSeconds_*1000);

            browserBot.findElement(elementLocator_).sendKeys(text_);

        } catch (Exception exception_) {
            throw exception_;
        }
    }

    public void waitUntilElementPresent(final String elementLocator_, int delayInSeconds_) {

        final BrowserBot browserBot = new BrowserBot(getWebDriver());

        browserBot.wait(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                try {
                    browserBot.isElementPresent(elementLocator_);
                    return true;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Exception in Review Page >>>>> : "+e.getMessage());
                    return false;
                }
            }
        }, delayInSeconds_);
    }

}
