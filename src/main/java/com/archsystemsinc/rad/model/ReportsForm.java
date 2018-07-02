package com.archsystemsinc.rad.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class ReportsForm {  
    
    private String macId;   
    
    private String userId;
    
    private String jurisId;
    
    private String programId;
    
    private String pccLocationId;
    
    private Date fromDate;
    
    private Date toDate;
    
    private String scoreCardType;
    
    private String compliance;
    
    private String mainReportSelect;
    
    private String callResult;
    
    private String macName;
    
    private String jurisdictionName;
    
    private String programName;
    
    private String pccLocationName;
    
    private String fromDateString;
    
    private String toDateString;
    
    private String complianceReportType;
    
    private String callCategoryType;
    
    private String rebuttalStatus;   
    
    private ArrayList<Integer> jurIdList;
    
    private ArrayList<String> jurisdictionNameList;
    
    private String jurisdictionNameValues;
    
    private String[] jurisdictionIds;
    
    
	
	public String getPccLocationName() {
		return pccLocationName;
	}

	public void setPccLocationName(String pccLocationName) {
		this.pccLocationName = pccLocationName;
	}

	public String[] getJurisdictionIds() {
		return jurisdictionIds;
	}

	public void setJurisdictionIds(String[] jurisdictionIds) {
		this.jurisdictionIds = jurisdictionIds;
	}

	public ArrayList<String> getJurisdictionNameList() {
		return jurisdictionNameList;
	}

	public void setJurisdictionNameList(ArrayList<String> jurisdictionNameList) {
		this.jurisdictionNameList = jurisdictionNameList;
	}

	public String getJurisdictionNameValues() {
		return jurisdictionNameValues;
	}

	public void setJurisdictionNameValues(String jurisdictionNameValues) {
		this.jurisdictionNameValues = jurisdictionNameValues;
	}

	public ArrayList<Integer> getJurIdList() {
		return jurIdList;
	}

	public void setJurIdList(ArrayList<Integer> jurIdList) {
		this.jurIdList = jurIdList;
	}

	public String getComplianceReportType() {
		return complianceReportType;
	}

	public void setComplianceReportType(String complianceReportType) {
		this.complianceReportType = complianceReportType;
	}

	public String getCallCategoryType() {
		return callCategoryType;
	}

	public void setCallCategoryType(String callCategoryType) {
		this.callCategoryType = callCategoryType;
	}

	
	public String getRebuttalStatus() {
		return rebuttalStatus;
	}

	public void setRebuttalStatus(String rebuttalStatus) {
		this.rebuttalStatus = rebuttalStatus;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getFromDateString() {
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	public String getToDateString() {
		return toDateString;
	}

	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	public String getMacName() {
		return macName;
	}

	public void setMacName(String macName) {
		this.macName = macName;
	}

	public String getJurisdictionName() {
		return jurisdictionName;
	}

	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}

	public String getCallResult() {
		return callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}

	public String getMainReportSelect() {
		return mainReportSelect;
	}

	public void setMainReportSelect(String mainReportSelect) {
		this.mainReportSelect = mainReportSelect;
	}

	public String getCompliance() {
		return compliance;
	}

	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}

	public String getMacId() {
		return macId;
	}

	public void setMacId(String macId) {
		this.macId = macId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJurisId() {
		return jurisId;
	}

	public void setJurisId(String jurisId) {
		this.jurisId = jurisId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getPccLocationId() {
		return pccLocationId;
	}

	public void setPccLocationId(String pccLocationId) {
		this.pccLocationId = pccLocationId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getScoreCardType() {
		return scoreCardType;
	}

	public void setScoreCardType(String scoreCardType) {
		this.scoreCardType = scoreCardType;
	}
}