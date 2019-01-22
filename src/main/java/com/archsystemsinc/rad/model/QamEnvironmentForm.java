package com.archsystemsinc.rad.model;

import java.util.Date;

/**
 * This persistent class for the user database table.
 */

public class QamEnvironmentForm {
    
	//JSP Form Elements
    private String keepCurrentListCB;
    
    private String macIdK;
    private String macIdS;
    private String macIdU;
    

    
    private String jurisdictionK;
    private String jurisdictionS;
    private String jurisdictionU;
    
    private String jurisdictionUText;
    
    private String fromDate;
    private String toDate; 
    
;
    //DB Model Elements
    private Long qamEnvironmentChangeFormId;
    
    private Integer userId;
    
	private Long macLookupId;
	
	private Long jurisdictionId;
	
	private String qamEnvFormstatus;
	

	private String fileType;
	

	private String documentName;
	

	private String description;
	

 
    private byte[] documentContent;
	

	private String createdBy;
	

	private String updatedBy;
	


	private String createdDate;
	
	


	private String updateddDate;
	

	private Long recordStatus;
	

	private String formType;
	
	
	
	
    
	public Long getMacLookupId() {
		return macLookupId;
	}
	public void setMacLookupId(Long macLookupId) {
		this.macLookupId = macLookupId;
	}
	public Long getJurisdictionId() {
		return jurisdictionId;
	}
	public void setJurisdictionId(Long jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}
	public String getQamEnvFormstatus() {
		return qamEnvFormstatus;
	}
	public void setQamEnvFormstatus(String qamEnvFormstatus) {
		this.qamEnvFormstatus = qamEnvFormstatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdateddDate() {
		return updateddDate;
	}
	public void setUpdateddDate(String updateddDate) {
		this.updateddDate = updateddDate;
	}
	public Long getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(Long recordStatus) {
		this.recordStatus = recordStatus;
	}
	public Long getQamEnvironmentChangeFormId() {
		return qamEnvironmentChangeFormId;
	}
	public void setQamEnvironmentChangeFormId(Long qamEnvironmentChangeFormId) {
		this.qamEnvironmentChangeFormId = qamEnvironmentChangeFormId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public byte[] getDocumentContent() {
		return documentContent;
	}
	public void setDocumentContent(byte[] documentContent) {
		this.documentContent = documentContent;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getJurisdictionUText() {
		return jurisdictionUText;
	}
	public void setJurisdictionUText(String jurisdictionUText) {
		this.jurisdictionUText = jurisdictionUText;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getKeepCurrentListCB() {
		return keepCurrentListCB;
	}
	public void setKeepCurrentListCB(String keepCurrentListCB) {
		this.keepCurrentListCB = keepCurrentListCB;
	}
	public String getMacIdK() {
		return macIdK;
	}
	public void setMacIdK(String macIdK) {
		this.macIdK = macIdK;
	}
	public String getMacIdS() {
		return macIdS;
	}
	public void setMacIdS(String macIdS) {
		this.macIdS = macIdS;
	}
	public String getMacIdU() {
		return macIdU;
	}
	public void setMacIdU(String macIdU) {
		this.macIdU = macIdU;
	}
	public String getJurisdictionK() {
		return jurisdictionK;
	}
	public void setJurisdictionK(String jurisdictionK) {
		this.jurisdictionK = jurisdictionK;
	}
	public String getJurisdictionS() {
		return jurisdictionS;
	}
	public void setJurisdictionS(String jurisdictionS) {
		this.jurisdictionS = jurisdictionS;
	}
	public String getJurisdictionU() {
		return jurisdictionU;
	}
	public void setJurisdictionU(String jurisdictionU) {
		this.jurisdictionU = jurisdictionU;
	} 
}