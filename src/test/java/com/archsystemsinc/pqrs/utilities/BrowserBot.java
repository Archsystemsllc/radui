package com.archsystemsinc.pqrs.utilities;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class BrowserBot {
	private WebDriver driver;
	// Wait for the page to load indefinitely
	// private static final int PAGE_LOAD_TIMEOUT = -1;
	private static final int TIMEOUT_IN_SECONDS = 120;
	private static final int POLL_INTERVAL = 500;
	private static final Logger LOGGER = Logger.getLogger(BrowserBot.class);
	// instance for the chrome driver service
	private static ChromeDriverService service = null;
	protected Properties settingProps;
	Logger log = Logger.getLogger(BrowserBot.class);
	public String url;

	public BrowserBot(WebDriver driver) {
		this.driver = driver;
	}

	private <U> U wait_internal(ExpectedCondition<U> condition) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(RuntimeException.class)
				.withTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL, TimeUnit.MILLISECONDS);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			throw new BrowserBotException(errMessage);
		}
	}

	@SuppressWarnings("unused")
	private <U> U wait_internal(ExpectedCondition<U> condition, int timeoutInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(RuntimeException.class)
				.withTimeout(timeoutInSeconds, TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL, TimeUnit.MILLISECONDS);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			throw new BrowserBotException(errMessage);
		}
	}

	public void init() {
	}

	/**
	 * This is the exposed function to wait Until for a certain criteria
	 * 
	 * @param condition
	 * @return
	 */
	public Boolean wait(ExpectedCondition<Boolean> condition) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(RuntimeException.class)
				.withTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL, TimeUnit.MILLISECONDS);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			LOGGER.warn(errMessage);
			return false;
		} catch (Exception err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			LOGGER.warn(errMessage);
			return false;
		}
	}

	/**
	 * This is the exposed function to wait Until for a certain criteria until
	 * the timeout
	 * 
	 * @param condition
	 * @return
	 */
	public Boolean wait(ExpectedCondition<Boolean> condition, int timeoutInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(RuntimeException.class)
				.withTimeout(timeoutInSeconds, TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL, TimeUnit.MILLISECONDS);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			LOGGER.warn(errMessage);
			return false;
		} catch (Exception err) {
			String errMessage = "Bot encountered a timeout while waiting for a condition,  "
					+ err.getLocalizedMessage();
			LOGGER.warn(errMessage);
			return false;
		}
	}
	/**
	 * 
	 * @param timeoutInSeconds
	 * @param propkey
	 */
	public void waitForElementToLoad(int timeoutInSeconds,String propkey){
		log.warn("waitForElementToLoad..............Start");
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ElementLocator.getBySelector(propkey)));
		log.warn("waitForElementToLoad..............End");
	}
	/**
	 * Method to making the driver to wait implicitly
	 **/
	public void implicitWait(int timeOutInSeconds) {

		driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
	}

	public void maximiseWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * clicks on the given web element which uses click method to click.
	 * findElement method is used to locate the web element with the given
	 * selector which will return the first matching element on the current
	 * page.
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot clickOnElement(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		WebElement element = driver.findElement(locator);
		if (driver.findElement(locator).isDisplayed()) {
			wait_internal(ExpectedConditions.visibilityOfElementLocated(locator),60);
			driver.findElement(locator).click();
		
			log.warn("Clicked on element: " + locator);
		}
		return this;
	}
	
	

	/**
	 * Method to return the current window handle
	 * 
	 * @return the current window handle
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}
	/**
	 * Method to making the driver to wait implicitly
	 */
	public void wait(int i) {
		try{
			
			if (!Thread.interrupted()) 
			{
				Thread.sleep(i);
			}
			else
			{
				System.out.println("thread Interupted");
			      throw new InterruptedException();
			}
		}
		catch(Exception e){
		
			System.out.println(e);
		}
	}

	/**
	 * clicks on the given web element which uses click method to click.
	 * findElement method is used to locate the web element with the given
	 * selector which will return the first matching element on the current
	 * page. Suppose, if the element text is passed as an input instead of the
	 * web element for this function then it will find the web element which has
	 * this inner text and will click on that
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @param elementText
	 *            the text of the element
	 * @return returns the BrowserBot instance
	 **/
	public BrowserBot clickOnElementWithText(String elementLocator, String elementText) {
		By locator = ElementLocator.getBySelector(elementLocator);
		List<WebElement> elementList = driver.findElements(locator);
		int index = 0;
		for (WebElement element : elementList) {
			if (element.getText().trim().equalsIgnoreCase(elementText) && element.isDisplayed()) {
				element.click();
				index++;
				break;
			}
		}
		if (index == elementList.size()) {
			throw new RuntimeException("Could not locate any element described by the locator "
					+ elementLocator.toString() + " with text " + elementText);
		}
		return this;
	}

	/**
	 * simulates typing into an element, which may set its value.
	 * 
	 * @param elementLocator
	 *            � �s the key associated to Locator(LocatorType;LocatorValue)
	 *            that specifies the selector
	 * @param text
	 *            the keys to send to the element
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot type(String elementLocator, String text) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Entering " + text + " into the " + locator + " text field");
		WebElement element = driver.findElement(locator);
		element.sendKeys(text);
		return this;
	}

	/**
	 * simulates typing into an element, which may set its value.
	 * 
	 * @param elementLocator
	 *            � �s the key associated to Locator(LocatorType;LocatorValue)
	 *            that specifies the selector
	 * @param text
	 *            the keys to send to the element
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot type(String elementLocator, String text, Keys keys) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Entering " + text + " into the " + locator + " text field and pressing " + keys);
		WebElement element = driver.findElement(locator);
		element.sendKeys(text);
		element.sendKeys(keys);
		return this;
	}

	/**
	 * navigates to a particular page with the given url. It uses driver get
	 * method to load a new web page in the current browser window with the
	 * given url
	 * 
	 * @param URL
	 *            the URL where the browser has to navigate
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot goToUrl(String url) {
		log.info("Loading the URL:" + url);
		this.driver.get(url);
		return this;
	}

	/**
	 * Retrieves the specified css attribute for a given web element.
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @param attribute
	 *            the attribute for the given element
	 * @return String the attribute pertaining to the element
	 */
	public String getCssAttribute(String elementLocator, String attribute) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Getting CSS value of " + attribute + " from the locator " + locator);
		return driver.findElement(locator).getCssValue(attribute);
	}

	/**
	 * Retrieves the specified attribute for a given web element
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @param attribute
	 *            the attribute for the given element
	 * @return String the attribute pertaining to the element
	 */
	public String getAttribute(String elementLocator, String attribute) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Getting CSS attribute of " + attribute + " from the locator " + locator);
		return driver.findElement(locator).getAttribute(attribute);
	}

	/**
	 * Submits the form via the specified element. If this current element is a
	 * form, or an element within a form, then this will be submitted to the
	 * remote server. If this causes the current page to change, then this
	 * method will block until the new page is loaded
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot submit(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Submitting the Form");
		driver.findElement(locator).submit();
		return this;
	}

	/**
	 * Retrieves the number of windows that is currently opened. It uses size
	 * method of driver getWindowHandles method to get the count of the browser
	 * windows
	 * 
	 * @return int the count of browser windows
	 */
	public int getNumberOfOpenWindows() {
		return driver.getWindowHandles().size();
	}

	/**
	 * navigate is an abstraction allowing the driver to access the browser's
	 * history and to navigate to a given URL. navigateBack is used to navigate
	 * to the previous page. It uses back method of driver navigate method for
	 * this purpose
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot navigateBack() {
		driver.navigate().back();
		log.info("Navigating to the previous page");
		return this;
	}

	/**
	 * navigate is an abstraction allowing the driver to access the browser's
	 * history and to navigate to a given URL. navigateForward is used to
	 * navigate to the next page. It uses forward method of driver navigate for
	 * this purpose
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot navigateForward() {
		driver.navigate().forward();
		log.info("Navigating to the next page");
		return this;
	}

	/**
	 * selectDropDown is used to select an option from the given drop down web
	 * element. It will set the value based on the visible text provided
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue)
	 * @param visibleText
	 *            the option to select
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot selectValueFromDropDown(String elementLocator, String visibleText) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Selecting " + visibleText + " from the DropDown");
		WebElement dropDownElement = driver.findElement(locator);
		Select dropDownSelect = new Select(dropDownElement);
		dropDownSelect.selectByVisibleText(visibleText);
		return this;
	}

	public BrowserBot selectValueFromDropDown(String elementLocator, int index) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Selecting " + index + " from the DropDown");
		WebElement dropDownElement = driver.findElement(locator);
		Select dropDownSelect = new Select(dropDownElement);
		dropDownSelect.selectByIndex(index);
		return this;
	}

	public BrowserBot selectValueFromDropDownByOptionValue(String elementLocator, String optionValue_) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Selecting Value (" + optionValue_ + ") from the DropDown");
		WebElement dropDownElement = driver.findElement(locator);
		Select dropDownSelect = new Select(dropDownElement);
		dropDownSelect.selectByValue(optionValue_);
		return this;
	}
	
	public List<WebElement> getAllOptionsFromDropDown(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		WebElement dropDownElement = driver.findElement(locator);
		Select dropDownSelect = new Select(dropDownElement);
		return dropDownSelect.getOptions();
	}
	
	/**
	 * method to perform the drag and drop action
	 * 
	 * @param fromElementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @param toElementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot dragAndDrop(String fromElementLocator, String toElementLocator) {
		By fromLocator = ElementLocator.getBySelector(fromElementLocator);
		By toLocator = ElementLocator.getBySelector(toElementLocator);
		Actions action = new Actions(this.driver);
		action.dragAndDrop(driver.findElement(fromLocator), driver.findElement(toLocator)).build().perform();
		return this;
	}

	/**
	 * Forces the refresh operation. It will load the driver instance in the
	 * Actions class and then will perform the refresh operation
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot forceRefresh() {
		Actions action = new Actions(this.driver);
		log.info("Forcefully refreshing the page");
		action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
		return this;
	}

	/**
	 * Method to press the key. It uses sendKeys method to send the keys to the
	 * given web element
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @param key
	 *            the keys to send to the element
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot pressKey(String elementLocator, Keys key) {
		By locator = ElementLocator.getBySelector(elementLocator);
		driver.findElement(locator).sendKeys(key);
		return this;
	}

	/**
	 * A helper method to see if the element is enabled . An Element might be
	 * present in a hTML page However it might not be enabled We use this method
	 * to check and see if the element is enabled or not
	 * 
	 * @param elementSelector
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return returns true if the element is enabled
	 */
	public Boolean isElementEnabled(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		return driver.findElement(locator).isEnabled();
	}

	/**
	 * switches the focus to the window based on the title. It will get all the
	 * window handles using driver getWindowHandles method then focus to the
	 * corresponding window with the given title
	 * 
	 * @param titleOfNewWindow
	 *            the string that specifies the title of the window
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot switchToWindowByTitle(String titleOfNewWindow) {
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			driver.switchTo().window(windowHandle);
			if (driver.getTitle().contains(titleOfNewWindow)) {
				break;
			}
		}
		return this;
	}

	/**
	 * Returns focus to the main browser window by using defaultContent method
	 * of the driver switchTo method
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot switchToDefaultContent() {
		driver.switchTo().defaultContent();
		return this;
	}

	/**
	 * Refreshes the page
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot refreshPage() {
		driver.navigate().refresh();
		return this;
	}

	/**
	 * Performs double click on an element. Action and Actions classes in
	 * interactions are used
	 * 
	 * @param elementSelector
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot doubleClick(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(locator);
		Action hoverOverRegistrar = (Action) builder.doubleClick(element);
		hoverOverRegistrar.perform();
		return this;
	}

	/**
	 * Deletes all the cookies. It uses deleteAllCookies of driver manage method
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot clearCookies() {
		this.driver.manage().deleteAllCookies();
		return this;
	}

	/**
	 * Returns the displayed element from a list of similarly named elements by
	 * getting the element locator as the input value
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return returns the displayed element
	 */
	@SuppressWarnings("unused")
	private WebElement getDisplayedElement(String elementLocator) {
		log.info("Finding the displayed Element for the locator provided--" + elementLocator);
		By locator = ElementLocator.getBySelector(elementLocator);
		List<WebElement> elementList = findElements(locator);
		for (WebElement element : elementList) {
			if (element.isDisplayed())
				return element;
		}
		throw new BrowserBotException("Element not found--" + elementLocator + " displayed");
	}

	/**
	 * Finds elements within an element using ByChained
	 * 
	 * @param elementLocators
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies all the selectors
	 * @return the list of web elements
	 */
	/*
	 * private List<WebElement> findElements(String... elementLocators) { By[]
	 * bys = new By[elementLocators.length];
	 * 
	 * // Wait for the root element until the timeout
	 * this.wait(Until.elementIsDisplayed(elementLocators[0])); ByChained by =
	 * new ByChained(bys); return by.findElements(driver); }
	 */
	/**
	 * Gets a string representing the current URL of the page which is currently
	 * loaded in the browser
	 * 
	 * @return String the current URL
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Retrieves the size for the specified element.
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return int the size for the element
	 */
	public int getListSize(String propKey) {
		By locator = ElementLocator.getBySelector(propKey);
		return findElements(locator).size();
	}

	/**
	 * Finds all the Web Elements that matches the given by selector
	 * 
	 * @param by
	 *            By object to identify the element(s)
	 * @return List<WebElement> the list of web elements that is found
	 * @throws SupportException
	 *             if no element is found
	 */
	public List<WebElement> findElements(By by) throws BrowserBotException {
		try {
			return driver.findElements(by);
		} catch (NoSuchElementException e) {
			String msg = "Element could not be located " + by.toString();
			log.info(msg);
			throw new BrowserBotException(msg);
		}
	}

	public WebElement findElement(By locator) {

		try {

			return driver.findElement(locator);
		} catch (NoSuchElementException e) {
			String msg = "Element could not be located " + locator.toString();
			log.info(msg);
			throw new BrowserBotException(msg);
		}
	}

	public WebElement findElement(String locator) {

		try {

			By by = ElementLocator.getBySelector(locator);

			return driver.findElement(by);
		} catch (NoSuchElementException e) {
			String msg = "Element could not be located " + locator.toString();
			log.info(msg);
			throw new BrowserBotException(msg);
		}
	}

	/**
	 * Closes the current pop up window
	 * 
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot closePopupWindow() {
		driver.close();
		for (String name : driver.getWindowHandles()) {
			driver.switchTo().window(name);
			log.info("popup window closed : " + name);
			break;
		}
		return this;
	}

	/**
	 * Closes the given pop up window without switching to any other window
	 * 
	 * @param windowId
	 *            the window id that should be closed
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot closePopupWindow(String windowID) {
		driver.switchTo().window(windowID).close();
		return this;
	}
	
	public void quitAllDriverWindows(WebDriver driver){
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.quit();
		}
	}

	/**
	 * Closes the given popup window and switches to random window
	 * 
	 * @param windowID
	 *            the window id that should be closed
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot closepopUpAndSwitchtoParent(String windowID) {
		closePopupWindow(windowID);
		for (String name : driver.getWindowHandles()) {
			driver.switchTo().window(name);
			break;
		}
		return this;
	}

	/**
	 * Determines if a specific element is present
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementPresent(String propKey) {
		wait(1000);
		By locator = ElementLocator.getBySelector(propKey);
		log.debug("Checking the presence of the Element: " + propKey + " : " + propKey);
		return isElementPresent(locator);
	}

	
	/**
	 * Determines if a specific element is present in specified timeout
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementPresent(String propKey,int timeoutInSec) {
		log.debug("Checking the presence of the Element: " + propKey + " : " + propKey);
		By locator = ElementLocator.getBySelector(propKey);
		boolean found=false;int i=0;
		while (!found){
			if (isElementPresent(locator)){
				System.out.println("element present>>>>>");
				found=true;
			}else if (i==timeoutInSec){
				log.debug("Element "+propKey + "not found in wait time !");
				break;
			}else{
				try{
					i=i+1;
					Thread.sleep(1000);
				}catch(Exception e){
					//auto -generated 
				}
			}
		}
		return found;
	}
	 public boolean isElementPresent1(By by) 
	  {
	  	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  	try {
	  		//driver.findElement(by).sendKeys("");
	  		wait(1000);
	        driver.findElement(by);
	        return true;
	      } catch (NoSuchElementException e) {
	      	System.out.println("Caught NoSuchElementException");
	      	return false;
	      
	      }
	      catch (ElementNotVisibleException e) {
	      	System.out.println("Caught ElementNotVisibleException");
	      	return false;
	      	
	        }
	     
	    }  

	
	/**
	 * Determines if a specific element is visible
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementVisible(String propKey) {
		By locator = ElementLocator.getBySelector(propKey);
		log.debug("Checking the presence of the Visble: " + propKey + " : " + propKey);
		return isElementVisible(locator);
	}

	/**
	 * Determines if a specific element is visible
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementChecked(String propKey) {
		By locator = ElementLocator.getBySelector(propKey);
		log.debug("Checking the presence of the Visble: " + propKey + " : " + propKey);
		return isElementChecked(locator);
	}

	/**
	 * Determines if an element is present with the given by selector
	 * 
	 * @param by
	 *            By locator to find the element
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementPresent(By by) {
		try {
			WebElement element = driver.findElement(by);
			if (element != null) {
				log.debug("Element is present: " + by.toString());
				return true;
			}
			log.warn("Element is NOT present: " + by.toString());
			return false;
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Determines if an element is present with the given by selector
	 * 
	 * @param by
	 *            By locator to find the element
	 * @return boolean true/false if element is found/not found
	 */
	private boolean isElementChecked(By by) {
		try {
			WebElement element = driver.findElement(by);
			if (element.isSelected()) {
				log.debug("Element is checked: " + by.toString());
				return true;
			}
			log.warn("Element is NOT checked: " + by.toString());
			return false;
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Determines if an element is visible with the given by selector
	 * 
	 * @param by
	 *            By locator to find the element
	 * @return boolean true/false if element is found/not found
	 */
	public boolean isElementVisible(By by) {
		try {
			WebElement element = driver.findElement(by);
			if (element.isDisplayed()) {
				log.debug("Element is present: " + by.toString());
				return true;
			}
			log.warn("Element is NOT present: " + by.toString());
			return false;
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * clicks on an Nth element if there are many elements in the page with
	 * similar locators. This is useful in selecting an element from a list of
	 * elements (which have same IDs) but say different texts
	 * 
	 * @param indexOfElementToClick
	 *            index of the element which is to be clicked
	 * @param elementLocators
	 *            all the element locators
	 * @return returns the BrowserBot instance
	 */
	/*
	 * public BrowserBot clickOnNthElement(int indexOfElementToClick, String...
	 * elementLocators) { switchToDefaultContent(); List<WebElement> elementList
	 * = findElements(elementLocators); if (elementList == null ||
	 * elementList.size() == 0) throw new CustomException(
	 * "The Element could not be located");
	 * 
	 * elementList.get(indexOfElementToClick).click(); return this; }
	 */
	/**
	 * Method to switch to a parent window. It will get all the window handles
	 * and will iterate over the handles and then focus the particular window
	 * checking that window is not the parent window
	 * 
	 * @param parentWindowId
	 *            the id of the parent window
	 * @return returns the BrowserBot instance
	 * 
	 */
	public BrowserBot switchToParentWindow(String parentWindowId) {
		String windowId = "";
		// WebDriverExtended.driver.close();
		Set<String> set = driver.getWindowHandles();
		log.info("Number of windows opened: " + set.size());
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			windowId = (String) iterator.next();
			if (windowId.equals(parentWindowId)) {
				log.info("Switching to the window: " + parentWindowId);
				driver.switchTo().window(parentWindowId);
			}
			log.info("windowId" + windowId);
		}
		return this;
	}

	/**
	 * Method for selecting multiple options from the list box. It uses Actions
	 * class for keyDown and keyUp methods
	 * 
	 * @param listBoxLocator
	 *            the locator of the list box
	 * @param indexes
	 *            the indexes of all the items
	 * @return returns the BrowserBot instance
	 */
	public BrowserBot selectMultipleListItems(String listBoxLocator, int[] indexes) {
		By locator = ElementLocator.getBySelector(listBoxLocator);
		Actions action = new Actions(this.driver);
		WebElement listItem = driver.findElement(locator);
		List<WebElement> listOptions = listItem.findElements(By.tagName("option"));
		action.keyDown(Keys.CONTROL).perform();
		for (int i : indexes) {
			listOptions.get(i).click();
		}
		action.keyUp(Keys.CONTROL).perform();
		return this;
	}

	/**
	 * Switch to the pop up window by getting parent window as the input value
	 * It gets the window handles and compares it with parent window to select
	 * the new pop up window other than parent window
	 * 
	 * @param ID
	 *            of the parent window
	 * @return the instance of the BrowserBot class
	 */
	public BrowserBot switchToPopUpWindow(String parentWindowId) {
		String windowId = "";
		Set<String> set = driver.getWindowHandles();
		log.info("Number of windows opened: " + set.size());
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			windowId = (String) iterator.next();
			if (!windowId.equals(parentWindowId)) {
				log.info("Switching to the window: " + parentWindowId);
				driver.switchTo().window(windowId);
			}
			LOGGER.info("Popup windowId" + windowId);
		}
		return this;
	}

	/**
	 * Method for switching to the frame of the given frame id
	 * 
	 * @param frameId
	 * @return instance of the BrowserBot class
	 */
	public BrowserBot switchToFrame(int frameId) {
		log.info("Switching to the frame: " + frameId);
		driver.switchTo().frame(frameId);
		return this;
	}

	public BrowserBot switchToFrame(String frameName) {
		log.info("Switching to the frame: " + frameName);
		driver.switchTo().frame(frameName);
		return this;
	}

	/**
	 * gets the visible inner text of the specified web element, including
	 * sub-elements,without any leading or trailing whitespace
	 * 
	 * @param elementLocator
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the selector
	 * @return the text of the given element
	 */
	public String getText(String elementLocator) {
		By locator = ElementLocator.getBySelector(elementLocator);
		log.info("Getting the text of the element: " + locator);
		return this.driver.findElement(locator).getText();
	}

	/**
	 * Returns the text from the alert window
	 * 
	 * @return the text of the alert
	 */
	public String getAlertText() {
		log.info("Getting the Alert text");
		return driver.switchTo().alert().getText();
	}

	/**
	 * Accepts the alert window
	 */
	public void acceptAlert() {
		log.info("Confirming the operation");
		driver.switchTo().alert().accept();
	}

	/**
	 * Dismisses the alert window
	 */
	public void dismissAlert() {
		log.info("Cancelling the operation");
		driver.switchTo().alert().dismiss();
	}

	/**
	 * Executes the JavaScript of the given script using the object of
	 * JavaScriptExecutor and returns the resulting text The executeScript
	 * method of JavaScriptExecutor is used
	 * 
	 * @param Javascript
	 *            that is to be executed
	 * @return text
	 */
	public String executeJavaScript(String script) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String text = (String) jsExecutor.executeScript(script);
		return text;
	}

	public void executeJavaScript(String script, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript(script, element);

	}

	
	
	/**
	 * Gets the title of the current window.
	 * 
	 * @return String the title of the window
	 */
	public String getTitle() {
		log.info("Getting the title of the page");
		return driver.getTitle();
	}

	/**
	 * This is a helper function/class to determine if a page is loaded
	 * properly, We use the document.readystate to determine if the page is
	 * loaded correctly
	 * 
	 * This prevents the webdriver from doing automation when a page "component"
	 * is loaded partially.Without this the automation will be unreliable. This
	 * is analogous to the selenium.waitforPageToLoad, Except that this prints a
	 * warning message and tries to continue with automation when the timeout
	 * expires
	 * 
	 * @return Boolean value indicating if the page has loaded or not
	 */
	public Boolean isPageLoaded() {
		final String javaScriptFunction = "function f(){return document.readyState;} return f();";
		// We describe the condition to wait- Wait until the page load is
		// "Complete"
		Function<WebDriver, Boolean> condition = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver d) {
				String result = (String) ((JavascriptExecutor) d).executeScript(javaScriptFunction);
				LOGGER.debug("The page is in " + result + " state");
				if (result.equalsIgnoreCase("complete"))
					return true;
				return false;
			}
		};
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(2, TimeUnit.SECONDS)
				.withTimeout(180, TimeUnit.SECONDS).ignoring(RuntimeException.class);
		try {
			return wait.until(condition);
		} catch (TimeoutException err) {
			LOGGER.warn("THE PAGE IS NOT LOADED EVEN AFTER THE TIMEOUT, OF 180 SECONDS"
					+ "THIS COULD MEAN THAT THE AUTOMATION MIGHT FAIL OR BE UNRELIABLE");
			return false;
		}
	}

	/**
	 * Quits the web driver object and closes the every associated window
	 */
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			// Stop the chrome driver service if needed
			if (service != null) {
				service.stop();
			}
			LOGGER.debug("The web driver is quit successfully");
		}
	}

	/**
	 * Adds the given window to the windowHandle list if not already added
	 * 
	 * @param windowHandle
	 *            the window handle of the window to be added to the list
	 */
	/**
	 * Returns the windowset containing all the opened window handles
	 * 
	 * @return The set of windows in the windowhandle list
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/**
	 * Gets the current window title
	 * 
	 * @return the current window title
	 */
	public String getWindowTitle() {
		return driver.getTitle();
	}

	/**
	 * Switches to window having the specified element
	 * 
	 * @param elementLocator
	 *            the locator for the element
	 * @return True if switch is performed. False if no window with the expected
	 *         element is found
	 */
	public boolean switchToWindowWithElement(String elementLocator) {
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			if (isElementPresent(elementLocator))
				return true;
		}
		return false;
	}

	/**
	 * Switches to window having the specified url part
	 * 
	 * @param URLPart
	 *            the part of url to match
	 * @return True if switch is performed. False if no window with the expected
	 *         url part is found
	 */
	public boolean switchToWindowWithURLPart(String URLPart) {
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			if (getCurrentUrl().contains(URLPart))
				return true;
		}
		return false;
	}

	public String getPageSource() {
		return this.getPageSource();
	}

	/**
	 * Clear the specified text box.
	 * 
	 * @param propKey
	 *            the key associated to Locator(LocatorType;LocatorValue) that
	 *            specifies the by selector
	 * @return the instance of the BrowserBot class
	 */
	public BrowserBot clearTextBox(String elementLocator) {
		driver.findElement(ElementLocator.getBySelector(elementLocator)).clear();
		return this;
	}

	public void sendKeys(String text, String locator) {
		By elementLocator = ElementLocator.getBySelector(locator);
		//isElementVisible(elementLocator);
		clickOnElement(locator);
		clearTextBox(locator);
		driver.findElement(elementLocator).sendKeys(text);
	}
	
	public void sendKeysForDropDown(String text, String locator) {
		By elementLocator = ElementLocator.getBySelector(locator);
		isElementVisible(elementLocator);
		clickOnElement(locator);
		//clearTextBox(locator);
		driver.findElement(elementLocator).sendKeys(text);
	}
	
	public void sendEnterKeys(Keys keys, String locator) {
		By elementLocator = ElementLocator.getBySelector(locator);
		isElementVisible(elementLocator);
		clickOnElement(locator);
		clearTextBox(locator);
		driver.findElement(elementLocator).sendKeys(Keys.ENTER);
	}

	public void sendKeys(String locator, Keys keys, Keys keys1) {
		By elementLocator = ElementLocator.getBySelector(locator);
		clickOnElement(locator);
		driver.findElement(elementLocator).sendKeys(Keys.DOWN, Keys.ENTER);

	}

	public void sendKeys(String locator, Keys keys) {
		By elementLocator = ElementLocator.getBySelector(locator);
		clickOnElement(locator);
		driver.findElement(elementLocator).sendKeys(keys);

	}

	public List<WebElement> findElements(String locator) throws BrowserBotException {
		try {
			By locator1 = ElementLocator.getBySelector(locator);
			return driver.findElements(locator1);
		} catch (NoSuchElementException e) {
			String msg = "Element could not be located " + locator.toString();
			log.info(msg);
			throw new BrowserBotException(msg);
		}
	}
	
	/**
	 * Captures screenshot of the screen under the folder path stored in
	 * screenshotBaseFolder variable
	 * 
	 * @param fileName
	 */
	public  void captureScreen(String fileName) {
		String filePath = "./test-output/screenshots/"+fileName+".jpg";
		try {

		    File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		   
		    org.apache.commons.io.FileUtils.copyFile(srcFile, new File(filePath));

		} catch (Exception e) {
			System.out.println("Exception thrown while capturing screenshot '" + filePath + "' : " + e.toString());
		}
	}
	
	
	public void openNewTab(){
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	}
	
	/**
	 * 
	 * @return driver WebDriver
	 */
	public WebDriver getWebDriver() {
		return this.driver;
	}

}
