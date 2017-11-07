package com.archsystemsinc.pqrs.constant;

/**
 * This is the constant class for the Year Look Up Names.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/20/2017
 *
 */
public enum YearNameEnum {
	
	BASE_YEAR("Base Year"),
	OPTIONAL_YEAR_1("Option Year 1"),
	OPTIONAL_YEAR_2("Option Year 2"),
	OPTIONAL_YEAR_3("Option Year 3"),
	ALL("ALL");
	
	private final String yearName;
	
	YearNameEnum(String name_){
		yearName = name_;
	}
	
	public String getYearName(){
		return yearName;
	}
	
}
