package com.archsystemsinc.rad.model;

import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class QamMacByJurisdictionReviewReport implements Comparable<QamMacByJurisdictionReviewReport>{  
    
    private String macName;   
    
    private String jurisdictionName;
    
    private String program;
    
    private String location;
    
    private Integer totalCount = 0;
    
    private Integer scorableCount = 0;
    
    private Integer scorablePass = 0;
    
    private Integer scorableFail = 0;
    
    private Float scorablePassPercent = 0.0f;
    
    private Float scorableFailPercent= 0.0f;
    
    private Integer hhhTotalCount = 0;
    
    private Integer hhhScorableCount = 0;
    
    private Integer hhhScorablePass = 0;
    
    private Integer hhhScorableFail = 0;
    
    private Float hhhScorablePassPercent = 0.0f;
    
    private Float hhhScorableFailPercent= 0.0f;
    
    private Integer nonScorableCount= 0;
    
    private Float nonScorablePercent= 0.0f;
    
    private Integer doesNotCount_Number= 0;
    
    private Float doesNotCount_Percent= 0.0f;
    
    private String qamStartDate;
    
    private String qamEndDate;   
    
    private String complianceStatus;
    
    private String monthYear;
    
    private Integer macId;
    
    private String scoreCardType;
    
    private Integer plannedCalls;
    
    private String createdMethod;
    
    private Integer assignedCalls;
    
    private String qmName;
    
    private String callCategoryName;
    
    private String callDuration;
    
    private String macCallReferenceNumber;
    
    private String finalCallResult;
    
    
    public String getFinalCallResult() {
		return finalCallResult;
	}

	public void setFinalCallResult(String finalCallResult) {
		this.finalCallResult = finalCallResult;
	}

	public String getMacCallReferenceNumber() {
		return macCallReferenceNumber;
	}

	public void setMacCallReferenceNumber(String macCallReferenceNumber) {
		this.macCallReferenceNumber = macCallReferenceNumber;
	}

	public String getCallCategoryName() {
		return callCategoryName;
	}

	public void setCallCategoryName(String callCategoryName) {
		this.callCategoryName = callCategoryName;
	}
	

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public Integer getHhhTotalCount() {
		return hhhTotalCount;
	}

	public void setHhhTotalCount(Integer hhhTotalCount) {
		this.hhhTotalCount = hhhTotalCount;
	}

	public Integer getHhhScorableCount() {
		return hhhScorableCount;
	}

	public void setHhhScorableCount(Integer hhhScorableCount) {
		this.hhhScorableCount = hhhScorableCount;
	}

	public Integer getHhhScorablePass() {
		return hhhScorablePass;
	}

	public void setHhhScorablePass(Integer hhhScorablePass) {
		this.hhhScorablePass = hhhScorablePass;
	}

	public Integer getHhhScorableFail() {
		return hhhScorableFail;
	}

	public void setHhhScorableFail(Integer hhhScorableFail) {
		this.hhhScorableFail = hhhScorableFail;
	}

	public Float getHhhScorablePassPercent() {
		return hhhScorablePassPercent;
	}

	public void setHhhScorablePassPercent(Float hhhScorablePassPercent) {
		this.hhhScorablePassPercent = hhhScorablePassPercent;
	}

	public Float getHhhScorableFailPercent() {
		return hhhScorableFailPercent;
	}

	public void setHhhScorableFailPercent(Float hhhScorableFailPercent) {
		this.hhhScorableFailPercent = hhhScorableFailPercent;
	}

	public Integer getPlannedCalls() {
		return plannedCalls;
	}

	public void setPlannedCalls(Integer plannedCalls) {
		this.plannedCalls = plannedCalls;
	}

	public String getCreatedMethod() {
		return createdMethod;
	}

	public void setCreatedMethod(String createdMethod) {
		this.createdMethod = createdMethod;
	}

	public Integer getAssignedCalls() {
		return assignedCalls;
	}

	public void setAssignedCalls(Integer assignedCalls) {
		this.assignedCalls = assignedCalls;
	}

	public String getQmName() {
		return qmName;
	}

	public void setQmName(String qmName) {
		this.qmName = qmName;
	}

	public String getScoreCardType() {
		return scoreCardType;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}



	public void setScoreCardType(String scoreCardType) {
		this.scoreCardType = scoreCardType;
	}

	public Integer getDoesNotCount_Number() {
		return doesNotCount_Number;
	}

	public void setDoesNotCount_Number(Integer doesNotCount_Number) {
		this.doesNotCount_Number = doesNotCount_Number;
	}

	public Float getDoesNotCount_Percent() {
		return doesNotCount_Percent;
	}

	public void setDoesNotCount_Percent(Float doesNotCount_Percent) {
		this.doesNotCount_Percent = doesNotCount_Percent;
	}

	public Integer getMacId() {
		return macId;
	}

	public void setMacId(Integer macId) {
		this.macId = macId;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}

	public String getComplianceStatus() {
		return complianceStatus;
	}

	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	public String getQamStartDate() {
		return qamStartDate;
	}

	public void setQamStartDate(String qamStartDate) {
		this.qamStartDate = qamStartDate;
	}

	public String getQamEndDate() {
		return qamEndDate;
	}

	public void setQamEndDate(String qamEndDate) {
		this.qamEndDate = qamEndDate;
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

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getScorableCount() {
		return scorableCount;
	}

	public void setScorableCount(Integer scorableCount) {
		this.scorableCount = scorableCount;
	}

	public Integer getScorablePass() {
		return scorablePass;
	}

	public void setScorablePass(Integer scorablePass) {
		this.scorablePass = scorablePass;
	}

	public Integer getScorableFail() {
		return scorableFail;
	}

	public void setScorableFail(Integer scorableFail) {
		this.scorableFail = scorableFail;
	}

	public Float getScorablePassPercent() {
		return scorablePassPercent;
	}

	public void setScorablePassPercent(Float scorablePassPercent) {
		this.scorablePassPercent = scorablePassPercent;
	}

	public Float getScorableFailPercent() {
		return scorableFailPercent;
	}

	public void setScorableFailPercent(Float scorableFailPercent) {
		this.scorableFailPercent = scorableFailPercent;
	}

	public Integer getNonScorableCount() {
		return nonScorableCount;
	}

	public void setNonScorableCount(Integer nonScorableCount) {
		this.nonScorableCount = nonScorableCount;
	}

	public Float getNonScorablePercent() {
		return nonScorablePercent;
	}

	public void setNonScorablePercent(Float nonScorablePercent) {
		this.nonScorablePercent = nonScorablePercent;
	}

	@Override
	public int compareTo(QamMacByJurisdictionReviewReport object) {

	int compareMacId = ((QamMacByJurisdictionReviewReport) object).getMacId();

	//ascending order
	return this.macId - compareMacId;

	//descending order
	//return compareQuantity - this.quantity;

	}
}