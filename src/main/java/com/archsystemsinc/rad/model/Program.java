package com.archsystemsinc.rad.model;

/**
 * This persistent class for the user database table.
 */

public class Program {
	
	private Integer id;

	
	private String createdBy;

	
	private String createdDate;

	
	private String programDescription;

	
	private String programName;

	
	private String updatedBy;

	
	private String updatedDate;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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


	public String getProgramDescription() {
		return programDescription;
	}


	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}


	public String getProgramName() {
		return programName;
	}


	public void setProgramName(String programName) {
		this.programName = programName;
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
	
	
}
