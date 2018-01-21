package com.archsystemsinc.rad.model;

import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class QamMacByJurisdictionReviewReport {  
    
    private String macName;   
    
    private String jurisdictionName;
    
    private String program;
    
    private String location;
    
    private Integer scorableCount = 0;
    
    private Integer scorablePass = 0;
    
    private Integer scorableFail = 0;
    
    private Float scorablePassPercent = 0.0f;
    
    private Float scorableFailPercent= 0.0f;
    
    private Integer nonScorableCount= 0;
    
    private Float nonScorablePercent= 0.0f;
    
    private String qamStartDate;
    
    private String qamEndDate;   
    

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
}