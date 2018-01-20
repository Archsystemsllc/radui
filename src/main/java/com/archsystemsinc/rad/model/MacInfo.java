package com.archsystemsinc.rad.model;

/**
 * This persistent class for the user database table.
 */

public class MacInfo {
	private Long id;
	private String macName;
	private String macDescription;
	private String createdBy;
	private String updatedBy;	
	
	private String qamStartDate;	
	private String qamEndDate;	
	private String createdDate;	
	private String updateddDate;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMacName() {
		return macName;
	}


	public void setMacName(String macName) {
		this.macName = macName;
	}


	public String getMacDescription() {
		return macDescription;
	}


	public void setMacDescription(String macDescription) {
		this.macDescription = macDescription;
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
}
