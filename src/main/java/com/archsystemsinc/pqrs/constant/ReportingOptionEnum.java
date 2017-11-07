package com.archsystemsinc.pqrs.constant;

/**
 * This is the constant class for the Reporting Option Look Up Names.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/20/2017
 *
 */
public enum ReportingOptionEnum {

	CLAIMS("CLAIMS"),
	EHR("EHR"),
	REGISTRY("REGISTRY"),
	GPROWI("GPROWI"),
	QCDR("QCDR");
	
	private final String reportingOptionName;
	
	ReportingOptionEnum(String name_){
		reportingOptionName = name_;
	}
	
	public String getReportingOptionName(){
		return reportingOptionName;
	}
	
}
