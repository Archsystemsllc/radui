package com.archsystemsinc.rad.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

@Service
public class UtilityFunctions {
	
	public HashMap sortByValues(HashMap map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });

	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }
	
	private static final SimpleDateFormat usEstDateFormatFullDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	
	private static final SimpleDateFormat usEstDateFormatOnlyDate = new SimpleDateFormat("MM/dd/yyyy");
	
	private static final SimpleDateFormat usEstDateFormatMonthYearReportDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

	TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
	public String convertToStringFromDate(Date dateValue, String dateType) {				
		
		String dateValueString = "";
		if(dateValue!=null) {
			if(dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_FULL)) {
				//usEstDateFormatFullDate.setTimeZone(tzInAmerica);
				dateValueString = usEstDateFormatFullDate.format(dateValue);
			} else if (dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_ONLY_DATE)) {
				//usEstDateFormatOnlyDate.setTimeZone(tzInAmerica);
				dateValueString = usEstDateFormatOnlyDate.format(dateValue);
			}
			
			return dateValueString;
		} else return null;
		
	}

	
	public Date convertToDateFromString(String dataString, String dateType) {
		try {
			Date returnDateValue = null;
			
			if(dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_FULL)) {
				//usEstDateFormatFullDate.setTimeZone(tzInAmerica);
				returnDateValue = usEstDateFormatFullDate.parse(dataString);				
			} else if (dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_ONLY_DATE)) {				
				//usEstDateFormatOnlyDate.setTimeZone(tzInAmerica);
				returnDateValue = usEstDateFormatOnlyDate.parse(dataString);			
			}
			return returnDateValue;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String convertToStringFromLocalDate(LocalDate dateValue, String dateType) {				
		
		String dateValueString = "";
		if(dateValue!=null) {
			if(dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_FULL)) {
				 DateTimeFormatter dateFormatter1 = DateTimeFormatter
				            .ofPattern("MM/dd/yyyy hh:mm:ss a");
				dateValueString = dateValue.format(dateFormatter1);
			} else if (dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_ONLY_DATE)) {
				 DateTimeFormatter dateFormatter1 = DateTimeFormatter
				            .ofPattern("MM/dd/yyyy");
				dateValueString = dateValue.format(dateFormatter1);
			}
			
			return dateValueString;
		} else return null;
		
	}	
	
	public LocalDate convertToLocalDateFromString(String dataString, String dateType) {
		try {
			LocalDate  returnDateValue = null;
			
			if(dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_FULL)) {
				//usEstDateFormatFullDate.setTimeZone(tzInAmerica);
				returnDateValue = LocalDate.parse(dataString,DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a"));				
			} else if (dateType.equalsIgnoreCase(UIGenericConstants.DATE_TYPE_ONLY_DATE)) {				
				//usEstDateFormatOnlyDate.setTimeZone(tzInAmerica);
				returnDateValue = LocalDate.parse(dataString,DateTimeFormatter.ofPattern("MM/dd/yyyy"));			
			}
			return returnDateValue;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
}
