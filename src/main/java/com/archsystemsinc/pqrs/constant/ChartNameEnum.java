package com.archsystemsinc.pqrs.constant;

/**
 * This is the constant class for the Chart Names.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/22/2017
 *
 */
public enum ChartNameEnum {
	
	MAP("Map"),
	BAR_CHART("Bar Chart"),
	LINE_CHART("Line Chart");
	
	private final String chartName;
	
	ChartNameEnum(String name_){
		chartName = name_;
	}
	
	public String getChartName(){
		return chartName;
	}
	
}
