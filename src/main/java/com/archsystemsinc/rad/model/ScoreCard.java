package com.archsystemsinc.rad.model;

import java.util.Date;


public class ScoreCard  {
	
	private int id;

	
	private Integer callCategoryId;	
	
	
	private Integer callSubCategoryId;
	
	
	private String csrFullName;
	
	
	private String callDuration;

	
	
	private Date callFailureTime;

	
	private String callLanguage;

	
	private String callMonitoringDate;

	
	private String callResult;

	
	private String callTime;

	
	private String csrFallPrivacyProv;

	
	private String csrLevel;

	
	private String csrPrvAccInfo;

	
	private String csrPrvCompInfo;

	
	private String csrWasCourteous;
	
	
	private String failReasonComments;
	
	
	private String failReasonAdditionalComments;
	
	
	private String failReason;
	
	
	private Integer jurId;
	
	
	private String macCallReferenceNumber;

	
	private Integer macId;

	
	private Integer programId;

	
	private String qamEnddateTime;

	
	private String qamFullName;

	
	private String qamStartdateTime;

	
	private String scorecardComments;

	
	private String scorecardStatus;
	
	private Integer userId;
	
	private String scorecardType;
	
	private String lob;
	
	private String nonScoreableReason;
	
	//Temporary Jursidction Name Variable
	private String jurisdictionName;
	
	
	public String getNonScoreableReason() {
		return nonScoreableReason;
	}

	public void setNonScoreableReason(String nonScoreableReason) {
		this.nonScoreableReason = nonScoreableReason;
	}

	public String getJurisdictionName() {
		return jurisdictionName;
	}

	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public Integer getCallSubCategoryId() {
		return callSubCategoryId;
	}

	public void setCallSubCategoryId(Integer callSubCategoryId) {
		this.callSubCategoryId = callSubCategoryId;
	}

	public String getScorecardType() {
		return scorecardType;
	}

	public void setScorecardType(String scorecardType) {
		this.scorecardType = scorecardType;
	}
	

	public String getFailReasonAdditionalComments() {
		return failReasonAdditionalComments;
	}



	public void setFailReasonAdditionalComments(String failReasonAdditionalComments) {
		this.failReasonAdditionalComments = failReasonAdditionalComments;
	}



	public ScoreCard() {
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Integer getCallCategoryId() {
		return callCategoryId;
	}



	public void setCallCategoryId(Integer callCategoryId) {
		this.callCategoryId = callCategoryId;
	}



	public String getCsrFullName() {
		return csrFullName;
	}



	public void setCsrFullName(String csrFullName) {
		this.csrFullName = csrFullName;
	}



	public String getCallDuration() {
		return callDuration;
	}



	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}



	public Date getCallFailureTime() {
		return callFailureTime;
	}



	public void setCallFailureTime(Date callFailureTime) {
		this.callFailureTime = callFailureTime;
	}



	public String getCallLanguage() {
		return callLanguage;
	}



	public void setCallLanguage(String callLanguage) {
		this.callLanguage = callLanguage;
	}



	public String getCallMonitoringDate() {
		return callMonitoringDate;
	}



	public void setCallMonitoringDate(String callMonitoringDate) {
		this.callMonitoringDate = callMonitoringDate;
	}



	public String getCallResult() {
		return callResult;
	}



	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}



	public String getCallTime() {
		return callTime;
	}



	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}



	public String getCsrFallPrivacyProv() {
		return csrFallPrivacyProv;
	}



	public void setCsrFallPrivacyProv(String csrFallPrivacyProv) {
		this.csrFallPrivacyProv = csrFallPrivacyProv;
	}



	public String getCsrLevel() {
		return csrLevel;
	}



	public void setCsrLevel(String csrLevel) {
		this.csrLevel = csrLevel;
	}



	public String getCsrPrvAccInfo() {
		return csrPrvAccInfo;
	}



	public void setCsrPrvAccInfo(String csrPrvAccInfo) {
		this.csrPrvAccInfo = csrPrvAccInfo;
	}



	public String getCsrPrvCompInfo() {
		return csrPrvCompInfo;
	}



	public void setCsrPrvCompInfo(String csrPrvCompInfo) {
		this.csrPrvCompInfo = csrPrvCompInfo;
	}



	public String getCsrWasCourteous() {
		return csrWasCourteous;
	}



	public void setCsrWasCourteous(String csrWasCourteous) {
		this.csrWasCourteous = csrWasCourteous;
	}



	public String getFailReasonComments() {
		return failReasonComments;
	}



	public void setFailReasonComments(String failReasonComments) {
		this.failReasonComments = failReasonComments;
	}



	public Integer getJurId() {
		return jurId;
	}



	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public void setJurId(Integer jurId) {
		this.jurId = jurId;
	}



	public String getMacCallReferenceNumber() {
		return macCallReferenceNumber;
	}



	public void setMacCallReferenceNumber(String macCallReferenceNumber) {
		this.macCallReferenceNumber = macCallReferenceNumber;
	}



	public Integer getMacId() {
		return macId;
	}



	public void setMacId(Integer macId) {
		this.macId = macId;
	}



	public Integer getProgramId() {
		return programId;
	}



	public void setProgramId(Integer programId) {
		this.programId = programId;
	}



	public String getQamEnddateTime() {
		return qamEnddateTime;
	}



	public void setQamEnddateTime(String qamEnddateTime) {
		this.qamEnddateTime = qamEnddateTime;
	}



	public String getQamFullName() {
		return qamFullName;
	}



	public void setQamFullName(String qamFullName) {
		this.qamFullName = qamFullName;
	}



	public String getQamStartdateTime() {
		return qamStartdateTime;
	}



	public void setQamStartdateTime(String qamStartdateTime) {
		this.qamStartdateTime = qamStartdateTime;
	}



	public String getScorecardComments() {
		return scorecardComments;
	}



	public void setScorecardComments(String scorecardComments) {
		this.scorecardComments = scorecardComments;
	}



	public String getScorecardStatus() {
		return scorecardStatus;
	}



	public void setScorecardStatus(String scorecardStatus) {
		this.scorecardStatus = scorecardStatus;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}	

}