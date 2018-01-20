package com.archsystemsinc.rad.model;

import java.io.File;

public class Rebuttal  {	
	
	private int id;

	
	private String callDate;

	
	private String callType;

	
	private String contactPerson;

	
	private String createdBy;

	
	private String createdDate;

	
	private int csrId;

	
	private String datePosted;

	private String description;

	
	private int failureReasonId;

	
	private String macReferenceId;

	
	private int rebuttalQmLogId;

	
	private String updatedBy;

	
	private String updatedDate;

	
	private int userId;	
	
	private String macPCCName;
	
	private String csrFullName;
	
	private String failureReason;
	
	private String qamFullName;
	
	private String callTime;
	
	private File uploadAttachment;
	
	private String callCategory;
	
	private String descriptionComments;
	
	private String macCallReferenceNumber;
	
	
	public Rebuttal() {
	}	


	public String getMacPCCName() {
		return macPCCName;
	}

	public String getMacCallReferenceNumber() {
		return macCallReferenceNumber;
	}


	public void setMacCallReferenceNumber(String macCallReferenceNumber) {
		this.macCallReferenceNumber = macCallReferenceNumber;
	}


	public void setMacPCCName(String macPCCName) {
		this.macPCCName = macPCCName;
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


	public String getDatePosted() {
		return datePosted;
	}


	public void setDatePosted(String datePosted) {
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

}