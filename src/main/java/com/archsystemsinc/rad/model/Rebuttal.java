package com.archsystemsinc.rad.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.archsystemsinc.rad.common.utils.UtilityFunctions;

public class Rebuttal implements Comparable<Rebuttal> {	
	
	
	private int id;

	private String agree;
	
	private String callDate;

	
	private String callType;

	
	private String contactPerson;

	
	private String createdBy;

	
	private String createdDate;

	
	private int csrId;

	
	private String datePostedString;
	
	private Date datePosted;

	private String description;

	
	private int failureReasonId;

	
	private String macReferenceId;

	
	private int rebuttalQmLogId;

	
	private String updatedBy;

	
	private String updatedDate;

	
	private int userId;	
	
	private String csrFullName;
	
	private String failureReason;
	
	private String qamFullName;
	
	private String callTime;
	
	private File uploadAttachment;
	
	private String callCategory;
	
	private String descriptionComments;
	
	private String macCallReferenceNumber;
	
	private Integer macId;
	
	private Integer jurisId;
	
	private Integer programId;
	
	private String rebuttalStatus;
	
	private String rebuttalResult;
	
	private String accuracyCallFailureReason;	
	
	private String completenessCallFailureReason;	
	
	private String privacyCallFailureReason;
	
	private String customerSkillsCallFailureReason;
	
	private Integer pccLocationId;
	
	private String rebuttalCallCategory;
	
	private Integer qamId;
	
	//Temporaray Variables
	
	private String rebuttalResultTemp;
	
	private String descriptionCommentsAppend;
	
	private String rebuttalCompleteFlag;
	
	private String macPCCNameTempValue;
	
	private String filterFromDateString;
	
	private String filterToDateString;
	
	private Date filterFromDate;
	
	private Date filterToDate;
	
	private Integer filterMacId;
	
	private String macName;
	
	private String lob;
	
	private String jurisName;
	
	private String[] jurisIdReportSearchString;
	
	private ArrayList<Integer> jurisIdList;
	
	private byte[] rebuttalFileAttachment;
	

	private String fileName;
	

	private String fileDescription;
	

	private String fileType;
	
	private MultipartFile rebuttalFileObject;
	
	private ByteArrayResource httpFileData;
	
	private String callCategoryForDisplay;
	
	private String lobForDisplay;
	
	
	
	
	public String getRebuttalResultTemp() {
		return rebuttalResultTemp;
	}


	public void setRebuttalResultTemp(String rebuttalResultTemp) {
		this.rebuttalResultTemp = rebuttalResultTemp;
	}


	public Integer getQamId() {
		return qamId;
	}


	public void setQamId(Integer qamId) {
		this.qamId = qamId;
	}


	public String getCallCategoryForDisplay() {
		return callCategoryForDisplay;
	}


	public void setCallCategoryForDisplay(String callCategoryForDisplay) {
		this.callCategoryForDisplay = callCategoryForDisplay;
	}


	public String getLobForDisplay() {
		return lobForDisplay;
	}


	public void setLobForDisplay(String lobForDisplay) {
		this.lobForDisplay = lobForDisplay;
	}


	public ByteArrayResource getHttpFileData() {
		return httpFileData;
	}


	public void setHttpFileData(ByteArrayResource httpFileData) {
		this.httpFileData = httpFileData;
	}


	public String[] getJurisIdReportSearchString() {
		return jurisIdReportSearchString;
	}


	public void setJurisIdReportSearchString(String[] jurisIdReportSearchString) {
		this.jurisIdReportSearchString = jurisIdReportSearchString;
	}


	public MultipartFile getRebuttalFileObject() {
		return rebuttalFileObject;
	}


	public void setRebuttalFileObject(MultipartFile rebuttalFileObject) {
		this.rebuttalFileObject = rebuttalFileObject;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileDescription() {
		return fileDescription;
	}


	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public byte[] getRebuttalFileAttachment() {
		return rebuttalFileAttachment;
	}


	public void setRebuttalFileAttachment(byte[] rebuttalFileAttachment) {
		this.rebuttalFileAttachment = rebuttalFileAttachment;
	}


	public ArrayList<Integer> getJurisIdList() {
		return jurisIdList;
	}


	public void setJurisIdList(ArrayList<Integer> jurisIdList) {
		this.jurisIdList = jurisIdList;
	}


	public String getJurisName() {
		return jurisName;
	}


	public void setJurisName(String jurisName) {
		this.jurisName = jurisName;
	}


	public String getLob() {
		return lob;
	}


	public void setLob(String lob) {
		this.lob = lob;
	}


	public String getMacName() {
		return macName;
	}


	public void setMacName(String macName) {
		this.macName = macName;
	}


	public Integer getFilterMacId() {
		return filterMacId;
	}


	public void setFilterMacId(Integer filterMacId) {
		this.filterMacId = filterMacId;
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


	public String getRebuttalCallCategory() {
		return rebuttalCallCategory;
	}


	public void setRebuttalCallCategory(String rebuttalCallCategory) {
		this.rebuttalCallCategory = rebuttalCallCategory;
	}


	public String getMacPCCNameTempValue() {
		return macPCCNameTempValue;
	}


	public void setMacPCCNameTempValue(String macPCCNameTempValue) {
		this.macPCCNameTempValue = macPCCNameTempValue;
	}


	public String getRebuttalResult() {
		return rebuttalResult;
	}


	public void setRebuttalResult(String rebuttalResult) {
		this.rebuttalResult = rebuttalResult;
	}
	
	


	public Integer getPccLocationId() {
		return pccLocationId;
	}


	public void setPccLocationId(Integer pccLocationId) {
		this.pccLocationId = pccLocationId;
	}


	public String getRebuttalCompleteFlag() {
		return rebuttalCompleteFlag;
	}


	public void setRebuttalCompleteFlag(String rebuttalCompleteFlag) {
		this.rebuttalCompleteFlag = rebuttalCompleteFlag;
	}


	public String getDescriptionCommentsAppend() {
		return descriptionCommentsAppend;
	}


	public void setDescriptionCommentsAppend(String descriptionCommentsAppend) {
		this.descriptionCommentsAppend = descriptionCommentsAppend;
	}

	public String getAccuracyCallFailureReason() {
		return accuracyCallFailureReason;
	}


	public void setAccuracyCallFailureReason(String accuracyCallFailureReason) {
		this.accuracyCallFailureReason = accuracyCallFailureReason;
	}


	public String getCompletenessCallFailureReason() {
		return completenessCallFailureReason;
	}


	public void setCompletenessCallFailureReason(String completenessCallFailureReason) {
		this.completenessCallFailureReason = completenessCallFailureReason;
	}


	public String getPrivacyCallFailureReason() {
		return privacyCallFailureReason;
	}


	public void setPrivacyCallFailureReason(String privacyCallFailureReason) {
		this.privacyCallFailureReason = privacyCallFailureReason;
	}


	public String getCustomerSkillsCallFailureReason() {
		return customerSkillsCallFailureReason;
	}


	public void setCustomerSkillsCallFailureReason(String customerSkillsCallFailureReason) {
		this.customerSkillsCallFailureReason = customerSkillsCallFailureReason;
	}


	public Integer getProgramId() {
		return programId;
	}


	public void setProgramId(Integer programId) {
		this.programId = programId;
	}


	public String getRebuttalStatus() {
		return rebuttalStatus;
	}


	public void setRebuttalStatus(String rebuttalStatus) {
		this.rebuttalStatus = rebuttalStatus;
	}


	public Integer getMacId() {
		return macId;
	}


	public void setMacId(Integer macId) {
		this.macId = macId;
	}


	public Integer getJurisId() {
		return jurisId;
	}


	public void setJurisId(Integer jurisId) {
		this.jurisId = jurisId;
	}


	public Rebuttal() {
	}	


	

	public String getMacCallReferenceNumber() {
		return macCallReferenceNumber;
	}


	public void setMacCallReferenceNumber(String macCallReferenceNumber) {
		this.macCallReferenceNumber = macCallReferenceNumber;
	}


	public String getCallCategory() {
		return callCategory;
	}






	public void setCallCategory(String callCategory) {
		this.callCategory = callCategory;
	}






	public String getDescriptionComments() {
		return descriptionComments;
	}



	public void setDescriptionComments(String descriptionComments) {
		this.descriptionComments = descriptionComments;
	}



	

	public String getCsrFullName() {
		return csrFullName;
	}

	public void setCsrFullName(String csrFullName) {
		this.csrFullName = csrFullName;
	}




	public String getFailureReason() {
		return failureReason;
	}




	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}




	public String getQamFullName() {
		return qamFullName;
	}




	public void setQamFullName(String qamFullName) {
		this.qamFullName = qamFullName;
	}




	public String getCallTime() {
		return callTime;
	}




	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public File getUploadAttachment() {
		return uploadAttachment;
	}

	public void setUploadAttachment(File uploadAttachment) {
		this.uploadAttachment = uploadAttachment;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCallDate() {
		return callDate;
	}


	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}


	public String getCallType() {
		return callType;
	}


	public void setCallType(String callType) {
		this.callType = callType;
	}


	public String getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public int getCsrId() {
		return csrId;
	}


	public void setCsrId(int csrId) {
		this.csrId = csrId;
	}
	

	public String getDatePostedString() {
		return datePostedString;
	}


	public void setDatePostedString(String datePostedString) {
		this.datePostedString = datePostedString;
	}


	public Date getDatePosted() {
		return datePosted;
	}


	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getFailureReasonId() {
		return failureReasonId;
	}


	public void setFailureReasonId(int failureReasonId) {
		this.failureReasonId = failureReasonId;
	}


	public String getMacReferenceId() {
		return macReferenceId;
	}


	public void setMacReferenceId(String macReferenceId) {
		this.macReferenceId = macReferenceId;
	}


	public int getRebuttalQmLogId() {
		return rebuttalQmLogId;
	}


	public void setRebuttalQmLogId(int rebuttalQmLogId) {
		this.rebuttalQmLogId = rebuttalQmLogId;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getAgree() {
		return agree;
	}


	public void setAgree(String agree) {
		this.agree = agree;
	}	
	
	public int compareTo(Rebuttal rebuttal) {

		String date1 = ((Rebuttal) rebuttal).getUpdatedDate();
		String date2 = this.updatedDate;
		
		if(date1 != null && !date1.equalsIgnoreCase("") && date2 != null && !date2.equalsIgnoreCase("") ) {
			return date1.compareTo(date2);
		} else if((date1 == null || date1.equalsIgnoreCase("")) && (date2 == null || date2.equalsIgnoreCase(""))) {
			return 0;
		} else if(date1 == null || date1.equalsIgnoreCase("")) {
			return -1;
		}  else if(date2 == null || date2.equalsIgnoreCase("")) {
			return 1;
		} else return 0;
		//descending order
		//return compareQuantity - this.quantity;

	}


}