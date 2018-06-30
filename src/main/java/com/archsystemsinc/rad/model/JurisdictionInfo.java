package com.archsystemsinc.rad.model;

/**
 * This persistent class for the user database table.
 */

public class JurisdictionInfo {
	private Long id;
	private String jurisdictionName;	
	private String createdBy;
	private String updatedBy;
	private String createdDate;	
	private String updateddDate;
	
	private String description;	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJurisdictionName() {
		return jurisdictionName;
	}
	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
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
}
