package com.archsystemsinc.rad.model;

import java.util.Date;
import java.util.HashMap;


public class ScoreCard  implements Comparable<ScoreCard>{
	
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
	
	private String accuracyCallFailureReason;
	
	private String accuracyCallFailureTime;
	
	private String completenessCallFailureReason;
	
	private String completenessCallFailureTime;
	
	private String privacyCallFailureReason;
	
	private String privacyCallFailureTime;
	
	private String customerSkillsCallFailureReason;
	
	private String customerSkillsCallFailureTime;
	
	//Temporary Variables
	private String jurisdictionName;	
	
	private String callCategoryName;
	
	private HashMap<Integer,String> pccLocationMap;
	
	private String filterFromDateString;	
	
	private String filterToDateString;
	
	private Date filterFromDate;
	
	private Date filterToDate;
	
	public String getFilterFromDateString() {
		return filterFromDateString;
	}

	public void setFilterFromDateString(String filterFromDateString) {
		this.filterFromDateString = filterFromDateString;
	}

	public String getFilterToDateString() {
		return filterToDateString;
	}

	public void setFilterToDateString(String filterToDateString) {
		this.filterToDateString = filterToDateString;
	}

	public Date getFilterFromDate() {
		return filterFromDate;
	}

	public void setFilterFromDate(Date filterFromDate) {
		this.filterFromDate = filterFromDate;
	}

	public Date getFilterToDate() {
		return filterToDate;
	}

	public void setFilterToDate(Date filterToDate) {
		this.filterToDate = filterToDate;
	}

	public HashMap<Integer, String> getPccLocationMap() {
		return pccLocationMap;
	}

	public void setPccLocationMap(HashMap<Integer, String> pccLocationMap) {
		this.pccLocationMap = pccLocationMap;
	}

	public String getCallCategoryName() {
		return callCategoryName;
	}

	public void setCallCategoryName(String callCategoryName) {
		this.callCategoryName = callCategoryName;
	}

	public String getAccuracyCallFailureReason() {
		return accuracyCallFailureReason;
	}

	public void setAccuracyCallFailureReason(String accuracyCallFailureReason) {
		this.accuracyCallFailureReason = accuracyCallFailureReason;
	}

	public String getAccuracyCallFailureTime() {
		return accuracyCallFailureTime;
	}

	public void setAccuracyCallFailureTime(String accuracyCallFailureTime) {
		this.accuracyCallFailureTime = accuracyCallFailureTime;
	}

	public String getCompletenessCallFailureReason() {
		return completenessCallFailureReason;
	}

	public void setCompletenessCallFailureReason(String completenessCallFailureReason) {
		this.completenessCallFailureReason = completenessCallFailureReason;
	}

	public String getCompletenessCallFailureTime() {
		return completenessCallFailureTime;
	}

	public void setCompletenessCallFailureTime(String completenessCallFailureTime) {
		this.completenessCallFailureTime = completenessCallFailureTime;
	}

	public String getPrivacyCallFailureReason() {
		return privacyCallFailureReason;
	}

	public void setPrivacyCallFailureReason(String privacyCallFailureReason) {
		this.privacyCallFailureReason = privacyCallFailureReason;
	}

	public String getPrivacyCallFailureTime() {
		return privacyCallFailureTime;
	}

	public void setPrivacyCallFailureTime(String privacyCallFailureTime) {
		this.privacyCallFailureTime = privacyCallFailureTime;
	}

	public String getCustomerSkillsCallFailureReason() {
		return customerSkillsCallFailureReason;
	}

	public void setCustomerSkillsCallFailureReason(String customerSkillsCallFailureReason) {
		this.customerSkillsCallFailureReason = customerSkillsCallFailureReason;
	}

	public String getCustomerSkillsCallFailureTime() {
		return customerSkillsCallFailureTime;
	}

	public void setCustomerSkillsCallFailureTime(String customerSkillsCallFailureTime) {
		this.customerSkillsCallFailureTime = customerSkillsCallFailureTime;
	}

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



	public int compareTo(ScoreCard scoreCard) {

		int compareMacId = ((ScoreCard) scoreCard).getMacId();

		//ascending order
		return this.macId - compareMacId;

		//descending order
		//return compareQuantity - this.quantity;

	}

}