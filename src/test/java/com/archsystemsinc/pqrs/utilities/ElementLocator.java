package com.archsystemsinc.pqrs.utilities;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


public class ElementLocator extends By {
	/**
	 * Reads and returns the appropriate selectors specified in
	 * selenium.properties. Value of the specified key follows the following
	 * format: <typeOfSelector>=<stringToLocateElement> Where
	 * <stringToLocateElement> can have <____> within it and be replaced by the
	 * dynamic value specified.
	 * 
	 * @param propKey
	 *            the key associated to selenium.properties that specifies the
	 *            by selector
	 * @param dynamic
	 *            the Map that contains the token,value pair to replace prior to
	 *            locating the element
	 * @return By selector specified in selenium.properties after parsing the
	 *         value and replacing any instance of the dynamic values
	 */
	public static By getBySelector(String propKey) {
		// get the value from selenium.properties and split the type and value
		String[] split = propKey.split(";");
		String type = split[0];
		// generate the By selector based on the type
		if (type.equalsIgnoreCase("id")) {
			return ElementLocator.id(split[1]);
		} else if (type.equalsIgnoreCase("css")) {
			return ElementLocator.cssSelector(split[1]);
		} else if (type.equalsIgnoreCase("tagname")) {
			return ElementLocator.tagName(split[1]);
		} else if (type.equalsIgnoreCase("classname") || type.equalsIgnoreCase("class")) {
			return ElementLocator.className(split[1]);
		} else if (type.equalsIgnoreCase("name")) {
			return ElementLocator.name(split[1]);
		} else if (type.equalsIgnoreCase("xpath")) {
			return ElementLocator.xpath(split[1]);
		} else if (type.equalsIgnoreCase("link")) {
			return ElementLocator.linkText(split[1]);
		} else {
			throw new BrowserBotException("Invalid element locator parameter -" + propKey);
		}
	}

	/**
	 * 
	 * This needs to be fixed here
	 */
	@Override
	public List<WebElement> findElements(SearchContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}